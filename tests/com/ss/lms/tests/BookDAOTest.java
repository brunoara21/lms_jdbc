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
		List<String> columnNames = Arrays.asList("Name");
		
		Book book = new Book();
		List<String> columns = book.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		Book book = new Book();
		List<String> columnNames = Arrays.asList("Name");
		List<String> columns = book.getValues();
		
		Assert.assertEquals(book.checkIfRequired(null), false);
		Assert.assertEquals(book.checkIfRequired("Full Name"), false);

		Assert.assertEquals(book.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
	}
	
	@Test
	public void test_add_book() throws ClassNotFoundException, SQLException {
		Book toAdd = new Book();

		String bookName = "Science Fiction";
		toAdd.setValues(Arrays.asList(bookName));
		
		BookDAOTest adao = new BookDAOTest(conn);
		Integer pK = adao.createBook(toAdd);
		toAdd.setBookId(pK);
		
		Book result = adao.readBook(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Book ID: " + pK + "\nBook Name: " + bookName , result.toString());
	}
	
	@Test
	public void test_update_book() throws ClassNotFoundException, SQLException {
		BookDAOTest adao = new BookDAOTest(conn);
		Book toUpdate = adao.readBook(6);

		toUpdate.setValues(Arrays.asList("Mystery & Suspense"));
		
		adao.updateBook(toUpdate);
		Book result = adao.readBook(6);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_book() throws ClassNotFoundException, SQLException {
		BookDAOTest adao = new BookDAOTest(conn);
		Book toDelete = adao.readBook(6);
		
		adao.deleteBook(toDelete);
		Book result = adao.readBook(6);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_books_returns_size() throws ClassNotFoundException, SQLException {
		BookDAOTest adao = new BookDAOTest(conn);
		List<Book> books = adao.readAllBooks();
				
		Assert.assertEquals(books.size(), 7);
	}
	
	@Test
	public void test_read_all_books_by_name() throws ClassNotFoundException, SQLException {
		BookDAOTest adao = new BookDAOTest(conn);
		List<Book> books = adao.readAllBooks("a");
		Book book1 = adao.readBook(1); // Action
		Book book2 = adao.readBook(3); // Adventure
		Book book3 = adao.readBook(7); // Fantasy
		
		
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
