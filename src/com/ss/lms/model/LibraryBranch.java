/**
 * 
 */
package com.ss.lms.model;

import java.util.List;

/**
 * @author Bruno
 *
 */
public class LibraryBranch {

		private Integer branchId;	// PK
		private String branchName;
		private String branchAddress;
		private List<BookCopies> bookCopies;
		//private List<BookLoans> bookLoans;
		
		/**
		 * @return the branchId
		 */
		public Integer getBranchId() {
			return branchId;
		}
		/**
		 * @param branchId the branchId to set
		 */
		public void setBranchId(Integer branchId) {
			this.branchId = branchId;
		}
		/**
		 * @return the branchName
		 */
		public String getBranchName() {
			return branchName;
		}
		/**
		 * @param branchName the branchName to set
		 */
		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}
		/**
		 * @return the branchAddress
		 */
		public String getBranchAddress() {
			return branchAddress;
		}
		/**
		 * @param branchAddress the branchAddress to set
		 */
		public void setBranchAddress(String branchAddress) {
			this.branchAddress = branchAddress;
		}
		/**
		 * @return the bookCopies
		 */
		public List<BookCopies> getBookCopies() {
			return bookCopies;
		}
		/**
		 * @param bookCopies the bookCopies to set
		 */
		public void setBookCopies(List<BookCopies> bookCopies) {
			this.bookCopies = bookCopies;
		}
		
}
