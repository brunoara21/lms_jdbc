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
		prepareStmt("UPDATE tbl_author SET AuthorName = ? WHERE authorId = ?",
				Arrays.asList(author.getAuthorName(), author.getAuthorId() ));
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_author WHERE authorId = ?", Arrays.asList(author.getAuthorId()));

	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_author", null);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("AuthorId"));
			author.setAuthorName(rs.getString("AuthorName"));
			authors.add(author);
		}
		return authors;
	}
}
