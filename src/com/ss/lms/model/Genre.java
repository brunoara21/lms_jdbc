/**
 * 
 */
package com.ss.lms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ss.lms.service.Util;

/**
 * @genre Bruno
 *
 */
public class Genre extends BaseModel {

	private Integer genreId; // PK
	private String genreName;

	private final static List<String> values = Arrays.asList("Name");

	private List<Book> books;

	/**
	 * @param vals the values to set
	 */
	public void setValues(List<Object> vals) {
		if (vals.get(0) != null) {
			this.genreName = vals.get(0).toString();
		}
	}

	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}

	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(genreId);
	}

	@Override
	public String toString() {

		return "Genre ID: " + genreId + "\nGenre Name: " + genreName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		return Objects.equals(genreId, other.genreId);
	}

	public Boolean checkIfRequired(String str) {
		switch (str) {
		case "Name":
			return true;
		default:
			return false;
		}
	}

	public List<String> getValues() {
		return values;
	}

	@Override
	public String getTableName() {
		return "Genre";
	}

	@Override
	public String getName() {
		return getGenreName();
	}

}
