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

import com.ss.lms.model.Book;
import com.ss.lms.model.BookCopies;
import com.ss.lms.model.LibraryBranch;

/**
 * @author Bruno
 *
 */
public class BookCopiesDAO extends BaseDAO<BookCopies> {

	private BookDAO bdao = null;
	private LibraryBranchDAO lbdao = null;
	private PublisherDAO pdao = null;

	public BookCopiesDAO(Connection conn) {
		super(conn);
		bdao = new BookDAO(conn);
		lbdao = new LibraryBranchDAO(conn);
		pdao = new PublisherDAO(conn);
	}

	public Integer createBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES(?, ?, ?)",
				Arrays.asList(bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId(),
						bookCopies.getNoOfCopies()));
	}

	public void updateBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? AND branchId = ?", Arrays.asList(
				bookCopies.getNoOfCopies(), bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()));
	}

	public void deleteBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?",
				Arrays.asList(bookCopies.getBook().getBookId(), bookCopies.getBranch().getBranchId()));

	}

	public List<BookCopies> readAllBookCopies() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_book_copies", null);
	}

	public List<Book> readAllBookCopies_Books_EnoughCopies(Integer branchId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_book.bookId, title, publisherId FROM tbl_book_copies "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_copies.bookId "
				+ "INNER JOIN tbl_library_branch ON tbl_book_copies.branchId = tbl_library_branch.branchId "
				+ "WHERE tbl_library_branch.branchId = ? AND noOfCopies > 0;", Arrays.asList(branchId));
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pdao.readPublisher(rs.getInt("publisherId")));
			books.add(book);
		}
		return books;
	}
	
	public List<Book> readAllBookCopies_Books(Integer branchId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet("SELECT tbl_book.bookId, title, publisherId FROM tbl_book_copies "
				+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_copies.bookId "
				+ "INNER JOIN tbl_library_branch ON tbl_book_copies.branchId = tbl_library_branch.branchId "
				+ "WHERE tbl_library_branch.branchId = ?;", Arrays.asList(branchId));
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pdao.readPublisher(rs.getInt("publisherId")));
			books.add(book);
		}
		return books;
	}

	public List<LibraryBranch> readAllBookCopies_Branches(Integer bookId) throws ClassNotFoundException, SQLException {
		ResultSet rs = readStmtResultSet(
				"SELECT tbl_library_branch.branchId, branchName, branchAddress FROM tbl_book_copies "
						+ "INNER JOIN tbl_book ON tbl_book.bookId = tbl_book_copies.bookId "
						+ "INNER JOIN tbl_library_branch ON tbl_book_copies.branchId = tbl_library_branch.branchId "
						+ "WHERE tbl_book.bookId = ?;",
				Arrays.asList(bookId));
		List<LibraryBranch> libBranches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch libBranch = new LibraryBranch();
			libBranch.setBranchId(rs.getInt("branchId"));
			libBranch.setBranchName(rs.getString("branchName"));
			libBranch.setBranchAddress(rs.getString("branchAddress"));
			libBranches.add(libBranch);
		}
		return libBranches;
	}

	public BookCopies readBookCopies(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?",
				Arrays.asList(bookId, branchId));
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookCopies> bookCopiesArr = new ArrayList<>();
		while (rs.next()) {
			BookCopies bookCopies = new BookCopies();
			bookCopies.setBook(bdao.readBook(rs.getInt("bookId")));
			bookCopies.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookCopies.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopiesArr.add(bookCopies);
		}
		return bookCopiesArr;
	}

	@Override
	public BookCopies extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		BookCopies bookCopies = null;
		while (rs.next()) {
			bookCopies = new BookCopies();
			bookCopies.setBook(bdao.readBook(rs.getInt("bookId")));
			bookCopies.setBranch(lbdao.readLibraryBranch(rs.getInt("branchId")));
			bookCopies.setNoOfCopies(rs.getInt("noOfCopies"));
		}
		return bookCopies;
	}
}
