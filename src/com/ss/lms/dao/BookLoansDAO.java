/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ss.lms.model.Book;
import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;
import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class BookLoansDAO extends BaseDAO<BookLoans> {

	private BookDAO bdao = null;
	private BorrowerDAO bodao = null;
	private LibraryBranchDAO lbdao = null;
	private PublisherDAO pdao = null;

	public BookLoansDAO(Connection conn) {
		super(conn);
		bdao = new BookDAO(conn);
		lbdao = new LibraryBranchDAO(conn);
		pdao = new PublisherDAO(conn);
		bodao = new BorrowerDAO(conn);
	}

	public Integer createBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK(
				"INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES(?, ?, ?, ?, ?)",
				Arrays.asList(bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(),
						bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate()));
	}

	public void updateBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_loans SET dateOut = ? , dueDate = ?, dateIn = ? WHERE cardNo = ? AND bookId = ? AND branchId = ?",
				Arrays.asList(bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn(), bookLoans.getBorrower().getCardNo(),
						bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId()));
	}

	public void deleteBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ?",
				Arrays.asList(bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId()));

	}

	public List<BookLoans> readAllBookLoans() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_loans", null);
	}

	public List<BookLoans> readAllBookLoans_FromBorrower(Borrower borrower)
			throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND dateIn IS NULL",
				Arrays.asList(borrower.getCardNo()));
	}

	public Boolean isDateInNotNull(BookLoans bookLoan) throws ClassNotFoundException, SQLException {
		Integer rowCount = readStmt(
				"SELECT * FROM tbl_book_loans WHERE cardNo = ? AND bookId = ? AND branchId = ? AND dateIn IS NOT NULL",
				Arrays.asList(bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(),
						bookLoan.getBranch().getBranchId())).size();
		return rowCount == 0 ? false : true;
	}

	public List<Book> readAllBookLoans_Books_EnoughLoans(Integer branchId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_book.bookId, title, publisherId FROM tbl_book_loans "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_loans.bookId "
				+ "INNER JOIN tbl_library_branch ON tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ "WHERE tbl_library_branch.branchId = ? AND noOfLoans > 0;", Arrays.asList(branchId));
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pdao.readPublisher(rs.getInt("publisherId")));
			books.add(book);
		}
		return books;
	}

	public List<Book> readAllBookLoans_Books(Integer branchId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_book.bookId, title, publisherId FROM tbl_book_loans "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_loans.bookId "
				+ "INNER JOIN tbl_library_branch ON tbl_book_loans.branchId = tbl_library_branch.branchId "
				+ "WHERE tbl_library_branch.branchId = ?;", Arrays.asList(branchId));
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pdao.readPublisher(rs.getInt("publisherId")));
			books.add(book);
		}
		return books;
	}

	public List<LibraryBranch> readAllBookLoans_Branches(Integer bookId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet(
				"SELECT tbl_library_branch.branchId, branchName, branchAddress FROM tbl_book_loans "
						+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_loans.bookId "
						+ "INNER JOIN tbl_library_branch ON tbl_book_loans.branchId = tbl_library_branch.branchId "
						+ "WHERE tbl_book.bookId = ?;",
				Arrays.asList(bookId));
		List<LibraryBranch> libBranches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch libBranch = new LibraryBranch();
			libBranch.setBranchId(rs.getInt("branchId"));
			libBranch.setBranchName(rs.getString("branchName"));
			libBranch.setBranchAddress(rs.getString("branchAddress"));
			libBranches.add(libBranch);
		}
		return libBranches;
	}

	public BookLoans readBookLoans(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ?",
				Arrays.asList(bookId, branchId));
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoansArr = new ArrayList<>();
		int i = 0;
		while (rs.next()) {
			BookLoans bookLoans = new BookLoans();
			bookLoans.setBook(bdao.readBook(rs.getInt("bookId")));
			bookLoans.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookLoans.setBorrower(bodao.readBorrower(rs.getInt("cardNo")));
			bookLoans.setDateOut(rs.getTimestamp("dateOut"));
			bookLoans.setDueDate(rs.getTimestamp("dueDate"));
			bookLoans.setDateIn(rs.getTimestamp("dateIn"));
			bookLoansArr.add(bookLoans);
		}
		return bookLoansArr;
	}

	@Override
	public BookLoans extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		BookLoans bookLoans = null;
		while (rs.next()) {
			bookLoans = new BookLoans();
			bookLoans.setBook(bdao.readBook(rs.getInt("bookId")));
			bookLoans.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookLoans.setBorrower(bodao.readBorrower(rs.getInt("cardNo")));
			bookLoans.setDateOut(rs.getTimestamp("dateOut"));
			bookLoans.setDueDate(rs.getTimestamp("dueDate"));
			bookLoans.setDateIn(rs.getTimestamp("dateIn"));
		}
		return bookLoans;
	}
}
