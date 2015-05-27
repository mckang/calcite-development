package org.apache.calcite.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Ignore;
import org.junit.Test;

public class Calcite666 {
  protected Connection connection = TnpmConnectionFactory
      .getCalciteLocalConnection(null);
  
  protected Connection mysqlConnection = TnpmConnectionFactory
      .getCalciteLocalConnection(TnpmConnectionFactory.jdbcModel);
  
  protected Connection hsqldbConnection = TnpmConnectionFactory
      .getCalciteLocalConnection(TnpmConnectionFactory.hsqldbModel);
  
  @Ignore
  @Test
  public void Test1() throws Exception {
    String sql = "SELECT * FROM \"tjoin1\" where c1 <> (select c1 from \"tjoin2\" where rnum = 0)";
    ResultSet rs = mysqlConnection.createStatement().executeQuery(sql);
  }
  
  @Test
  public void Test2() throws Exception {
    String sql = "SELECT * FROM TJOIN1 where C1 <> (select C1 from TJOIN2 where RNUM = 0)";
    ResultSet rs = hsqldbConnection.createStatement().executeQuery(sql);
    assertTrue(rs.next());
    assertTrue(rs.getObject(1).equals(1));
    assertTrue(rs.getObject(2).equals(20));
    assertTrue(rs.getObject(3).equals(25));
    assertFalse(rs.next());
  }
  
  @Test
  public void Test3() throws Exception {
    String sql = "SELECT * FROM TJOIN1 where C1 = (select C1 from TJOIN2 where RNUM = 0)";
    ResultSet rs = hsqldbConnection.createStatement().executeQuery(sql);
    assertTrue(rs.next());
    assertTrue(rs.getObject(1).equals(0));
    assertTrue(rs.getObject(2).equals(10));
    assertTrue(rs.getObject(3).equals(15));
    assertFalse(rs.next());
  }
  
  @Test
  public void Test4() throws Exception {
    String sql = "SELECT * FROM TJOIN1 where C1 < (select C1 from TJOIN2 where RNUM = 0)";
    ResultSet rs = hsqldbConnection.createStatement().executeQuery(sql);
    assertFalse(rs.next());
  }
  
  @Test
  public void Test5() throws Exception {
    String sql = "SELECT * FROM TJOIN1 where C1 > (select C1 from TJOIN2 where RNUM = 0)";
    ResultSet rs = hsqldbConnection.createStatement().executeQuery(sql);
    assertTrue(rs.next());
    assertTrue(rs.getObject(1).equals(1));
    assertTrue(rs.getObject(2).equals(20));
    assertTrue(rs.getObject(3).equals(25));
    assertFalse(rs.next());
  }
  
  @Ignore
  @Test
  public void Test6() throws Exception {
    String sql = "SELECT * FROM TJOIN1 where C1 <> (select C1 from TJOIN2 where RNUM = 0)";
    ResultSet rs = connection.createStatement().executeQuery(sql);
  }
}
