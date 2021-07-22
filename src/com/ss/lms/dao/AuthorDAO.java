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

import com.ss.lms.model.Author;

/**
 * @author Bruno
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public Integer createAuthor(Author author) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_author (authorName) VALUES(?)",
				Arrays.asList(author.getAuthorName()));
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_author SET authorName = ? WHERE authorId = ?",
				Arrays.asList(author.getAuthorName(), author.getAuthorId()));
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_author WHERE authorId = ?", Arrays.asList(author.getAuthorId()));

	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_author", null);
	}

	public Author readAuthor(Integer authorId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_author WHERE authorId = ?", Arrays.asList(authorId));
	}

	public List<Author> readAllAuthors(String searchName) throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_author WHERE authorName LIKE ?", Arrays.asList("%" + searchName +"%"));
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

	@Override
	public Author extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		Author author = null;
		if (rs.next()) {
			author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
		}
		return author;
	}
}
