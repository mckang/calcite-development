package org.apache.calcite.cognos;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Ignore;
import org.junit.Test;

public class CognosCertification102 {
  @Ignore
  @Test public void testCognosCertification102_1() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet =
          connection
            .createStatement()
              .executeQuery("select EMPNO, EMPS.DEPTNO, DEPTS.DEPTNO "
                  + "from EMPS inner join DEPTS on EMPS.DEPTNO = DEPTS.DEPTNO "
                  + "where EMPS.NAME = 'Fred' and DEPTS.NAME = 'Sales'");
      int count = 0;
      while(resultSet.next()) {
        //System.out.println(resultSet.getObject(1) + "|_|" + resultSet.getObject(2));
        count += 1;
      }
      assertTrue(count > 0);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  @Ignore
  @Test public void testCognosCertification102_2() {    
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet =
          connection
            .createStatement()
              .executeQuery("select DEPTNO from DEPTS,"
                  + "lateral (select DEPTNAME from DEPTS) DEPTS2");
      int count = 0;
      while(resultSet.next()) {
        count += 1;
      }
      //assertTrue(count > 0);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_3() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet =
          connection
            .createStatement()
              //.executeQuery("values (TIME'20:17:40', TIME'23:59:30.123000000')");
            .executeQuery("select * from tversion");
      int count = 0;
      while(resultSet.next()) {
        count += 1;        
        String prints = "";
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
          prints += resultSet.getObject(i) + "|_|";
        }
      }
      assertTrue(count > 0);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_4() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet =
          connection
            .createStatement()
            .executeQuery("select 1 from TVERSION where TIME '23:59:40' > TIME '23:59:30.123000000'");//true
      int count = 0;
      while(resultSet.next()) {
        count += 1;        
      }
      assertTrue(count == 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_5() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet =
          connection
            .createStatement()
            .executeQuery("select 1 from TVERSION where TIME '23:59:30.123000000' < TIME '23:59:40'");//false
      int count = 0;
      while(resultSet.next()) {
        count += 1;       
      }
      assertTrue(count == 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_6() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select * from TVERSION");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;       
      }
      assertTrue(count == 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_7() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select TIME '23:59:00' - TIME '23:59:00' from TVERSION");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;       
        System.out.println(resultSet.getObject(1));
      }
      assertTrue(count == 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_8() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select TIMESTAMP '1999-12-31 23:59:00' - TIMESTAMP '1990-01-01 23:59:00' from TVERSION");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;       
        //System.out.println(resultSet.getObject(1));
      }
      assertTrue(count == 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_9() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select * from TTM");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;    
        System.out.println(resultSet.getObject(1) + "|_|" + resultSet.getObject(2));
      }
      assertTrue(count > 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_10() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select TTM.RNUM,cast(TTM.CTM as varchar(100)) from TTM ");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;    
        /*
        System.out.println(
            resultSet.getObject(1) + "|_|" + resultSet.getMetaData().getColumnClassName(1) + 
            resultSet.getObject(2) + "|_|" + resultSet.getMetaData().getColumnClassName(2));
        */
      }
      assertTrue(count > 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_11() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      PreparedStatement preparedStatement =
          connection.prepareStatement("select TTM.RNUM,cast(TTM.CTM as TIMESTAMP) from TTM ");
      boolean status = preparedStatement.execute();
      assertEquals(true, status);
      ResultSet resultSet = preparedStatement.getResultSet();
      int count = 0;
      while(resultSet.next()) {
        count += 1;    
      }
      assertTrue(count > 1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  @Ignore
  @Test public void testCognosCertification102_12() {
      try {
        Connection connection =
            TnpmConnectionFactory.getCalciteLocalConnection(null);
        String hijackSql = "select ID from COGNOS where WEIGHT = 10";
        String fullSql = "";
        fullSql += "select * from COGNOS where ";
        fullSql += "ID = ( ";
        fullSql +=      "case ( select count(*) from (" + hijackSql + ") tbl ) ";
        fullSql +=          "when 1 then ( " + hijackSql + " ) ";
        fullSql +=          "else null ";
        fullSql +=      "end ";
        fullSql += ")";
        System.out.println(fullSql);
        PreparedStatement preparedStatement =
            connection.prepareStatement(fullSql);
        boolean status = preparedStatement.execute();
        assertEquals(true, status);
        ResultSet resultSet = preparedStatement.getResultSet();
        int count = 0;
        while(resultSet.next()) {
          count += 1; 
          System.out.println(resultSet.getObject(1) + "|_|" + resultSet.getObject(2));
        }
        assertTrue(count > 1);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    }
 
  @Test public void testCognosCertification102_13() {
      try {
        Connection connection =
            TnpmConnectionFactory.getCalciteLocalConnection(null);
        
        String fullSql = "";
        fullSql += "SELECT * FROM tVersion";
        ResultSet resultSet = connection.createStatement().executeQuery(fullSql);
        int count = 0;
        while(resultSet.next()) {
          count += 1; 
          System.out.println(resultSet.getObject(1) + "|_|" + resultSet.getObject(2));
        }
        assertTrue(count > 0);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    }
}
