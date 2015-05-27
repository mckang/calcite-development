package org.apache.calcite.test;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Ignore;
import org.junit.Test;

public class Calcite705 {
  
  protected Connection mysqlConnection =
      TnpmConnectionFactory
        .getCalciteLocalConnection(
            TnpmConnectionFactory.jdbcModel);
  
  @Ignore
  @Test
  public void Test1() throws Exception {
    Statement statement = mysqlConnection.createStatement();
    boolean status = statement.execute(
            "INSERT into \"cognos\".\"dept\" "
            		+ "(deptno, dname, loc) "
            		+ "values (50, 'DEVELOPMENT', 'MALAYSIA')");
  }
}
