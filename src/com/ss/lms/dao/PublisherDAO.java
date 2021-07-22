/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ss.lms.model.Publisher;

/**
 * @publisher Bruno
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public Integer createPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		
		return prepareStmtReturnPK("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES(?,?,?)",
				Arrays.asList(publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()));
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		prepareStmt("UPDATE tbl_publisher "
				+ "SET publisherName = ?, publisherAddress = ?, publisherPhone = ?"
				+ "WHERE publisherId = ?",
				Arrays.asList(publisher.getPublisherName(), 
						publisher.getPublisherAddress(),
						publisher.getPublisherPhone(),
						publisher.getPublisherId() ));
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		prepareStmt("DELETE FROM tbl_publisher WHERE publisherId = ?", Arrays.asList(publisher.getPublisherId()));

	}

	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_publisher", null);
	}

	public Publisher readPublisher(Integer publisherId) throws ClassNotFoundException, SQLException {
		return readStmtOne("SELECT * FROM tbl_publisher WHERE publisherId = ?", Arrays.asList(publisherId));
	}

	public List<Publisher> readAllPublishers(String searchName) throws ClassNotFoundException, SQLException {
		return readStmt("SELECT * FROM tbl_publisher WHERE publisherName LIKE ?", Arrays.asList("%" + searchName +"%"));
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

	@Override
	public Publisher extractDataOne(ResultSet rs) throws ClassNotFoundException, SQLException {
		Publisher publisher = null;
		while (rs.next()) {
			publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
		}
		return publisher;
	}
}
