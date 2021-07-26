/**
 * 
 */
package com.ss.lms.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BookLoansDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.Book;
import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.service.Util;

/**
 * @book Bruno
 *
 */
public class BookLoansDAOTest {

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
		List<String> columnNames = Arrays.asList("Book", "Library Branch", "Borrower", "Date Out", "Due Date", "Date In");

		BookLoans bookLoans = new BookLoans();
		List<String> columns = bookLoans.getValues();

		Assert.assertEquals(columns, columnNames);
	}

	@Test
	public void test_column_requirements() {
		BookLoans bookLoans = new BookLoans();
		List<String> columnNames = Arrays.asList("Book", "Library Branch", "Borrower", "Date Out", "Due Date", "Date In");
		List<String> columns = bookLoans.getValues();

		Assert.assertEquals(bookLoans.checkIfRequired(null), false);
		Assert.assertEquals(bookLoans.checkIfRequired("Full Name"), false);

		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));
		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(1)), true);
		Assert.assertEquals(columns.get(1), columnNames.get(1));
		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(2)), true);
		Assert.assertEquals(columns.get(2), columnNames.get(2));
		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(3)), false);
		Assert.assertEquals(columns.get(3), columnNames.get(3));
		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(4)), false);
		Assert.assertEquals(columns.get(4), columnNames.get(4));
		Assert.assertEquals(bookLoans.checkIfRequired(columns.get(5)), false);
		Assert.assertEquals(columns.get(5), columnNames.get(5));
	}

	@Test
	public void test_add_book_loans() throws ClassNotFoundException, SQLException {
		BookLoans toAdd = new BookLoans();
		
		BookDAO bdao = new BookDAO(conn);
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BorrowerDAO bodao = new BorrowerDAO(conn);

		Book b = bdao.readBook(3); // Blind
		LibraryBranch lb = lbdao.readLibraryBranch(4); // Arkansas
		Borrower bo = bodao.readBorrower(2); // Vanya Melior
		
		LocalDateTime ldt = LocalDateTime.now();
		LocalDateTime ldtOneWeek = ldt.plusWeeks(1);
		Timestamp dtOut = Timestamp.valueOf(ldt);
		Timestamp dtDue= Timestamp.valueOf(ldtOneWeek);
			
		toAdd.setValues(Arrays.asList(b,lb,bo,dtOut, dtDue, null));
		

		BookLoansDAO bldao = new BookLoansDAO(conn);
		bldao.createBookLoans(toAdd);


		BookLoans result = bldao.readBookLoans(toAdd);

		Assert.assertEquals(toAdd, result);
	}

	@Test
	public void test_update_book_loans() throws ClassNotFoundException, SQLException {
		BookLoansDAO bldao = new BookLoansDAO(conn);
		
		BookLoans toUpdate = bldao.readBookLoans(1, 10, 187); // Book: The Lost Tribe , Branch: Dakota, Borrower: Antoinette Berresford
	
		Assert.assertEquals("2018-09-12", toUpdate.getDueDate().toLocalDateTime().toLocalDate().toString());

		LocalDateTime ldtOneDay = toUpdate.getDueDate().toLocalDateTime().plusDays(1);
		Timestamp dtOut = Timestamp.valueOf(ldtOneDay);
		
		toUpdate.setDueDate(dtOut);
		bldao.updateBookLoans(toUpdate);
		BookLoans result = bldao.readBookLoans(1, 10, 187);

		Assert.assertEquals(toUpdate, result);
		Assert.assertEquals("2018-09-13", toUpdate.getDueDate().toLocalDateTime().toLocalDate().toString());

	}

	@Test
	public void test_read_all_book_loans_from_borrower() throws ClassNotFoundException, SQLException {
		BookLoansDAO bldao = new BookLoansDAO(conn);
		BorrowerDAO bdao = new BorrowerDAO(conn);
		
		Borrower borrower = bdao.readBorrower(2); // Vanya Melior
		List<BookLoans> bookloans = bldao.readAllBookLoans_FromBorrower(borrower);
		
		Assert.assertEquals(5, bookloans.size());
	}

	@Test
	public void test_is_book_loan_returned() throws ClassNotFoundException, SQLException {
		BookLoansDAO bldao = new BookLoansDAO(conn);
		BookLoans bookLoanReturned = bldao.readBookLoans(17,5,2); // 
		BookLoans bookLoanNotReturned = bldao.readBookLoans(1, 10, 187);

		Assert.assertEquals(true, bldao.isDateInNotNull(bookLoanReturned));
		Assert.assertEquals(false, bldao.isDateInNotNull(bookLoanNotReturned));

	}

	

	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

}
