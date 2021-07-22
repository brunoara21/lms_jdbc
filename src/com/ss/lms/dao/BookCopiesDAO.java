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

import com.ss.lms.model.BookCopies;

/**
 * @author Bruno
 *
 */
public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}

	public Integer createBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_copies VALUES(?, ?, ?)",
				Arrays.asList(bookCopies.getBook(), bookCopies.getBranch(), bookCopies.getNoOfCopies()));
	}

	public void updateBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", Arrays.asList(
				bookCopies.getNoOfCopies(), bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()));
	}

	public void deleteBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?",
				Arrays.asList(bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()));

	}

	public List<BookCopies> readAllBookCopies() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_copies", null);
	}

	public BookCopies readBookCopies(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", Arrays.asList(bookId, branchId));
	}

	/*
	 * public List<BookCopies> readAllBookCopies(String searchName) throws
	 * ClassNotFoundException, SQLException { return
	 * readStmt("SELECT * FROM tbl_book_copies WHERE branchName LIKE ?",
	 * Arrays.asList("%" + searchName + "%")); }
	 */

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookCopies> bookCopiesArr = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopies = new BookCopies();
			// bookCopies.setBook(rs.getInt("bookId"));
			// bookCopies.setBranchName(rs.getString("genreId"));
			// bookCopiesArr.add(bookCopies);
		}
		return bookCopiesArr;
	}

	@Override
	public BookCopies extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
