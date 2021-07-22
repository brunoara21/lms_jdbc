/**
 * 
 */
package com.ss.lms.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ss.lms.service.Util;

/**
 * @publisher Bruno
 *
 */
public class Publisher extends BaseModel {

	private Integer publisherId; // PK
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;

	private final static List<String> values = Arrays.asList("Name", "Address", "Phone");

	private List<Book> books;

	/**
	 * @param vals the values to set
	 */
	public void setValues(List<Object> vals) {
		if (vals.get(0) != null) {
			this.publisherName = vals.get(0).toString();
		}
		if (vals.get(1) != null) {
			this.publisherAddress = vals.get(1).toString();
		}
		if (vals.get(2) != null) {
			this.publisherPhone = vals.get(2).toString();
		}
	}

	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}

	/**
	 * @param publisherPhone the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}

	/**
	 * @param publisherAddress the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	/**
	 * @return the publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}

	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	// public static List<String> get

	@Override
	public int hashCode() {
		return Objects.hash(publisherId);
	}

	@Override
	public String toString() {

		return "Publisher ID: " + publisherId + "\nPublisher Name: " + publisherName + "\nPublisher Address: "
				+ (publisherAddress != null ? publisherAddress : Util.fSysAlert.format("Address Empty"))
				+ Util.fSysOutput.format("\nPublisher Phone: "
						+ (publisherPhone != null ? publisherPhone : Util.fSysAlert.format("Phone Empty")));
	}

	public String toStringTest() {

		return "Publisher ID: " + publisherId + "\nPublisher Name: " + publisherName + 
				"\nPublisher Address: "+ (publisherAddress != null ? publisherAddress : "Address Empty")
				+ "\nPublisher Phone: "+ (publisherPhone != null ? publisherPhone :"Phone Empty");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		return Objects.equals(publisherId, other.publisherId);
	}

	public Boolean checkIfRequired(String str) {
		if (str != null) {
			switch (str) {
			case "Name":
				return true;
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
		return "Publisher";
	}

	@Override
	public String getName() {
		return getPublisherName();
	}

}
