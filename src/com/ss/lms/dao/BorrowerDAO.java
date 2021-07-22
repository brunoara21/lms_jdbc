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

import com.ss.lms.model.Borrower;

/**
 * @borrower Bruno
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public Integer createBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {

		return prepareStmtReturnPK("INSERT INTO tbl_borrower (name, address, phone) VALUES(?,?,?)",
				Arrays.asList(borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone()));
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
				Arrays.asList(borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(),
						borrower.getCardNo()));
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_borrower WHERE cardNo = ?", Arrays.asList(borrower.getCardNo()));

	}

	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_borrower", null);
	}

	public Borrower readBorrower(Integer cardNo) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_borrower WHERE cardNo = ?", Arrays.asList(cardNo));
	}

	public List<Borrower> readAllBorrowers(String searchName) throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_borrower WHERE name LIKE ?", Arrays.asList("%" + searchName +"%"));
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setBorrowerName(rs.getString("name"));
			borrower.setBorrowerAddress(rs.getString("address"));
			borrower.setBorrowerPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	@Override
	public Borrower extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		Borrower borrower = null;
		while (rs.next()) {
			borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setBorrowerName(rs.getString("name"));
			borrower.setBorrowerAddress(rs.getString("address"));
			borrower.setBorrowerPhone(rs.getString("phone"));
		}
		return borrower;
	}
}
