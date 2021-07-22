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

import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.model.Borrower;
import com.ss.lms.service.Util;

/**
 * @borrower Bruno
 *
 */
public class BorrowerDAOTest {

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
		List<String> columnNames = Arrays.asList("Name", "Address", "Phone");
		
		Borrower borrower = new Borrower();
		List<String> columns = borrower.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		Borrower borrower = new Borrower();
		List<String> columnNames = Arrays.asList("Name", "Address", "Phone");
		List<String> columns = borrower.getValues();
		
		Assert.assertEquals(borrower.checkIfRequired(null), false);
		Assert.assertEquals(borrower.checkIfRequired("Full Name"), false);

		Assert.assertEquals(borrower.checkIfRequired(columns.get(0)), false);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
		Assert.assertEquals(borrower.checkIfRequired(columns.get(1)), false);
		Assert.assertEquals(columns.get(1), columnNames.get(1));
		Assert.assertEquals(borrower.checkIfRequired(columns.get(2)), false);
		Assert.assertEquals(columns.get(2), columnNames.get(2));
	}
	
	@Test
	public void test_add_borrower() throws ClassNotFoundException, SQLException {
		Borrower toAdd = new Borrower();

		String borrowerName = "Bruno Rebaza";
		String borrowerAddress = "127 E 32nd St";
		String borrowerPhone = "201-542-6752";

		toAdd.setValues(Arrays.asList(borrowerName, borrowerAddress, borrowerPhone));
		
		BorrowerDAO bdao = new BorrowerDAO(conn);
		Integer pK = bdao.createBorrower(toAdd);
		toAdd.setCardNo(pK);
		
		Borrower result = bdao.readBorrower(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Borrower CardNo: " + pK + 
			"\nBorrower Name: " + borrowerName + 
			"\nBorrower Address: " + borrowerAddress  +
			"\nBorrower Phone: " + borrowerPhone  , result.toStringTest());
	}
	
	@Test
	public void test_add_borrower_name_null() throws ClassNotFoundException, SQLException {
		Borrower toAdd = new Borrower();

		String borrowerName = null;
		String borrowerAddress = "127 E 32nd St";
		String borrowerPhone = "201-542-6752";

		toAdd.setValues(Arrays.asList(borrowerName, borrowerAddress, borrowerPhone));
		
		BorrowerDAO bdao = new BorrowerDAO(conn);
		Integer pK = bdao.createBorrower(toAdd);
		toAdd.setCardNo(pK);
		
		Borrower result = bdao.readBorrower(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Borrower CardNo: " + pK + 
			"\nBorrower Name: " + "Name Empty"+ 
			"\nBorrower Address: " + borrowerAddress  +
			"\nBorrower Phone: " + borrowerPhone  , result.toStringTest());
	}
	
	@Test
	public void test_add_borrower_all_null() throws ClassNotFoundException, SQLException {
		Borrower toAdd = new Borrower();

		String borrowerName = null;
		String borrowerAddress = null;
		String borrowerPhone = null;

		toAdd.setValues(Arrays.asList(borrowerName, borrowerAddress, borrowerPhone));
		
		BorrowerDAO bdao = new BorrowerDAO(conn);
		Integer pK = bdao.createBorrower(toAdd);
		toAdd.setCardNo(pK);
		
		Borrower result = bdao.readBorrower(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Borrower CardNo: " + pK + 
			"\nBorrower Name: " +  "Name Empty"+ 
			"\nBorrower Address: " +  "Address Empty" +
			"\nBorrower Phone: " +  "Phone Empty"  , result.toStringTest());
	}
	
	@Test
	public void test_update_borrower() throws ClassNotFoundException, SQLException {
		BorrowerDAO bdao = new BorrowerDAO(conn);
		Borrower toUpdate = bdao.readBorrower(150);

		toUpdate.setValues(Arrays.asList("Cristy Caplin", "127 E 32nd St", "201-542-6752"));
		
		bdao.updateBorrower(toUpdate);
		Borrower result = bdao.readBorrower(150);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_borrower() throws ClassNotFoundException, SQLException {
		BorrowerDAO bdao = new BorrowerDAO(conn);
		Borrower toDelete = bdao.readBorrower(150);
		
		bdao.deleteBorrower(toDelete);
		Borrower result = bdao.readBorrower(150);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_borrowers_returns_size() throws ClassNotFoundException, SQLException {
		BorrowerDAO bdao = new BorrowerDAO(conn);
		List<Borrower> borrowers = bdao.readAllBorrowers();
				
		Assert.assertEquals(borrowers.size(), 500);
	}
	
	@Test
	public void test_read_all_borrowers_by_name() throws ClassNotFoundException, SQLException {
		BorrowerDAO bdao = new BorrowerDAO(conn);
		List<Borrower> borrowers = bdao.readAllBorrowers("tom");
		Borrower borrower1 = bdao.readBorrower(104); // Gayleen Tomaini
		Borrower borrower2 = bdao.readBorrower(232); // Tomasina Grollman
		Borrower borrower3 = bdao.readBorrower(387); // Tome Whitlaw
		Borrower borrower4 = bdao.readBorrower(434); // Kim Tomasz
		
		
		Assert.assertEquals(4 , borrowers.size());
		Assert.assertEquals(borrowers.get(0), borrower1);
		Assert.assertEquals(borrowers.get(1), borrower2);
		Assert.assertEquals(borrowers.get(2), borrower3);
		Assert.assertEquals(borrowers.get(3), borrower4);
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

	
}
