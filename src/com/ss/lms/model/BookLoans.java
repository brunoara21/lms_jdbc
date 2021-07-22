/**
 * 
 */
package com.ss.lms.model;

/**
 * @author Bruno
 *
 */
public class BookLoans {

		private LibraryBranch branch;	// FK
		private Book book;				// FK
		private Integer noOfLoans;
		
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
		 * @return the noOfLoans
		 */
		public Integer getNoOfLoans() {
			return noOfLoans;
		}
		/**
		 * @param noOfLoans the noOfLoans to set
		 */
		public void setNoOfLoans(Integer noOfLoans) {
			this.noOfLoans = noOfLoans;
		}
		
		
		
		
}
