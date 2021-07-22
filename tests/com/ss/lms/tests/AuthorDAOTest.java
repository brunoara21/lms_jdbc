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
import com.ss.lms.model.Author;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class AuthorDAOTest {

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
		
		Author author = new Author();
		List<String> columns = author.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		Author author = new Author();
		List<String> columnNames = Arrays.asList("Name");
		List<String> columns = author.getValues();
		
		Assert.assertEquals(author.checkIfRequired(null), false);
		Assert.assertEquals(author.checkIfRequired("Full Name"), false);

		Assert.assertEquals(author.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
	}
	
	@Test
	public void test_add_author() throws ClassNotFoundException, SQLException {
		Author toAdd = new Author();

		String authorName = "Maya Angelou";
		toAdd.setValues(Arrays.asList("Maya Angelou"));
		
		AuthorDAO adao = new AuthorDAO(conn);
		Integer pK = adao.createAuthor(toAdd);
		toAdd.setAuthorId(pK);
		
		Author result = adao.readAuthor(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Author ID: " + pK + "\nAuthor Name: " + authorName , result.toString());
	}
	
	@Test
	public void test_update_author() throws ClassNotFoundException, SQLException {
		AuthorDAO adao = new AuthorDAO(conn);
		Author toUpdate = adao.readAuthor(509);

		toUpdate.setValues(Arrays.asList("Bruno A. Rebaza"));
		
		adao.updateAuthor(toUpdate);
		Author result = adao.readAuthor(509);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_author() throws ClassNotFoundException, SQLException {
		AuthorDAO adao = new AuthorDAO(conn);
		Author toDelete = adao.readAuthor(509);
		
		adao.deleteAuthor(toDelete);
		Author result = adao.readAuthor(509);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_authors_returns_size() throws ClassNotFoundException, SQLException {
		AuthorDAO adao = new AuthorDAO(conn);
		List<Author> authors = adao.readAllAuthors();
				
		Assert.assertEquals(authors.size(), 506);
	}
	
	@Test
	public void test_read_all_authors_by_name() throws ClassNotFoundException, SQLException {
		AuthorDAO adao = new AuthorDAO(conn);
		List<Author> authors = adao.readAllAuthors("tom");
		Author author1 = adao.readAuthor(263); // Jill Tomini
		Author author2 = adao.readAuthor(264); // Aileen Tomaschke
		
		
		Assert.assertEquals(authors.size(), 2);
		Assert.assertEquals(authors.get(0), author1);
		Assert.assertEquals(authors.get(1), author2);
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

	
}
