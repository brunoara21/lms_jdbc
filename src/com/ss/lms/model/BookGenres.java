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
public class BookGenres extends BaseModel{

		private Genre genre;	// FK
		private Book book;				// FK
		
		private final static List<String> values = Arrays.asList("Genre", "Book");

		
		/**
		 * @return the genre
		 */
		public Genre getGenre() {
			return genre;
		}
		/**
		 * @param genre the genre to set
		 */
		public void setGenre(Genre genre) {
			this.genre = genre;
		}
		/**
		 * @return the book
		 */
		public Book getBook() {
			return book;
		}
		/**
		 * @param book the book to set
		 */
		public void setBook(Book book) {
			this.book = book;
		}
		
		@Override
		public void setValues(List<Object> inputs) {
			if (inputs.get(0) != null) {
				this.genre = (Genre) inputs.get(0);
			}
			if (inputs.get(1) != null) {
				this.book = (Book) inputs.get(1);
			}

		}
		@Override
		public Boolean checkIfRequired(String str) {
			if (str != null) {
				switch (str) {
				case "Genre":
					return true;
				case "Book":
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
		public String getTableName() {
			return "Book-Genres";
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(book, genre);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BookGenres other = (BookGenres) obj;
			return Objects.equals(book, other.book) && Objects.equals(genre, other.genre);
		}
		@Override
		public String getName() {
			
			return null;
		}

		
		
		
}
