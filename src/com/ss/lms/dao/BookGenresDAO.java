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

import com.ss.lms.model.BookGenres;

/**
 * @author Bruno
 *
 */
public class BookGenresDAO extends BaseDAO<BookGenres> {

	public BookGenresDAO(Connection conn) {
		super(conn);
	}

	public Integer createBookGenres(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_genres VALUES(?, ?)",
				Arrays.asList(bookGenres.getGenre(), bookGenres.getBook()));
	}

	public void updateBookGenres_Book(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_genres SET bookId = ? WHERE bookId = ? AND genreId = ?",
				Arrays.asList(bookGenres.getBook().getBookId(), bookGenres.getBook().getBookId(),
						bookGenres.getGenre().getGenreId()));
	}

	public void updateBookGenres_Genre(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_genres SET genreId = ? WHERE bookId = ? AND genreId = ?",
				Arrays.asList(bookGenres.getGenre().getGenreId(), bookGenres.getBook().getBookId(),
						bookGenres.getGenre().getGenreId()));
	}
	
	public void deleteBookGenres(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_genres WHERE bookId = ? AND genreId = ?", Arrays.asList(bookGenres.getBook().getBookId(),
				bookGenres.getGenre().getGenreId()));

	}

	public List<BookGenres> readAllBookGenres() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_genres", null);
	}

	/*
	 * public BookGenres readBookGenres(Integer bookGenresId) throws
	 * ClassNotFoundException, SQLException { return
	 * readStmtOne("SELECT * FROM tbl_book_genres WHERE branchId = ?",
	 * Arrays.asList(bookGenresId)); }
	 */

	/*
	 * public List<BookGenres> readAllBookGenres(String searchName) throws
	 * ClassNotFoundException, SQLException { return
	 * readStmt("SELECT * FROM tbl_book_genres WHERE branchName LIKE ?",
	 * Arrays.asList("%" + searchName + "%")); }
	 */

	@Override
	public List<BookGenres> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookGenres> bookGenresArr = new ArrayList<>();
		while(rs.next()) {
			BookGenres bookGenres = new BookGenres();
			//bookGenres.setBook(rs.getInt("bookId"));
			//bookGenres.setBranchName(rs.getString("genreId"));
			//bookGenresArr.add(bookGenres);
		}
		return bookGenresArr;
	}

	@Override
	public BookGenres extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
