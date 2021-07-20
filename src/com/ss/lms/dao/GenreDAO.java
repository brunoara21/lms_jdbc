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

import com.ss.lms.model.Genre;

/**
 * @genre Bruno
 *
 */
public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}

	public Integer createGenre(Genre genre) throws ClassNotFoundException, SQLException {
		return prepareStmtReturnPK("INSERT INTO tbl_genre (genreName) VALUES(?)",
				Arrays.asList(genre.getGenreName()));
	}

	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_genre SET genreName = ? WHERE genreId = ?",
				Arrays.asList(genre.getGenreName(), genre.getGenreId() ));
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_genre WHERE genreId = ?", Arrays.asList(genre.getGenreId()));

	}

	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_genre", null);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genreId"));
			genre.setGenreName(rs.getString("genreName"));
			genres.add(genre);
		}
		return genres;
	}

	@Override
	public Genre extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		Genre genre = null;
		while (rs.next()) {
			genre = new Genre();
			genre.setGenreId(rs.getInt("genreId"));
			genre.setGenreName(rs.getString("genreName"));
			
		}
		return genre;
	}
}
