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

import com.ss.lms.dao.BookCopiesDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class BookCopiesDAOTest {

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
		List<String> columnNames = Arrays.asList("Book", "Library Branch", "Copies");

		BookCopies book = new BookCopies();
		List<String> columns = book.getValues();

		Assert.assertEquals(columns, columnNames);
	}

	@Test
	public void test_column_requirements() {
		BookCopies book = new BookCopies();
		List<String> columnNames = Arrays.asList("Book", "Library Branch", "Copies");
		List<String> columns = book.getValues();

		Assert.assertEquals(book.checkIfRequired(null), false);
		Assert.assertEquals(book.checkIfRequired("Full Name"), false);

		Assert.assertEquals(book.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));
		Assert.assertEquals(book.checkIfRequired(columns.get(1)), true);
		Assert.assertEquals(columns.get(1), columnNames.get(1));
		Assert.assertEquals(book.checkIfRequired(columns.get(2)), false);
		Assert.assertEquals(columns.get(2), columnNames.get(2));
	}

	@Test
	public void test_add_book_copies() throws ClassNotFoundException, SQLException {
		BookCopies toAdd = new BookCopies();

		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		Integer copies = 10;
		LibraryBranch lb = lbdao.readLibraryBranch(4); // Arkansas
		Book b = bdao.readBook(3);  // Blind
		
		toAdd.setValues(Arrays.asList(b, lb, copies));

		BookCopiesDAO badao = new BookCopiesDAO(conn);
		badao.createBookCopies(toAdd);

		BookCopies result = badao.readBookCopies(b.getBookId(), lb.getBranchId());

		Assert.assertEquals(toAdd, result);
	}

	@Test
	public void test_update_book_copies() throws ClassNotFoundException, SQLException {
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookCopies toUpdate = bcdao.readBookCopies(1, 4); // Book: The Lost Tribe , Branch Arkansas
		Assert.assertEquals(100, toUpdate.getNoOfCopies().intValue());

		toUpdate.setNoOfCopies(toUpdate.getNoOfCopies() + 1);
		bcdao.updateBookCopies(toUpdate);
		BookCopies result = bcdao.readBookCopies(1, 4);

		Assert.assertEquals(toUpdate, result);
	}

	@Test
	public void test_read_all_book_enough_copies_returns_size() throws ClassNotFoundException, SQLException {
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		List<Book> books = bcdao.readAllBookCopies_Books_EnoughCopies(50); // Branch Arkansas

		Assert.assertEquals(17, books.size());
	}

	@Test
	public void test_read_all_book_copies_books_returns_size() throws ClassNotFoundException, SQLException {
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		List<Book> books = bcdao.readAllBookCopies_Books(50); // Branch Arkansas

		Assert.assertEquals(18, books.size());
	}

	@Test
	public void test_read_all_book_copies_branches_returns_size() throws ClassNotFoundException, SQLException {
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		List<LibraryBranch> branches = bcdao.readAllBookCopies_Branches(1026); // Book: Dynamic Programming Ed2.

		Assert.assertEquals(2, branches.size());
	}

	@Test
	public void test_read_all_book_copies() throws ClassNotFoundException, SQLException {
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		List<BookCopies> bc = bcdao.readAllBookCopies();

		Assert.assertNotEquals(1000, bc.size());
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
