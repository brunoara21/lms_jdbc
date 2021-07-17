/**
 * 
 */
package com.ss.lms.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Bruno
 *
 */
public class Book {

		private Integer bookId;			// PK
		//private Publisher branch;		// FK
		private String title;
		private List<BookCopies> bookCopies;
		
		
		
		
		
		
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
		
		
}
