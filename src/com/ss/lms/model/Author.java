/**
 * 
 */
package com.ss.lms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Bruno
 *
 */
public class Author extends BaseModel {

	private Integer authorId; // PK
	private String authorName;

	private final static List<String> values = Arrays.asList("Name");

	private List<Book> books;

	/**
	 * @return the authorId
	 */
	public Integer getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId);
	}

	@Override
	public String toString() {
		return "Author ID: " + authorId + "\nAuthor Name: " + authorName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return Objects.equals(authorId, other.authorId);
	}

	@Override
	public Boolean checkIfRequired(String str) {
		if (str != null) {
			switch (str) {
			case "Name":
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public List<String> getValues() {
		return values;
	}

	@Override
	public void setValues(List<Object> inputs) {
		if (inputs.get(0) != null) {
			this.authorName = inputs.get(0).toString();
		}
	}

	@Override
	public String getTableName() {
		return "Author";
	}

	@Override
	public String getName() {
		return getAuthorName();
	}

}
