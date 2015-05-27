package org.apache.calcite.avatica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class AvaticaPreparedStatementTest extends AvaticaBaseTest {
  
  @Test public void preparedStatementaAndExecuteQuery()
    throws Exception {
    showBorder("preparedStatementaAndExecuteQuery");
    PreparedStatement preparedStatement = connection
        .prepareStatement("select * from depts where 100 = ? and 200 = ?");
    preparedStatement.setInt(1, 100);
    preparedStatement.setInt(2, 200);
      
    ResultSet resultSet = preparedStatement.executeQuery();
    int count = 0;
    while (resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("preparedStatementaAndExecuteQuery");
  }
  
  @Test public void preparedStatementaAndExecuteQuery1()
      throws Exception {
      showBorder("preparedStatementaAndExecuteQuery1");
      PreparedStatement preparedStatement = connection
          .prepareStatement(
              "select * from depts where 100 = ? and 200 = ?",
              ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
      preparedStatement.setInt(1, 100);
      preparedStatement.setInt(2, 200);
      ResultSet resultSet = preparedStatement.executeQuery();
      int count = 0;
      while (resultSet.next()) {
        count += 1;
      }
      assertTrue(count > 0);
      resultSet.close();
      assertEquals(true, resultSet.isClosed());
      showBorder("preparedStatementaAndExecuteQuery1");
    }
  
  @Test public void preparedStatementaAndExecuteQuery2()
      throws Exception {
    showBorder("testPreparedStatementaAndExecuteQuery2");
    PreparedStatement preparedStatement = connection
        .prepareStatement(
            "select * from depts where 100 = ? and 200 = ?",
            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
            ResultSet.HOLD_CURSORS_OVER_COMMIT);
    preparedStatement.setInt(1, 100);
    preparedStatement.setInt(2, 200);
    ResultSet resultSet = preparedStatement.executeQuery();
    int count = 0;
    while (resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("testPreparedStatementaAndExecuteQuery2");
  }
  
  @Test public void preparedStatementaAndExecute() throws Exception {
    showBorder("preparedStatementaAndExecute");
    PreparedStatement preparedStatement = 
        connection.prepareStatement("select * from depts");
    boolean status = preparedStatement.execute();
    assertEquals(true, status);
    ResultSet resultSet = preparedStatement.getResultSet();
    int count = 0;
    while(resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("preparedStatementaAndExecute");
  }
  
  @Test public void preparedStatementaAndExecuteWithBigData() throws Exception {
    showBorder("preparedStatementaAndExecuteWithBigData");
    PreparedStatement preparedStatement = 
        connection.prepareStatement("select * from BIGDATA");
    ResultSet resultSet = preparedStatement.executeQuery();
    int count = 0;
    while(resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 100);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("preparedStatementaAndExecuteWithBigData");
  }
  
  @Test public void preparedStatementaAndExecuteWithBigData1() 
      throws Exception {
    showBorder("preparedStatementaAndExecuteWithBigData1");
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from bigdata");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      assertEquals(false, resultSet.isClosed());
      int count = 0;
      while(resultSet.next()) {
        count += 1;
      }
      assertTrue(count > 100);
      resultSet.close();
      assertEquals(true, resultSet.isClosed());
    showBorder("preparedStatementaAndExecuteWithBigData1");
  }
  
  @Test public void inspectMetadataOfClosedResultSet() throws Exception {
    showBorder("inspectMetadataOfClosedResultSet");
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from bigdata");
    ResultSet resultSet = preparedStatement.executeQuery();
    assertEquals(false, resultSet.isClosed());
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    assertEquals(1, resultSet.getMetaData().getColumnCount());
    showBorder("inspectMetadataOfClosedResultSet");
  }
  
  /*
   * CALCITE-646
   * Submit to GitHub
   */
  @Test public void testRemotePreparedStatementExecute() throws Exception {
    showBorder("testRemotePreparedStatementExecute");
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from (values (1, 2))");
    boolean status = preparedStatement.execute();
    assertEquals(true, status);
    ResultSet resultSet = preparedStatement.getResultSet();
    assertEquals(false, resultSet.isClosed());
    int count = 0;
    while(resultSet.next()) {
      count += 1;
    }
    assertEquals(1, count);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("testRemotePreparedStatementExecute");
  }
}
