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

import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.LibraryBranch;
import com.ss.lms.service.Util;

/**
 * @branch Bruno
 *
 */
public class LibraryBranchDAOTest {

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
		List<String> columnNames = Arrays.asList("Name", "Address");
		
		LibraryBranch branch = new LibraryBranch();
		List<String> columns = branch.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		LibraryBranch branch = new LibraryBranch();
		List<String> columnNames = Arrays.asList("Name", "Address");
		List<String> columns = branch.getValues();
		
		Assert.assertEquals(branch.checkIfRequired(null), false);
		Assert.assertEquals(branch.checkIfRequired("Full Name"), false);

		Assert.assertEquals(branch.checkIfRequired(columns.get(0)), false);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
		Assert.assertEquals(branch.checkIfRequired(columns.get(1)), false);
		Assert.assertEquals(columns.get(1), columnNames.get(1));	
	}
	
	@Test
	public void test_add_branch() throws ClassNotFoundException, SQLException {
		LibraryBranch toAdd = new LibraryBranch();

		String branchName = "Paterson Public Library";
		String branchAddress = "512 Broadway";
		toAdd.setValues(Arrays.asList(branchName, branchAddress));
		
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		Integer pK = pdao.createLibraryBranch(toAdd);
		toAdd.setBranchId(pK);
		
		LibraryBranch result = pdao.readLibraryBranch(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Library Branch ID: " + pK + 
				"\nLibrary Branch Name: " + branchName + 
				"\nLibrary Branch Address: " + branchAddress , result.toStringTest());
	}
	
	@Test
	public void test_add_branch_address_null() throws ClassNotFoundException, SQLException {
		LibraryBranch toAdd = new LibraryBranch();

		String branchName = "Paterson Public Library";
		String branchAddress = null;
		toAdd.setValues(Arrays.asList(branchName, branchAddress));
		
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		Integer pK = pdao.createLibraryBranch(toAdd);
		toAdd.setBranchId(pK);
		
		LibraryBranch result = pdao.readLibraryBranch(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Library Branch ID: " + pK + 
				"\nLibrary Branch Name: " + branchName + 
				"\nLibrary Branch Address: " +  "Address Empty"  , result.toStringTest());
	}
	
	@Test
	public void test_add_branch_all_null() throws ClassNotFoundException, SQLException {
		LibraryBranch toAdd = new LibraryBranch();

		String branchName = null;
		String branchAddress = null;
		toAdd.setValues(Arrays.asList(branchName, branchAddress));
		
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		Integer pK = pdao.createLibraryBranch(toAdd);
		toAdd.setBranchId(pK);
		
		LibraryBranch result = pdao.readLibraryBranch(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Library Branch ID: " + pK + 
				"\nLibrary Branch Name: " + "Name Empty" + 
				"\nLibrary Branch Address: " + "Address Empty" , result.toStringTest());
	}
	
	@Test
	public void test_update_branch() throws ClassNotFoundException, SQLException {
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		LibraryBranch toUpdate = pdao.readLibraryBranch(25);

		toUpdate.setValues(Arrays.asList("Paterson Public Library", "129 McConnell Ave"));
		
		pdao.updateLibraryBranch(toUpdate);
		LibraryBranch result = pdao.readLibraryBranch(25);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_branch() throws ClassNotFoundException, SQLException {
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		LibraryBranch toDelete = pdao.readLibraryBranch(25);
		
		pdao.deleteLibraryBranch(toDelete);
		LibraryBranch result = pdao.readLibraryBranch(25);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_branches_returns_size() throws ClassNotFoundException, SQLException {
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		List<LibraryBranch> branches = pdao.readAllLibraryBranches();
				
		Assert.assertEquals(50, branches.size());
	}
	
	@Test
	public void test_read_all_branches_by_name() throws ClassNotFoundException, SQLException {
		LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
		List<LibraryBranch> branches = pdao.readAllLibraryBranches("bar");
		LibraryBranch branch1 = pdao.readLibraryBranch(30); // Bartelt
		LibraryBranch branch2 = pdao.readLibraryBranch(37); // Barnett
		
		
		Assert.assertEquals(2, branches.size());
		Assert.assertEquals(branches.get(0), branch1);
		Assert.assertEquals(branches.get(1), branch2);
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

	
}
