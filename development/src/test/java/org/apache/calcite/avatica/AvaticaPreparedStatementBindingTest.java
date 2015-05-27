package org.apache.calcite.avatica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvaticaPreparedStatementBindingTest extends AvaticaBaseTest {

  @Test public void bindDouble() throws Exception {
    String sql = "select * from depts where 1.0 = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setDouble(1, 1.0);
    boolean status = preparedStatement.execute();
    ResultSet resultSet = preparedStatement.getResultSet();
    int count = 0;
    while(resultSet.next()) {
      System.out.println("XXX|_|" + resultSet.getObject(1));
      count += 1;
    }
    assertEquals(3, count);
  }
}
