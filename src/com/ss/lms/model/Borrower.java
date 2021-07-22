/**
 * 
 */
package com.ss.lms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ss.lms.service.Util;

/**
 * @borrower Bruno
 *
 */
public class Borrower extends BaseModel {

	private Integer cardNo; // PK
	private String borrowerName;
	private String borrowerAddress;
	private String borrowerPhone;

	private final static List<String> values = Arrays.asList("Name", "Address", "Phone");

	private List<Book> books;
	private List<LibraryBranch> libBranches;

	/**
	 * @param vals the values to set
	 */
	public void setValues(List<Object> vals) {
		if (vals.get(0) != null) {
			this.borrowerName = vals.get(0).toString();
		}
		if (vals.get(1) != null) {
			this.borrowerAddress = vals.get(1).toString();
		}
		if (vals.get(2) != null) {
			this.borrowerPhone = vals.get(2).toString();
		}
	}

	/**
	 * @return the borrowerPhone
	 */
	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	/**
	 * @param borrowerPhone the borrowerPhone to set
	 */
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

	/**
	 * @return the borrowerAddress
	 */
	public String getBorrowerAddress() {
		return borrowerAddress;
	}

	/**
	 * @param borrowerAddress the borrowerAddress to set
	 */
	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}

	/**
	 * @return the cardNo
	 */
	public Integer getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}

	/**
	 * @param borrowerName the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	// public static List<String> get

	@Override
	public int hashCode() {
		return Objects.hash(cardNo);
	}

	@Override
	public String toString() {

		return "Borrower CardNo: " + cardNo + "\nBorrower Name: "
				+ (borrowerName != null ? borrowerName : Util.fSysAlert.format("Name Empty"))
				+ Util.fSysOutput.format("\nBorrower Address: "
						+ (borrowerAddress != null ? borrowerAddress : Util.fSysAlert.format("Address Empty")))
				+ Util.fSysOutput.format("\nBorrower Phone: "
						+ (borrowerPhone != null ? borrowerPhone : Util.fSysAlert.format("Phone Empty")));
	}

	public String toStringTest() {

		return "Borrower CardNo: " + cardNo + "\nBorrower Name: " + (borrowerName != null ? borrowerName : "Name Empty")
				+ "\nBorrower Address: " + (borrowerAddress != null ? borrowerAddress : "Address Empty")
				+ "\nBorrower Phone: " + (borrowerPhone != null ? borrowerPhone : "Phone Empty");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		return Objects.equals(cardNo, other.cardNo);
	}

	public Boolean checkIfRequired(String str) {
		if (str != null) {
			switch (str) {
			case "Name":
				return false;
			case "Address":
				return false;
			case "Phone":
				return false;
			default:
				return false;
			}
		}
		return false;
	}

	public List<String> getValues() {
		return values;
	}

	@Override
	public String getTableName() {
		return "Borrower";
	}

	@Override
	public String getName() {
		return getBorrowerName();
	}

}
