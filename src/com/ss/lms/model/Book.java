/**
 * 
 */
package com.ss.lms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class Book extends BaseModel {

	private Integer bookId; // PK
	private String title;
	private Publisher publisher; // FK

	private List<Author> bookAuthors;
	private List<Genre> bookGenres;
	private List<LibraryBranch> bookLibBranches;

	private final static List<String> values = Arrays.asList("Title", "Publisher ID", "Authors", "Genres",
			"Library Branches");

	/**
	 * @return the bookAuthors
	 */
	public List<Author> getBookAuthors() {
		return bookAuthors;
	}

	/**
	 * @param bookAuthors the bookAuthors to set
	 */
	public void setBookAuthors(List<Author> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	/**
	 * @return the bookGenres
	 */
	public List<Genre> getBookGenres() {
		return bookGenres;
	}

	/**
	 * @param bookGenres the bookGenres to set
	 */
	public void setBookGenres(List<Genre> bookGenres) {
		this.bookGenres = bookGenres;
	}

	/**
	 * @return the bookBranches
	 */
	public List<LibraryBranch> getBookBranches() {
		return bookBranches;
	}

	/**
	 * @param bookBranches the bookBranches to set
	 */
	public void setBookBranches(List<LibraryBranch> bookBranches) {
		this.bookBranches = bookBranches;
	}

	private List<LibraryBranch> bookBranches;
	// private List<LibraryBranch> bookBranches;

	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher != null ? publisher : new Publisher();
	}

	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {

		return "Book ID: " + bookId + "\nBook Title: " + title
				+ Util.fSysOutput.format("\nAuthor(s): " + formatAuthor(bookAuthors))
				+ Util.fSysOutput.format("Genre(s): "
						+ (bookGenres.size() != 0 ? formatGenre(bookGenres) : Util.fSysAlert.format("Genre Empty\n")))
				+ Util.fSysOutput.format(
						"Publisher: " + (getPublisher().getPublisherName() != null ? getPublisher().getPublisherName()
								: Util.fSysAlert.format("Publisher Empty")));

	}

	public String toStringTest() {

		return "Book ID: " + bookId + "\nBook Title: " + title + "\nPublisher ID: "
				+ (getPublisher().getPublisherId() != null ? getPublisher().getPublisherId() : "Publisher Empty");

	}

	private String formatGenre(List<Genre> genres) {
		StringBuilder sb = new StringBuilder();
		Integer place = 0;
		for (Genre a : genres) {
			if (place > 0) {
				sb.append("\t");
			}
			sb.append("\t" + a.getGenreName() + "\n");
			place++;
		}
		return sb.toString();
	}

	private String formatAuthor(List<Author> authors) {
		StringBuilder sb = new StringBuilder();
		Integer place = 0;
		for (Author a : authors) {
			if (place > 0) {
				sb.append("\t");
			}
			sb.append("\t" + a.getAuthorName() + "\n");
			place++;
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(bookId, other.bookId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValues(List<Object> inputs) {
		if (inputs.get(0) != null) { // Title
			this.title = inputs.get(0).toString();
		} 
		if (inputs.get(1) != null) { // PublisherId
			this.publisher = (Publisher) inputs.get(1);
		}
		if (inputs.get(2) != null) { // Authors
			this.bookAuthors = (List<Author>) (List<?>) inputs.get(2);
		} 
		if (inputs.get(3) != null) { // Genres
			this.bookGenres = (List<Genre>) (List<?>) inputs.get(3);
		}
		if (inputs.get(4) != null) { // Library Branches
			this.bookBranches = (List<LibraryBranch>) (List<?>) inputs.get(4);
		} 
	}

	@Override
	public Boolean checkIfRequired(String str) {
		if (str != null) {
			switch (str) {
			case "Title":
				return true;
			case "Publisher ID":
				return false;
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
	public String getTableName() {
		return "Book";
	}

	@Override
	public String getName() {
		return getTitle();
	}

	public List<LibraryBranch> getBookLibBranches() {
		return bookLibBranches;
	}

	public void setBookLibBranches(List<LibraryBranch> bookLibBranches) {
		this.bookLibBranches = bookLibBranches;
	}

}
