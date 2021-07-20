/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}

	public void createLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		prepareStmt("INSERT INTO  tbl_library_branch VALUES(?, ?)",
				Arrays.asList(libBranch.getBranchId(), libBranch.getBranchName(), libBranch.getBranchAddress()));
	}

	public void updateLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_library_branch SET branchName = ? , branchAddress = ? WHERE branchId = ?",
				Arrays.asList(libBranch.getBranchName(), libBranch.getBranchAddress(), libBranch.getBranchId()));
	}

	public void deleteLibraryBranch(LibraryBranch libBranch) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_library_branch WHERE branchId = ?", Arrays.asList(libBranch.getBranchId()));

	}

	public List<LibraryBranch> readAllLibraryBranch() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_library_branch", null);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		while(rs.next()) {
			LibraryBranch libBranch = new LibraryBranch();
			libBranch.setBranchId(rs.getInt("branchId"));
			libBranch.setBranchName(rs.getString("branchName"));
			libBranch.setBranchAddress(rs.getString("branchAddress"));
		}
		return libraryBranches;
	}

	@Override
	public LibraryBranch extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		LibraryBranch libBranch = null;
		while(rs.next()) {
			libBranch = new LibraryBranch();
			libBranch.setBranchId(rs.getInt("branchId"));
			libBranch.setBranchName(rs.getString("branchName"));
			libBranch.setBranchAddress(rs.getString("branchAddress"));
		}
		return libBranch;
	}
}
