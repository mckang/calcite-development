package org.apache.calcite.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class MySqlTest {
	@Test
	public void Test1() throws Exception {
		Connection connection =
				DriverManager
					.getConnection(
							"jdbc:mysql://10.211.27.0/test?user=root&password=skyline01");
		
		Statement statement = connection.createStatement();
		boolean status = false;
		
		status = statement.execute("DROP TABLE IF EXISTS temp");
		System.out.println("DROP TABLE: " + status);
		assertFalse(status);
		
		status = statement.execute("CREATE TABLE temp (id INT, name VARCHAR(255))");
		System.out.println("CREATE TABLE: " + status);
		assertFalse(status);
		
		status = statement.execute("INSERT INTO temp VALUES (1, 'YeongWei')");
		System.out.println("INSERT INTO: " + status);
		assertFalse(status);
		
		ResultSet rs = statement.getResultSet();
		System.out.println("RESULTSET: " + rs);
		if(rs == null) {
			System.out.println("RESULTSET is null. Do not evaluate.");
		} else {
			System.out.println("RESULTSET#isClosed" + rs.isClosed());
		}
		
		status = statement.execute("SELECT * FROM temp");
		System.out.println("SELECT FROM: " + status);
		assertTrue(status);
	}
}
