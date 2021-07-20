/**
 * 
 */
package com.ss.lms.tests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.model.Author;
import com.ss.lms.service.AdminService;
import com.ss.lms.service.Util;

/**
 * @author Bruno
 *
 */
public class AuthorDAOTest {

	Connection conn = null;
	Util util = new Util();

	@Test
	public void testCreate() throws ClassNotFoundException, SQLException {
		Author toAdd = new Author();

		conn = util.getConnection();

		toAdd.setValues(Arrays.asList("Maya Angelou"));
		
		AuthorDAO adao = new AuthorDAO(conn);
		Integer pK = adao.createAuthor(toAdd);
		toAdd.setAuthorId(pK);
		
		Author result = adao.readAuthor(pK);
		
		Assert.assertEquals(toAdd, result);
		conn.rollback();
		conn.close();
	}
	
	

	
}
