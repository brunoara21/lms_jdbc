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

import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.BookGenresDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.model.Genre;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookGenres;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class BookGenresDAOTest {

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
		List<String> columnNames = Arrays.asList("Genre", "Book");

		BookGenres book = new BookGenres();
		List<String> columns = book.getValues();

		Assert.assertEquals(columns, columnNames);
	}

	@Test
	public void test_column_requirements() {
		BookGenres book = new BookGenres();
		List<String> columnNames = Arrays.asList("Genre", "Book");
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
		BookGenres toAdd = new BookGenres();

		GenreDAO adao = new GenreDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		Genre g = adao.readGenre(1); // Action
		Book b = bdao.readBook(211);  // Tomboy
		
		toAdd.setValues(Arrays.asList(g, b));

		BookGenresDAO badao = new BookGenresDAO(conn);
		badao.createBookGenres(toAdd);

		BookGenres result = badao.readBookGenres(b.getBookId(), g.getGenreId());

		Assert.assertEquals(toAdd, result);
		//Assert.assertEquals("BookGenres ID: " + pK + "\nBookGenres Title: " + bookTitle + "\nPublisher ID: " + "Publisher Empty",result.toStringTest());
	}

	@Test
	@Ignore("Not ready yet")
	public void test_update_book_authors() throws ClassNotFoundException, SQLException {
		BookGenresDAO adao = new BookGenresDAO(conn);
		//BookGenres toUpdate = adao.readBookGenres(6);

		//toUpdate.setValues(Arrays.asList("Mystery & Suspense"));

		//adao.updateBookGenres(toUpdate);
		//BookGenres result = adao.readBookGenres(6);

		//Assert.assertEquals(toUpdate, result);
	}

	@Test
	@Ignore("Not ready yet")
	public void test_delete_book_authors() throws ClassNotFoundException, SQLException {
		BookGenresDAO adao = new BookGenresDAO(conn);
		//BookGenres toDelete = adao.readBookGenres(6);

		//adao.deleteBookGenres(toDelete);
		//BookGenres result = adao.readBookGenres(6);

		//Assert.assertNotEquals(toDelete, result);
		//Assert.assertNull(result);
	}

	@Test
	public void test_read_all_book_authors_returns_size() throws ClassNotFoundException, SQLException {
		BookGenresDAO adao = new BookGenresDAO(conn);
		List<BookGenres> books = adao.readAllBookGenres();

		Assert.assertEquals(books.size(), 6);
	}

	

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
