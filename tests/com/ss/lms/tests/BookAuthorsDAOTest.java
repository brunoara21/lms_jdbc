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

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookAuthorsDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.model.Author;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookAuthors;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class BookAuthorsDAOTest {

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
		List<String> columnNames = Arrays.asList("Author", "Book");

		BookAuthors book = new BookAuthors();
		List<String> columns = book.getValues();

		Assert.assertEquals(columns, columnNames);
	}

	@Test
	public void test_column_requirements() {
		BookAuthors book = new BookAuthors();
		List<String> columnNames = Arrays.asList("Author", "Book");
		List<String> columns = book.getValues();

		Assert.assertEquals(book.checkIfRequired(null), false);
		Assert.assertEquals(book.checkIfRequired("Full Name"), false);

		Assert.assertEquals(book.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));
		Assert.assertEquals(book.checkIfRequired(columns.get(1)), true);
		Assert.assertEquals(columns.get(1), columnNames.get(1));
	}

	@Test
	public void test_add_book_authors() throws ClassNotFoundException, SQLException {
		BookAuthors toAdd = new BookAuthors();

		AuthorDAO adao = new AuthorDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		Author a = adao.readAuthor(3); // Giustina Minter
		Book b = bdao.readBook(998);  // Darling 
		
		toAdd.setValues(Arrays.asList(a, b));

		BookAuthorsDAO badao = new BookAuthorsDAO(conn);
		badao.createBookAuthors(toAdd);

		BookAuthors result = badao.readBookAuthors(b.getBookId(), a.getAuthorId());

		Assert.assertEquals(toAdd, result);
	}

	@Test
	public void test_update_book_author() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO badao = new BookAuthorsDAO(conn);

		BookAuthors toUpdate = badao.readBookAuthors(110, 1); // Book: Human Resources , Author: Stephen King

		badao.updateBookAuthors_Author(toUpdate, 10);
		BookAuthors result = badao.readBookAuthors(110, 10);


		Assert.assertNotEquals(toUpdate, result);
	}

	@Test
	public void test_delete_book_authors() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO badao = new BookAuthorsDAO(conn);

		BookAuthors toDelete = badao.readBookAuthors(110,1);

		badao.deleteBookAuthors(toDelete);  
		BookAuthors result =	badao.readBookAuthors(110, 1);

		Assert.assertNotEquals(toDelete, result); 
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_book_authors_returns_size() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO badao = new BookAuthorsDAO(conn);
		List<BookAuthors> authors = badao.readAllBookAuthors();

		Assert.assertEquals(1006, authors.size());
	}

	@Test
	public void test_read_all_book_authors_returns_size_for_book() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO badao = new BookAuthorsDAO(conn);
		List<Author> authors = badao.readAllBookAuthors_Authors(1026);

		Assert.assertEquals(2, authors.size());
	}
	

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
