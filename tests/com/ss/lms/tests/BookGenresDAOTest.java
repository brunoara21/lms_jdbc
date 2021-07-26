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
import com.ss.lms.dao.BookGenresDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookGenres;
import com.ss.lms.model.Genre;
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
	public void test_add_book_genres() throws ClassNotFoundException, SQLException {
		BookGenres toAdd = new BookGenres();

		GenreDAO adao = new GenreDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		Genre g = adao.readGenre(1); // Action
		Book b = bdao.readBook(211); // Tomboy

		toAdd.setValues(Arrays.asList(g, b));

		BookGenresDAO badao = new BookGenresDAO(conn);
		badao.createBookGenres(toAdd);

		BookGenres result = badao.readBookGenres(b.getBookId(), g.getGenreId());

		Assert.assertEquals(toAdd, result);
	}

	@Test
	public void test_update_book_genre() throws ClassNotFoundException, SQLException {
		BookGenresDAO bgdao = new BookGenresDAO(conn);

		BookGenres toUpdate = bgdao.readBookGenres(3, 1); // Book: Blind , Genre: Action

		bgdao.updateBookGenres_Genre(toUpdate, 3);
		BookGenres result = bgdao.readBookGenres(3, 3);


		Assert.assertNotEquals(toUpdate, result);
	}

	@Test
	public void test_delete_book_genres() throws ClassNotFoundException, SQLException {
		BookGenresDAO bgdao = new BookGenresDAO(conn);

		BookGenres toDelete = bgdao.readBookGenres(3,1);

		bgdao.deleteBookGenres(toDelete);  
		BookGenres result =	bgdao.readBookGenres(3, 1);

		Assert.assertNotEquals(toDelete, result); 
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_book_genres_returns_size() throws ClassNotFoundException, SQLException {
		BookGenresDAO bgdao = new BookGenresDAO(conn);
		List<BookGenres> genres = bgdao.readAllBookGenres();

		Assert.assertEquals(12, genres.size());
	}

	@Test
	public void test_read_all_book_genres_returns_size_for_book() throws ClassNotFoundException, SQLException {
		BookGenresDAO bgdao = new BookGenresDAO(conn);
		List<Genre> genres = bgdao.readAllBookGenres_Genres(1);

		Assert.assertEquals(2, genres.size());
	}

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
