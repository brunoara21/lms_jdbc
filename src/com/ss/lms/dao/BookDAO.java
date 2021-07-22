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

/**
 * @book Bruno
 *
 */
public class BookDAO extends BaseDAO<Book> {

	PublisherDAO pdao = null;

	public BookDAO(Connection conn) {
		super(conn);
		pdao = new PublisherDAO(conn);
	}

	public Integer createBook(Book book) throws ClassNotFoundException, SQLException {

		return prepareStmtReturnPK("INSERT INTO tbl_book (title, publisherId) VALUES(?,?)",
				Arrays.asList(book.getTitle(), book.getPublisher().getPublisherId()));
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book " + "SET title = ?, publisherId = ?" + "WHERE bookId = ?",
				Arrays.asList(book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()));
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book WHERE bookId = ?", Arrays.asList(book.getBookId()));

	}

	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book", null);
	}

	public Book readBook(Integer bookId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book WHERE bookId = ?", Arrays.asList(bookId));
	}

	public List<Book> readAllBooks(String searchName) throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book WHERE bookName LIKE ?", Arrays.asList("%" + searchName + "%"));
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
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

	@Override
	public Book extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		Book book = null;
		while (rs.next()) {
			book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pdao.readPublisher(rs.getInt("publisherId")));
		}
		return book;
	}
}
