package org.apache.calcite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.calcite.avatica.AvaticaBaseTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackLogTest extends AvaticaBaseTest {
  
  @Ignore @Test public void findSalesSchema(Connection connection) 
      throws Exception {
    showBorder("testCsvSchema");
    ResultSet schemaRs = connection.getMetaData().getSchemas(null, "SALES");
    int count = 0;
    while (schemaRs.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    showBorder("testCsvSchema");
  }
  
  @Ignore @Test public void preparedStatementaAndExecuteUpdate() 
    throws Exception {
    showBorder("preparedStatementaAndExecuteUpdate"); 
    PreparedStatement preparedStatement = 
        connection.prepareStatement(
            "insert into depts (deptno, name) values (?, ?)");
    preparedStatement.setInt(1, 40);
    preparedStatement.setString(2, "Software Engineering");
    int status = preparedStatement.executeUpdate();
    assertEquals(1, status);
    showBorder("preparedStatementaAndExecuteUpdate");
  }
  
}
