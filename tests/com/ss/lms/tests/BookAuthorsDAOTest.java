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
import org.junit.Ignore;
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
		//Assert.assertEquals("BookAuthors ID: " + pK + "\nBookAuthors Title: " + bookTitle + "\nPublisher ID: " + "Publisher Empty",result.toStringTest());
	}

	@Test
	@Ignore("Not ready yet")
	public void test_update_book_authors() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO adao = new BookAuthorsDAO(conn);
		//BookAuthors toUpdate = adao.readBookAuthors(6);

		//toUpdate.setValues(Arrays.asList("Mystery & Suspense"));

		//adao.updateBookAuthors(toUpdate);
		//BookAuthors result = adao.readBookAuthors(6);

		//Assert.assertEquals(toUpdate, result);
	}

	@Test
	@Ignore("Not ready yet")
	public void test_delete_book_authors() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO adao = new BookAuthorsDAO(conn);
		//BookAuthors toDelete = adao.readBookAuthors(6);

		//adao.deleteBookAuthors(toDelete);
		//BookAuthors result = adao.readBookAuthors(6);

		//Assert.assertNotEquals(toDelete, result);
		//Assert.assertNull(result);
	}

	@Test
	public void test_read_all_book_authors_returns_size() throws ClassNotFoundException, SQLException {
		BookAuthorsDAO adao = new BookAuthorsDAO(conn);
		List<BookAuthors> books = adao.readAllBookAuthors();

		Assert.assertEquals(books.size(), 1000);
	}

	

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
