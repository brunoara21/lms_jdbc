/**
 * 
 */
package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.ss.lms.dao.BookAuthorsDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookGenresDAO;
import com.ss.lms.dao.BookLoansDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.BookGenres;
import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;
import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class BorrowerService {

	Util util = new Util();

	public String addBookLoan(BookLoans bl) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookLoansDAO bldao = new BookLoansDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				
				LocalDateTime ldt = LocalDateTime.now();
				LocalDateTime ldtOneWeek = ldt.plusWeeks(1);
				Timestamp dtOut = Timestamp.valueOf(ldt);
				Timestamp dtDue= Timestamp.valueOf(ldtOneWeek);
				bl.setDateOut(dtOut);
				bl.setDueDate(dtDue);
				BookCopies bc = readBookCopies(bl.getBook(), bl.getBranch());
				Integer noOfCopies = bc.getNoOfCopies();
				if (noOfCopies > 0) { // See if theres's enough copies to loan out
					bldao.createBookLoans(bl);
					bc.setNoOfCopies(noOfCopies - 1);
					bcdao.updateBookCopies(bc);
					conn.commit();
					return "Book Loan successfully checked out. Remember that this book is due on '" + bl.getDueDate().toLocalDateTime().toLocalDate()+ "'";
				} else { // Not enough copies at Branch of this Book
					return "Book Loan could not be added. Not enough of '" + bl.getBook().getName() + "' at '"
							+ bl.getBranch().getName() + "'.";
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				conn.rollback();
				if(updateCheckBookLoan(bl).equals("Book Loan successfully updated.") ) { // BookLoan existed but turned in. User checks book out again
					return "Book Loan successfully checked out. Remember that this book is due on '" + bl.getDueDate().toLocalDateTime().toLocalDate()+ "'";
				} else {
					return "Book Loan could not be added because this book is already loaned out by you at this location.";
				}
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book Loan could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book Loan could not be added, problem with connection.";
		}
	}

	public String updateCheckBookLoan(BookLoans bookLoan)
	{
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookLoansDAO bldao = new BookLoansDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				
				
				BookCopies bc = readBookCopies(bookLoan.getBook(), bookLoan.getBranch());
				Integer noOfCopies = bc.getNoOfCopies();
				if(bldao.isDateInNotNull(bookLoan)) { // Check to see if Book was returned
					bldao.updateBookLoans(bookLoan);
					bc.setNoOfCopies(noOfCopies - 1);
					bcdao.updateBookCopies(bc);
					conn.commit();
					return "Book Loan successfully updated.";
				}
				else {
					return "Book Loan could not be updated.";
				}

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book Loan could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book Loan could not be updated, problem with connection.";
		}
	}
	
	public String updateReturnBookLoan(BookLoans bookLoan)
	{
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookLoansDAO bldao = new BookLoansDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);

				LocalDateTime ldtI = LocalDateTime.now();
				Timestamp dtIn= Timestamp.valueOf(ldtI);
				bookLoan.setDateIn(dtIn);
				
				BookCopies bc = readBookCopies(bookLoan.getBook(), bookLoan.getBranch());
				Integer noOfCopies = bc.getNoOfCopies();
				
				bldao.updateBookLoans(bookLoan);	
				bc.setNoOfCopies(noOfCopies + 1);
				bcdao.updateBookCopies(bc);
				conn.commit();			
				if(bookLoan.getDueDate().compareTo(bookLoan.getDateIn()) >= 0) { // On Time
					return "Book Loan successfully returned ON TIME.";
				} else {
					return "Book Loan successfully returned LATE.";
				}

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book Loan could not be returned";
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book Loan could not be updated, problem with connection.";
		}
	}
	
	public BookCopies readBookCopies(Book book, LibraryBranch branch) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				BookCopies bc = bcdao.readBookCopies(book.getBookId(), branch.getBranchId());

				return bc;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Borrower readBorrower(Integer cardNo) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BorrowerDAO bdao = new BorrowerDAO(conn);
				Borrower borrower = bdao.readBorrower(cardNo);
				// TODO POPULATE CHILD ELEMENTS
				return borrower;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Book> readBooksFromBranchEnoughCopies(LibraryBranch branch) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);

				List<Book> books = bcdao.readAllBookCopies_Books_EnoughCopies(branch.getBranchId());
				for (Book b : books) {
					b.setBookAuthors(badao.readBookAuthors_Authors(b.getBookId()));
					b.setBookGenres(bgdao.readAllBookGenres_Genres(b.getBookId()));
					b.setBookBranches(bcdao.readAllBookCopies_Branches(b.getBookId()));
				}

				return books;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<LibraryBranch> readLibraryBranches() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				List<LibraryBranch> publishers = pdao.readAllLibraryBranches();
				// TODO POPULATE CHILD ELEMENTS
				return publishers;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<BookLoans> readBookLoansFromBorrower(Borrower borrower) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookLoansDAO bldao = new BookLoansDAO(conn);
				List<BookLoans> bookLoans = bldao.readAllBookLoans_FromBorrower(borrower);
				
				// TODO POPULATE CHILD ELEMENTS
				return bookLoans;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
