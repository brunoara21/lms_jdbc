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

import com.ss.lms.model.BookAuthors;
import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class BookAuthorsDAO extends BaseDAO<BookAuthors> {

	public BookAuthorsDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public Integer createBookAuthors(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_genres VALUES(?, ?)",
				Arrays.asList(bookAuthors.getAuthor(), bookAuthors.getBook()));
	}

	public void updateBookAuthors_Book(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_genres SET bookId = ? WHERE bookId = ? AND genreId = ?",
				Arrays.asList(bookAuthors.getBook().getBookId(), bookAuthors.getBook().getBookId(),
						bookAuthors.getAuthor().getAuthorId()));
	}

	public void updateBookAuthors_Author(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_genres SET genreId = ? WHERE bookId = ? AND genreId = ?",
				Arrays.asList(bookAuthors.getAuthor().getAuthorId(), bookAuthors.getBook().getBookId(),
						bookAuthors.getAuthor().getAuthorId()));
	}
	
	public void deleteBookAuthors(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_genres WHERE bookId = ? AND genreId = ?", Arrays.asList(bookAuthors.getBook().getBookId(),
				bookAuthors.getAuthor().getAuthorId()));

	}

	public List<BookAuthors> readAllBookAuthors() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_genres", null);
	}

	/*
	 * public BookAuthors readBookAuthors(Integer bookAuthorsId) throws
	 * ClassNotFoundException, SQLException { return
	 * readStmtOne("SELECT * FROM tbl_book_genres WHERE branchId = ?",
	 * Arrays.asList(bookAuthorsId)); }
	 */

	/*
	 * public List<BookAuthors> readAllBookAuthors(String searchName) throws
	 * ClassNotFoundException, SQLException { return
	 * readStmt("SELECT * FROM tbl_book_genres WHERE branchName LIKE ?",
	 * Arrays.asList("%" + searchName + "%")); }
	 */

	@Override
	public List<BookAuthors> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookAuthors> bookAuthorsArr = new ArrayList<>();
		while(rs.next()) {
			BookAuthors bookAuthors = new BookAuthors();
			//bookAuthors.setBook(rs.getInt("bookId"));
			//bookAuthors.setBranchName(rs.getString("genreId"));
			//bookAuthorsArr.add(bookAuthors);
		}
		return bookAuthorsArr;
	}

	@Override
	public BookAuthors extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
