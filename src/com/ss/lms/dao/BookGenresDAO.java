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
import com.ss.lms.model.Genre;

/**
 * @author Bruno
 *
 */
public class BookGenresDAO extends BaseDAO<BookGenres> {

	private BookDAO bdao = null;
	private GenreDAO gdao = null;

	public BookGenresDAO(Connection conn) {
		super(conn);
		bdao = new BookDAO(conn);
		gdao = new GenreDAO(conn);
	}

	public Integer createBookGenres(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_genres (genreId, bookId) VALUES(?, ?)",
				Arrays.asList(bookGenres.getGenre().getGenreId(), bookGenres.getBook().getBookId()));
	}

	public void updateBookGenres_Genre(BookGenres bookGenres, Integer genreId) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_genres SET genreId = ? WHERE bookId = ? AND genreId = ?",
				Arrays.asList(genreId, bookGenres.getBook().getBookId(),
						bookGenres.getGenre().getGenreId()));
	}

	public void deleteBookGenres(BookGenres bookGenres) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_genres WHERE bookId = ? AND genreId = ?",
				Arrays.asList(bookGenres.getBook().getBookId(), bookGenres.getGenre().getGenreId()));

	}

	public BookGenres readBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException{
		return readStmtOne("SELECT * FROM tbl_book_genres WHERE bookId = ? AND genreId = ?", Arrays.asList(bookId, genreId));
	}

	public List<Genre> readAllBookGenres_Genres(Integer bookId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_genre.genreId, genreName FROM tbl_book_genres "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_genres.bookId "
				+ "INNER JOIN tbl_genre ON tbl_book_genres.genreId = tbl_genre.genreId "
				+ "WHERE tbl_book.bookId = ?;", Arrays.asList(bookId));
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genreId"));
			genre.setGenreName(rs.getString("genreName"));
			genres.add(genre);
		}
		return genres;
	}

	public List<BookGenres> readAllBookGenres()  throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_genres", null);
	
	}

	@Override
	public List<BookGenres> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookGenres> bookGenresArr = new ArrayList<>();
		while (rs.next()) {
			BookGenres bookGenres = new BookGenres();
			bookGenres.setBook(bdao.readBook(rs.getInt("bookId"))); //
			bookGenres.setGenre(gdao.readGenre(rs.getInt("genreId"))); //
			bookGenresArr.add(bookGenres);
		}
		return bookGenresArr;
	}

	@Override
	public BookGenres extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		BookGenres bookGenres = null;
		while (rs.next()) {
			bookGenres = new BookGenres();
			bookGenres.setBook(bdao.readBook(rs.getInt("bookId"))); //
			bookGenres.setGenre(gdao.readGenre(rs.getInt("genreId"))); //
		}
		return bookGenres;
	}

}
