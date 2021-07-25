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
public class BookCopies extends BaseModel {

		private LibraryBranch branch;	// FK
		private Book book;				// FK
		private Integer noOfCopies;
		
		private final static List<String> values = Arrays.asList("Book", "Library Branch", "Copies");

		
		/**
		 * @return the branch
		 */
		public LibraryBranch getBranch() {
			return branch;
		}
		/**
		 * @param branch the branch to set
		 */
		public void setBranch(LibraryBranch branch) {
			this.branch = branch;
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
		/**
		 * @return the noOfCopies
		 */
		public Integer getNoOfCopies() {
			return noOfCopies;
		}
		/**
		 * @param noOfCopies the noOfCopies to set
		 */
		public void setNoOfCopies(Integer noOfCopies) {
			this.noOfCopies = noOfCopies;
		}
		@Override
		public void setValues(List<Object> inputs) {
			if (inputs.get(0) != null) {
				this.book = (Book) inputs.get(0);
			}
			if (inputs.get(1) != null) {
				this.branch= (LibraryBranch) inputs.get(1);
			}
			if (inputs.get(2) != null) {
				this.noOfCopies = (Integer)inputs.get(2);
			}			
		}
		@Override
		public Boolean checkIfRequired(String str) {
			if (str != null) {
				switch (str) {
				case "Book":
					return true;
				case "Library Branch":
					return true;
				case "Copies":
					return false;
				default:
					return false;
				}
			}

			return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(book, branch);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BookCopies other = (BookCopies) obj;
			return Objects.equals(book, other.book) && Objects.equals(branch, other.branch);
		}
		@Override
		public List<String> getValues() {
			return values;
		}
		@Override
		public String getTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
		
}
