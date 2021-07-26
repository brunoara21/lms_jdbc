/**
 * 
 */
package com.ss.lms.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ss.lms.dao.BookDAO;
import com.ss.lms.model.Book;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class BookDAOTest {

	private Connection conn = null;
	private Util util = new Util();

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		conn = util.getConnection();
	}

	@After
	public void tearDown() throws ClassNotFoundException, SQLException {
		conn.rollback();
		conn.close();
	}

	@Test
	public void test_column_values() {
		List<String> columnNames = Arrays.asList("Title", "Publisher ID", "Authors", "Genres", "Library Branches");

		Book book = new Book();
		List<String> columns = book.getValues();

		Assert.assertEquals(columns, columnNames);
	}

	@Test
	public void test_column_requirements() {
		Book book = new Book();
		List<String> columnNames = Arrays.asList("Title", "Publisher ID", "Authors", "Genres", "Library Branches");
		List<String> columns = book.getValues();

		Assert.assertEquals(book.checkIfRequired(null), false);
		Assert.assertEquals(book.checkIfRequired("Full Name"), false);

		Assert.assertEquals(book.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));
		Assert.assertEquals(book.checkIfRequired(columns.get(1)), false);
		Assert.assertEquals(columns.get(1), columnNames.get(1));
		Assert.assertEquals(book.checkIfRequired(columns.get(2)), false);
		Assert.assertEquals(columns.get(2), columnNames.get(2));
		Assert.assertEquals(book.checkIfRequired(columns.get(3)), false);
		Assert.assertEquals(columns.get(3), columnNames.get(3));
		Assert.assertEquals(book.checkIfRequired(columns.get(4)), false);
		Assert.assertEquals(columns.get(4), columnNames.get(4));
	}

	@Test
	public void test_add_book() throws ClassNotFoundException, SQLException {
		Book toAdd = new Book();

		String bookTitle = "Science Fiction";
		toAdd.setValues(Arrays.asList(bookTitle, null, null, null, null));

		BookDAO adao = new BookDAO(conn);
		Integer pK = adao.createBook(toAdd);
		toAdd.setBookId(pK);

		Book result = adao.readBook(pK);

		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Book ID: " + pK + "\nBook Title: " + bookTitle + "\nPublisher ID: " + "Publisher Empty",
				result.toStringTest());
	}

	@Test
	public void test_update_book() throws ClassNotFoundException, SQLException {
		BookDAO bdao = new BookDAO(conn);
		Book toUpdate = bdao.readBook(6);

		toUpdate.setValues(Arrays.asList("Five", null, null, null, null));

		bdao.updateBook(toUpdate);
		Book result = bdao.readBook(6);

		Assert.assertEquals(toUpdate, result);
	}

	@Test
	public void test_delete_book() throws ClassNotFoundException, SQLException {
		BookDAO bdao = new BookDAO(conn);
		Book toDelete = bdao.readBook(6);

		bdao.deleteBook(toDelete);
		Book result = bdao.readBook(6);

		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}

	@Test
	public void test_read_all_books_returns_size() throws ClassNotFoundException, SQLException {
		BookDAO adao = new BookDAO(conn);
		List<Book> books = adao.readAllBooks();

		Assert.assertEquals(books.size(), 1004);
	}

	@Test
	public void test_read_all_books_by_name() throws ClassNotFoundException, SQLException {
		BookDAO adao = new BookDAO(conn);
		List<Book> books = adao.readAllBooks("tom");
		Book book1 = adao.readBook(166); // Tom Sawyer
		Book book2 = adao.readBook(211); // Tomboy
		Book book3 = adao.readBook(933); // Restless (Levottomat)

		Assert.assertEquals(books.size(), 3);
		Assert.assertEquals(books.get(0), book1);
		Assert.assertEquals(books.get(1), book2);
		Assert.assertEquals(books.get(2), book3);
	}

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
