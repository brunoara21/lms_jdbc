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

import com.ss.lms.dao.GenreDAO;
import com.ss.lms.model.Genre;
import com.ss.lms.service.Util;

/**
 * @genre Bruno
 *
 */
public class GenreDAOTest {

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
		List<String> columnNames = Arrays.asList("Name");
		
		Genre genre = new Genre();
		List<String> columns = genre.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		Genre genre = new Genre();
		List<String> columnNames = Arrays.asList("Name");
		List<String> columns = genre.getValues();
		
		Assert.assertEquals(genre.checkIfRequired(null), false);
		Assert.assertEquals(genre.checkIfRequired("Full Name"), false);

		Assert.assertEquals(genre.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
	}
	
	@Test
	public void test_add_genre() throws ClassNotFoundException, SQLException {
		Genre toAdd = new Genre();

		String genreName = "Science Fiction";
		toAdd.setValues(Arrays.asList(genreName));
		
		GenreDAO adao = new GenreDAO(conn);
		Integer pK = adao.createGenre(toAdd);
		toAdd.setGenreId(pK);
		
		Genre result = adao.readGenre(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Genre ID: " + pK + "\nGenre Name: " + genreName , result.toString());
	}
	
	@Test
	public void test_update_genre() throws ClassNotFoundException, SQLException {
		GenreDAO adao = new GenreDAO(conn);
		Genre toUpdate = adao.readGenre(6);

		toUpdate.setValues(Arrays.asList("Mystery & Suspense"));
		
		adao.updateGenre(toUpdate);
		Genre result = adao.readGenre(6);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_genre() throws ClassNotFoundException, SQLException {
		GenreDAO adao = new GenreDAO(conn);
		Genre toDelete = adao.readGenre(6);
		
		adao.deleteGenre(toDelete);
		Genre result = adao.readGenre(6);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_genres_returns_size() throws ClassNotFoundException, SQLException {
		GenreDAO adao = new GenreDAO(conn);
		List<Genre> genres = adao.readAllGenres();
				
		Assert.assertEquals(genres.size(), 7);
	}
	
	@Test
	public void test_read_all_genres_by_name() throws ClassNotFoundException, SQLException {
		GenreDAO adao = new GenreDAO(conn);
		List<Genre> genres = adao.readAllGenres("a");
		Genre genre1 = adao.readGenre(1); // Action
		Genre genre2 = adao.readGenre(3); // Adventure
		Genre genre3 = adao.readGenre(7); // Fantasy
		
		
		Assert.assertEquals(genres.size(), 3);
		Assert.assertEquals(genres.get(0), genre1);
		Assert.assertEquals(genres.get(1), genre2);
		Assert.assertEquals(genres.get(2), genre3);
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

	
}
