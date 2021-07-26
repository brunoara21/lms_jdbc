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

import com.ss.lms.model.Author;
import com.ss.lms.model.BookAuthors;

/**
 * @author Bruno
 *
 */
public class BookAuthorsDAO extends BaseDAO<BookAuthors> {

	private BookDAO bdao = null;
	private AuthorDAO adao = null;

	public BookAuthorsDAO(Connection conn) {
		super(conn);
		bdao = new BookDAO(conn);
		adao = new AuthorDAO(conn);
	}

	public Integer createBookAuthors(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_authors (authorId, bookId) VALUES(?, ?)",
				Arrays.asList(bookAuthors.getAuthor().getAuthorId(), bookAuthors.getBook().getBookId()));
	}
	

	public void updateBookAuthors_Author(BookAuthors bookAuthors, Integer authorId) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_authors SET authorId = ? WHERE bookId = ? AND authorId = ?",
				Arrays.asList(authorId, bookAuthors.getBook().getBookId(),
						bookAuthors.getAuthor().getAuthorId()));
	}

	public void deleteBookAuthors(BookAuthors bookAuthors) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_authors WHERE bookId = ? AND authorId = ?",
				Arrays.asList(bookAuthors.getBook().getBookId(), bookAuthors.getAuthor().getAuthorId()));

	}

	public List<BookAuthors> readAllBookAuthors()  throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_authors", null);
	
	}

	public List<Author> readAllBookAuthors_Authors(Integer bookId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_author.authorId, authorName FROM tbl_book_authors "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_authors.bookId "
				+ "INNER JOIN tbl_author ON tbl_book_authors.authorId = tbl_author.authorId "
				+ "WHERE tbl_book.bookId = ?;", Arrays.asList(bookId));
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

	public BookAuthors readBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_authors WHERE bookId = ? AND authorId = ?",
				Arrays.asList(bookId, authorId));
	}



	@Override
	public List<BookAuthors> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookAuthors> bookAuthorsArr = new ArrayList<>();
		while (rs.next()) {
			BookAuthors bookAuthors = new BookAuthors();
			bookAuthors.setBook(bdao.readBook(rs.getInt("bookId")));
			bookAuthors.setAuthor(adao.readAuthor(rs.getInt("authorId")));
			bookAuthorsArr.add(bookAuthors);
		}
		return bookAuthorsArr;
	}

	@Override
	public BookAuthors extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		BookAuthors bookAuthors = null;
		while (rs.next()) {
			bookAuthors = new BookAuthors();
			bookAuthors.setBook(bdao.readBook(rs.getInt("bookId")));
			bookAuthors.setAuthor(adao.readAuthor(rs.getInt("authorId")));
		}
		return bookAuthors;
	}

}
