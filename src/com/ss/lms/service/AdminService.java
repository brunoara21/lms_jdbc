/**
 * 
 */
package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.LibraryBranchDAO;
import com.ss.lms.model.Author;

/**
 * @author Bruno
 *
 */
public class AdminService {

	Util util = new Util();

	// TODO
	public String addBook() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn); // TODO
				// PublisherDAO pdao = new PublisherDAO(conn);
				// BookGenreDAO bgdao = new BookGenreDAO(conn);
				// GenreDAO gdao = new GenreDAO(conn);
				// BookAuthorDAO badao = new BookAuthorDAO(conn);
				AuthorDAO adao = new AuthorDAO(conn);

				conn.commit();
				return "Book added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Book could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Book could not be added, problem with connection.";
		}
	}

	
	public String addAuthor(Author author) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				Integer pK = adao.createAuthor(author);
				author.setAuthorId(pK);
				conn.commit();
				return "Author '" + author.getAuthorName() + "' added successfully";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be added";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be added, problem with connection.";
		}
	}
	
	public String updateAuthor(Author author) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				adao.updateAuthor(author);
				conn.commit();
				return "Author '" + author.getAuthorName() + "' successfully updated";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be updated";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be updated, problem with connection.";
		}
	}
	
	public String deleteAuthor(Author author) {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				adao.deleteAuthor(author);
				conn.commit();
				return "Author '" + author.getAuthorName() + "' successfully deleted";

			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				return "Author could not be deleted";

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Author could not be deleted, problem with connection.";
		}
	}
	
	public List<Author> readAuthors() {
		Connection conn = null;
		try {
			try {
				conn = util.getConnection();
				AuthorDAO adao = new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
				// TODO POPULATE CHILD ELEMENTS
				return authors;
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
