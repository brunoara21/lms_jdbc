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
public class Author {

		private Integer authorId;		// PK
		private String authorName;
		private List<Book> books;
		
		
		
		
		
		
		/**
		 * @param vals the values to set
		 */
		public void setAuthor(List<Object> vals) {
			if(vals.get(0) != null) {
				this.authorName = vals.get(0).toString();
			}
		}
		
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
		
		
}
