package org.apache.calcite.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Calcite259 {
  protected Connection connection = TnpmConnectionFactory
      .getCalciteLocalConnection(null);
  
  protected Connection mysqlConnection = TnpmConnectionFactory
      .getCalciteLocalConnection(TnpmConnectionFactory.jdbcModel);
  
  @Ignore
  @Test (expected = SQLException.class)
  public void Test1() throws Exception {
      String sql = "select * from \"tjoin1\" "
          + "where c1 = (select c2 from \"tjoin2\")";
      connection.createStatement().executeQuery(sql);
  }
  
  @Test
  public void Test2() throws Exception {
      String sql = "select * from \"tjoin1\" "
          + "where c1 = (select c1 from \"tjoin2\" where c2 = 'BB')";
      ResultSet rs = mysqlConnection.createStatement().executeQuery(sql);
      int count = 0;
      while (rs.next()) {
//        System.out.println("[DEBUG]: " + rs.getObject(1) + "|_|" +
//           rs.getObject(2) + "|_|" + rs.getObject(3));
        count += 1;
      }
      assertTrue(count > 0);
  }
  
  @Rule 
  public ExpectedException expectedEx = ExpectedException.none();
  
  @Test(expected=SQLException.class)
  public void Test3() throws Exception {
    try {
      String sql = "SELECT \"RNUM\" FROM \"tjoin1\" "
        + "WHERE \"C1\" = (SELECT \"C1\" FROM \"tjoin2\")";
      mysqlConnection.createStatement().executeQuery(sql);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
}
