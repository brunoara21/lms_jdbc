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
public class LibraryBranch extends BaseModel{

		private Integer branchId;	// PK
		private String branchName;
		private String branchAddress;
		private List<BookCopies> bookCopies;
		private List<BookLoans> bookLoans;
		
		private final static List<String> values = Arrays.asList("Name", "Address");

		
		
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
		
		@Override
		public String toString() {

			return "Library Branch ID: " + branchId + "\nLibrary Branch Name: "
					+ (branchName != null ? branchName : Util.fSysAlert.format("Name Empty"))
					+ Util.fSysOutput.format("\nLibrary Branch Address: "
							+ (branchAddress != null ? branchAddress : Util.fSysAlert.format("Address Empty")));
		}

		public String toStringTest() {

			return "Library Branch ID: " + branchId + "\nLibrary Branch Name: " + (branchName != null ? branchName : "Name Empty")
					+ "\nLibrary Branch Address: " + (branchAddress != null ? branchAddress : "Address Empty");
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(branchId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LibraryBranch other = (LibraryBranch) obj;
			return Objects.equals(branchId, other.branchId);
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
		@Override
		public void setValues(List<Object> inputs) {
			if (inputs.get(0) != null) {
				this.branchName = inputs.get(0).toString();
			} 
			if (inputs.get(1) != null) {
				this.branchAddress = inputs.get(1).toString();
			}		
		}
		@Override
		public Boolean checkIfRequired(String str) {
			if (str != null) {
				switch (str) {
				case "Name":
					return false;
				case "Address":
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
			return "Library Branch";
		}
		@Override
		public String getName() {
			return getBranchName();
		}
		/**
		 * @return the bookLoans
		 */
		public List<BookLoans> getBookLoans() {
			return bookLoans;
		}
		/**
		 * @param bookLoans the bookLoans to set
		 */
		public void setBookLoans(List<BookLoans> bookLoans) {
			this.bookLoans = bookLoans;
		}
		
}
