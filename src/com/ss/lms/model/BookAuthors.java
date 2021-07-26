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
public class BookAuthors extends BaseModel{

		private Author author;	// FK
		private Book book;				// FK
		
		private final static List<String> values = Arrays.asList("Author", "Book");

		
		/**
		 * @return the author
		 */
		public Author getAuthor() {
			return author;
		}
		/**
		 * @param author the author to set
		 */
		public void setAuthor(Author author) {
			this.author = author;
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
				this.author = (Author) inputs.get(0);
			}
			if (inputs.get(1) != null) {
				this.book = (Book) inputs.get(1);
			}

		}
		@Override
		public Boolean checkIfRequired(String str) {
			if (str != null) {
				switch (str) {
				case "Author":
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
			return "Book-Authors";
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(book, author);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BookAuthors other = (BookAuthors) obj;
			return Objects.equals(book, other.book) && Objects.equals(author, other.author);
		}
		@Override
		public String getName() {
			
			return null;
		}
		
		
		
		
}
