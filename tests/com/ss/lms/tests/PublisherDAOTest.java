/**
 * 
 */
package com.ss.lms.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.model.Publisher;
import com.ss.lms.service.Util;

/**
 * @publisher Bruno
 *
 */
public class PublisherDAOTest {

	private Connection conn = null;
	private Util util = new Util();

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		conn = util.getConnection();
	}
	
	@After
	public void tearDown() throws ClassNotFoundException, SQLException {
		conn.rollback();
		conn.close();	
		}
	
	
	@Test
	public void test_column_values() {
		List<String> columnNames = Arrays.asList("Name", "Address", "Phone");
		
		Publisher publisher = new Publisher();
		List<String> columns = publisher.getValues();
		
		Assert.assertEquals(columns, columnNames);
	}
	
	
	@Test
	public void test_column_requirements() {
		Publisher publisher = new Publisher();
		List<String> columnNames = Arrays.asList("Name", "Address", "Phone");
		List<String> columns = publisher.getValues();
		
		Assert.assertEquals(publisher.checkIfRequired(null), false);
		Assert.assertEquals(publisher.checkIfRequired("Full Name"), false);

		Assert.assertEquals(publisher.checkIfRequired(columns.get(0)), true);
		Assert.assertEquals(columns.get(0), columnNames.get(0));	
		Assert.assertEquals(publisher.checkIfRequired(columns.get(1)), false);
		Assert.assertEquals(columns.get(1), columnNames.get(1));	
		Assert.assertEquals(publisher.checkIfRequired(columns.get(2)), false);
		Assert.assertEquals(columns.get(2), columnNames.get(2));	
	}
	
	@Test
	public void test_add_publisher() throws ClassNotFoundException, SQLException {
		Publisher toAdd = new Publisher();

		String publisherName = "Hachette Book Group";
		String publisherAddress = "1290 6th Ave";
		String publisherPhone = "212-364-1100";
		toAdd.setValues(Arrays.asList(publisherName, publisherAddress, publisherPhone));
		
		PublisherDAO pdao = new PublisherDAO(conn);
		Integer pK = pdao.createPublisher(toAdd);
		toAdd.setPublisherId(pK);
		
		Publisher result = pdao.readPublisher(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Publisher ID: " + pK + 
				"\nPublisher Name: " + publisherName + 
				"\nPublisher Address: " + publisherAddress +
				"\nPublisher Phone: " + publisherPhone , result.toStringTest());
	}
	
	@Test
	public void test_add_publisher_address_null() throws ClassNotFoundException, SQLException {
		Publisher toAdd = new Publisher();

		String publisherName = "Hachette Book Group";
		String publisherAddress = null;
		String publisherPhone = "212-364-1100";
		toAdd.setValues(Arrays.asList(publisherName, publisherAddress, publisherPhone));
		
		PublisherDAO pdao = new PublisherDAO(conn);
		Integer pK = pdao.createPublisher(toAdd);
		toAdd.setPublisherId(pK);
		
		Publisher result = pdao.readPublisher(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Publisher ID: " + pK + 
				"\nPublisher Name: " + publisherName + 
				"\nPublisher Address: " +  "Address Empty" +
				"\nPublisher Phone: " + publisherPhone  , result.toStringTest());
	}
	
	@Test
	public void test_add_publisher_all_null() throws ClassNotFoundException, SQLException {
		Publisher toAdd = new Publisher();

		String publisherName = "Hachette Book Group";
		String publisherAddress = null;
		String publisherPhone = null;
		toAdd.setValues(Arrays.asList(publisherName, publisherAddress, publisherPhone));
		
		PublisherDAO pdao = new PublisherDAO(conn);
		Integer pK = pdao.createPublisher(toAdd);
		toAdd.setPublisherId(pK);
		
		Publisher result = pdao.readPublisher(pK);
		
		Assert.assertEquals(toAdd, result);
		Assert.assertEquals("Publisher ID: " + pK + 
				"\nPublisher Name: " + publisherName + 
				"\nPublisher Address: " + "Address Empty" +
				"\nPublisher Phone: " + "Phone Empty" , result.toStringTest());
	}
	
	@Test
	public void test_update_publisher() throws ClassNotFoundException, SQLException {
		PublisherDAO pdao = new PublisherDAO(conn);
		Publisher toUpdate = pdao.readPublisher(101);

		toUpdate.setValues(Arrays.asList("Macmillan Publishers", "129 McConnell Ave", "740-576-1122"));
		
		pdao.updatePublisher(toUpdate);
		Publisher result = pdao.readPublisher(101);
		
		Assert.assertEquals(toUpdate, result);
	}
	
	@Test
	public void test_delete_publisher() throws ClassNotFoundException, SQLException {
		PublisherDAO pdao = new PublisherDAO(conn);
		Publisher toDelete = pdao.readPublisher(101);
		
		pdao.deletePublisher(toDelete);
		Publisher result = pdao.readPublisher(101);
		
		Assert.assertNotEquals(toDelete, result);
		Assert.assertNull(result);
	}
	
	@Test
	public void test_read_all_publishers_returns_size() throws ClassNotFoundException, SQLException {
		PublisherDAO pdao = new PublisherDAO(conn);
		List<Publisher> publishers = pdao.readAllPublishers();
				
		Assert.assertEquals(publishers.size(), 102);
	}
	
	@Test
	public void test_read_all_publishers_by_name() throws ClassNotFoundException, SQLException {
		PublisherDAO pdao = new PublisherDAO(conn);
		List<Publisher> publishers = pdao.readAllPublishers("con");
		Publisher publisher1 = pdao.readPublisher(23); // Herzog, Conroy and Herzog
		Publisher publisher2 = pdao.readPublisher(42); // Connelly-Bahringer
		Publisher publisher3 = pdao.readPublisher(92); // Kautzer, Conn and Dickinson
		
		
		Assert.assertEquals(3, publishers.size());
		Assert.assertEquals(publishers.get(0), publisher1);
		Assert.assertEquals(publishers.get(1), publisher2);
		Assert.assertEquals(publishers.get(2), publisher3);
	}
	
	/*
	 * @Test(expected = Exception.class) public void t() {
	 * 
	 * }
	 */

	
}
