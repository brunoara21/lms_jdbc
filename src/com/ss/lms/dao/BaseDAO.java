/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Bruno
 * @param <T>
 *
 */
public abstract class BaseDAO<T> {

	protected Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	public Integer prepareStmtReturnPK(String sql, List<Object> vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		Integer i = 1;
		for (Object o : vals) {
			pstmt.setObject(i, o);
			i++;
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	public void prepareStmt(String sql, List<Object> vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Integer i = 1;
		for (Object o : vals) {
			pstmt.setObject(i, o);
			i++;
		}
		pstmt.executeUpdate();
	}

	public List<T> readStmt(String sql, List<Object> vals) throws ClassNotFoundException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (vals != null) {
			Integer i = 1;
			for (Object o : vals) {
				pstmt.setObject(i, o);
				i++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}

	abstract public List<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException;
}
