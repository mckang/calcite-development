package org.apache.calcite.avatica;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvaticaStatementTest extends AvaticaBaseTest {

	@Test public void createStatement() throws Exception {
		showBorder("createStatement");
		Statement statement = connection.createStatement();
		assertEquals(false, statement.isClosed());
		showBorder("createStatement");
	}
	
	@Test public void createStatementAndExecuteQuery() throws Exception {
	  showBorder("createStatement");
		Statement statement = connection.createStatement();
		assertEquals(false, statement.isClosed());
		ResultSet resultSet = statement.executeQuery("select * from depts");
		assertEquals(false, resultSet.isClosed());
		int count = 0;
		while (resultSet.next()) {
			count += 1;
		}
		assertTrue(count > 0);
		resultSet.close();
		assertEquals(true, resultSet.isClosed());
		showBorder("createStatement");
	}
	
	@Test public void createStatementAndExecuteQueryWithBigData()
	    throws Exception {
    showBorder("createStatementAndExecuteQueryWithBigData");
    Statement statement = connection.createStatement();
    assertEquals(false, statement.isClosed());
    ResultSet resultSet = statement.executeQuery("select * from bigdata");
    assertEquals(false, resultSet.isClosed());
    int count = 0;
    while (resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 100);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("createStatementAndExecuteQueryWithBigData");
  }
	
	@Test public void inspectMetadataOfClosedResultSet() throws Exception {
	  showBorder("inspectMetadataOfClosedResultSet");
	  Statement statement = connection.createStatement();
    assertEquals(false, statement.isClosed());
    ResultSet resultSet = statement.executeQuery("select * from bigdata");
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    assertEquals(1, resultSet.getMetaData().getColumnCount());
	  showBorder("inspectMetadataOfClosedResultSet");
	}
	
	@Test public void inspectMetadataOfClosedStatement() throws Exception {
	  showBorder("inspectMetadataOfClosedStatement");
	  Statement statement = connection.createStatement();
    assertEquals(false, statement.isClosed());
    ResultSet resultSet = statement.executeQuery("select * from bigdata");
    assertEquals(false, resultSet.isClosed());
    statement.close();
    assertEquals(true, statement.isClosed());
    assertEquals(true, resultSet.isClosed());
    assertEquals(1, resultSet.getMetaData().getColumnCount());
	  showBorder("inspectMetadataOfClosedStatement");
	}

}
