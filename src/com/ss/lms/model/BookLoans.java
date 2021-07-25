/**
 * 
 */
package com.ss.lms.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bruno
 *
 */
public class BookLoans extends BaseModel{

		private Book book;				// FK
		private LibraryBranch branch;	// FK
		private Borrower borrower;	// FK
		
		private Timestamp dateOut;
		private Timestamp dueDate;
		private Timestamp dateIn;
		
		private final static List<String> values = Arrays.asList("Book", "Library Branch", "Borrower", "Date Out", "Due Date", "Date In");


		@Override
		public Boolean checkIfRequired(String str) {
			if (str != null) {
				switch (str) {
				case "Book":
					return true;
				case "Library Branch":
					return true;
				case "Borrower":
					return true;
				case "Date Out":
					return false;
				case "Due Date":
					return false;
				case "Date In":
					return false;
				default:
					return false;
				}
			}
			return false;
		}
		@Override
		public void setValues(List<Object> inputs) {
			if (inputs.get(0) != null) {
				this.book = (Book) inputs.get(0);
			}
			if (inputs.get(1) != null) {
				this.branch = (LibraryBranch) inputs.get(1);
			}
			if (inputs.get(2) != null) {
				this.borrower = (Borrower) inputs.get(2);
			}	
			if (inputs.get(3) != null) {
				this.dateOut = (Timestamp)inputs.get(3);
			}
			if (inputs.get(4) != null) {
				this.dueDate = (Timestamp) inputs.get(4);
			}
			if (inputs.get(5) != null) {
				this.dateIn = (Timestamp) inputs.get(5);
			}
		}
		/**
		 * @return the dateOut
		 */
		public Timestamp getDateOut() {
			return dateOut;
		}
		/**
		 * @param dateOut the dateOut to set
		 */
		public void setDateOut(Timestamp dateOut) {
			this.dateOut = dateOut;
		}
		/**
		 * @return the dueDate
		 */
		public Timestamp getDueDate() {
			return dueDate;
		}
		/**
		 * @param dueDate the dueDate to set
		 */
		public void setDueDate(Timestamp dueDate) {
			this.dueDate = dueDate;
		}
		/**
		 * @return the dateIn
		 */
		public Timestamp getDateIn() {
			return dateIn;
		}
		/**
		 * @param dateIn the dateIn to set
		 */
		public void setDateIn(Timestamp dateIn) {
			this.dateIn = dateIn;
		}
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
		
		
		@Override
		public List<String> getValues() {
			return values;
		}
		@Override
		public String getTableName() {
			return "Book Loans";
		}
		@Override
		public String getName() {
			return null;
		}
		/**
		 * @return the borrower
		 */
		public Borrower getBorrower() {
			return borrower;
		}
		/**
		 * @param borrower the borrower to set
		 */
		public void setBorrower(Borrower borrower) {
			this.borrower = borrower;
		}
		
		
		
		
}
