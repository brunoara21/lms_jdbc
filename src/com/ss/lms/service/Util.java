/**
 * 
 */
package com.ss.lms.service;

import static com.diogonunes.jcolor.Attribute.BOLD;
import static com.diogonunes.jcolor.Attribute.BRIGHT_WHITE_TEXT;
import static com.diogonunes.jcolor.Attribute.ITALIC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

/**
 * @author Bruno
 *
 */
public class Util {

	public static final AnsiFormat fSysAlert = new AnsiFormat(Attribute.BRIGHT_RED_TEXT(), BOLD());
	public static final AnsiFormat fSysMessage = new AnsiFormat(BRIGHT_WHITE_TEXT(), BOLD());
	public static final AnsiFormat fSysOption = new AnsiFormat(BRIGHT_WHITE_TEXT(), ITALIC());
	public static final AnsiFormat fSysOutput = new AnsiFormat(Attribute.CYAN_TEXT());
	public static final AnsiFormat fAdminMessage = new AnsiFormat(Attribute.TEXT_COLOR(255, 165, 0), BOLD());
	public static final AnsiFormat fAdminOption = new AnsiFormat(Attribute.TEXT_COLOR(255, 165, 0), ITALIC());

	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/library";
	private final String username = "root";
	private final String password = "rooT20!21Root";

	private final String testDriver = "org.h2.Driver";
	private final String testUrl = "jdbc:h2:tcp://localhost/server~/test;MODE=MySQL";
	private final String testUsername = "sa";
	private final String testPassword = "";

	private Connection conn = null;
	private Connection testConn = null;

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		return conn;
	}

	public Connection getTestConnection() throws ClassNotFoundException, SQLException {
		Class.forName(testDriver);
		testConn = DriverManager.getConnection(testUrl, testUsername, testPassword);
		testConn.setAutoCommit(false);
		return testConn;
	}
}
