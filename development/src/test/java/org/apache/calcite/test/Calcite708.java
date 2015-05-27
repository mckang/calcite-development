package org.apache.calcite.test;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Test;

public class Calcite708 {
  
  protected Connection csvConnection =
      TnpmConnectionFactory
        .getCalciteLocalConnection(TnpmConnectionFactory.jdbcModel);
  
  @Test
  public void Test1() throws Exception {
    ResultSet rs = csvConnection.getMetaData().getTypeInfo();
    rs.next();
  }
}
