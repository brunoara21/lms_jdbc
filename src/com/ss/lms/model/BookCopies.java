/**
 * 
 */
package com.ss.lms.model;

/**
 * @author Bruno
 *
 */
public class BookCopies {

		private LibraryBranch branch;	// FK
		private Book book;				// FK
		private Integer noOfCopies;
		
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
		
		
		
		
}
