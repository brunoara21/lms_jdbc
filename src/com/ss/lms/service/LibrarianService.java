/**
 * 
 */
package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.BookAuthorsDAO;
import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookGenresDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.BaseModel;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class LibrarianService extends BaseService {

	Util util = new Util();


	public String update(BaseModel toUpdate) {
		if (toUpdate instanceof LibraryBranch) {
			return updateLibraryBranch((LibraryBranch) toUpdate);
		} else if (toUpdate instanceof BookCopies) {
			return updateBookCopies((BookCopies) toUpdate);
		}

		return "Something went wrong when updating.";
	}


	public List<Book> readBooksFromBranch(LibraryBranch branch) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookAuthorsDAO badao = new BookAuthorsDAO(conn);
				BookGenresDAO bgdao = new BookGenresDAO(conn);
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);

				List<Book> books = bcdao.readAllBookCopies_Books(branch.getBranchId());
				for(Book b: books) {
					b.setBookAuthors(badao.readAllBookAuthors_Authors(b.getBookId()));
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
	
	public BookCopies readBookCopiesFromBookBranch(Book book, LibraryBranch branch) {
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
	
	

	public String updateLibraryBranch(LibraryBranch toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
				pdao.updateLibraryBranch(toUpdate);
				conn.commit();
				return "Library Branch '" + toUpdate.getBranchName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Library Branch could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Library Branch could not be updated, problem with connection.";
		}
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



	private String updateBookCopies(BookCopies toUpdate) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				BookCopiesDAO bcdao = new BookCopiesDAO(conn);
				bcdao.updateBookCopies(toUpdate);
				conn.commit();
				return "Book Copies successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book Copies could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book Copies could not be updated, problem with connection.";
		}
	}
}
