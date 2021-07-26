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

import com.ss.lms.model.BookLoans;
import com.ss.lms.model.Borrower;

/**
 * @author Bruno
 *
 */
public class BookLoansDAO extends BaseDAO<BookLoans> {

	private BookDAO bdao = null;
	private BorrowerDAO bodao = null;
	private LibraryBranchDAO lbdao = null;

	public BookLoansDAO(Connection conn) {
		super(conn);
		bdao = new BookDAO(conn);
		lbdao = new LibraryBranchDAO(conn);
		bodao = new BorrowerDAO(conn);
	}

	public Integer createBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK(
				"INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES(?, ?, ?, ?, ?)",
				Arrays.asList(bookLoans.getBook().getBookId(), bookLoans.getBranch().getBranchId(),
						bookLoans.getBorrower().getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate()));
	}

	public void updateBookLoans(BookLoans bookLoans) throws ClassNotFoundException, SQLException {
		prepareStmt(
				"UPDATE tbl_book_loans SET dateOut = ? , dueDate = ?, dateIn = ? WHERE cardNo = ? AND bookId = ? AND branchId = ?",
				Arrays.asList(bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn(),
						bookLoans.getBorrower().getCardNo(), bookLoans.getBook().getBookId(),
						bookLoans.getBranch().getBranchId()));
	}

	public BookLoans readBookLoans(BookLoans bl) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND bookId = ? AND branchId = ?",
				Arrays.asList(bl.getBorrower().getCardNo(), bl.getBook().getBookId(), bl.getBranch().getBranchId()));
	}
	
	public BookLoans readBookLoans(Integer bookId, Integer branchId, Integer cardNo) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND bookId = ? AND branchId = ?",
				Arrays.asList(cardNo, bookId, branchId));
	}

	public List<BookLoans> readAllBookLoans_FromBorrower(Borrower borrower)
			throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND dateIn IS NULL",
				Arrays.asList(borrower.getCardNo()));
	}

	public Boolean isDateInNotNull(BookLoans bookLoan) throws ClassNotFoundException, SQLException {
		Integer rowCount = readStmt(
				"SELECT * FROM tbl_book_loans WHERE cardNo = ? AND bookId = ? AND branchId = ? AND dateIn IS NOT NULL",
				Arrays.asList(bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(),
						bookLoan.getBranch().getBranchId())).size();
		return rowCount == 0 ? false : true;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoansArr = new ArrayList<>();
		while (rs.next()) {
			BookLoans bookLoans = new BookLoans();
			bookLoans.setBook(bdao.readBook(rs.getInt("bookId")));
			bookLoans.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookLoans.setBorrower(bodao.readBorrower(rs.getInt("cardNo")));
			bookLoans.setDateOut(rs.getTimestamp("dateOut"));
			bookLoans.setDueDate(rs.getTimestamp("dueDate"));
			bookLoans.setDateIn(rs.getTimestamp("dateIn"));
			bookLoansArr.add(bookLoans);
		}
		return bookLoansArr;
	}

	@Override
	public BookLoans extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		BookLoans bookLoans = null;
		while (rs.next()) {
			bookLoans = new BookLoans();
			bookLoans.setBook(bdao.readBook(rs.getInt("bookId")));
			bookLoans.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookLoans.setBorrower(bodao.readBorrower(rs.getInt("cardNo")));
			bookLoans.setDateOut(rs.getTimestamp("dateOut"));
			bookLoans.setDueDate(rs.getTimestamp("dueDate"));
			bookLoans.setDateIn(rs.getTimestamp("dateIn"));
		}
		return bookLoans;
	}
}
