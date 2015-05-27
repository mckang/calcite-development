package org.apache.calcite.squirrel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.calcite.avatica.AvaticaBaseTest;
import org.junit.Ignore;
import org.junit.Test;

public class SquirrelTest extends AvaticaBaseTest {
  
  /*
   * 2015.03.25: Although implemented remote preparedStatment but still not 
   * running with Squirrel. Below is the logic
   */
  @Test public void squirrelBehavior() throws Exception {
    showBorder("squirrelBehavior");
    Statement statement = connection.createStatement();
    Boolean status = statement.execute("select * from depts where 1 = 1");
    assertEquals(true, status);
    ResultSet resultSet = statement.getResultSet();
    assertEquals(false, resultSet.isClosed());
    int count = 0;
    while (resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("squirrelBehavior");
  }
  
  /*
   * Submit to Calcite Git Hub
   */
  @Ignore
  @Test public void squirrelBehavior1() throws Exception {
    showBorder("squirrelBehavior1");
    Statement statement = connection.createStatement();
    Boolean status = statement.execute("values (1, 2), (3, 4), (5, 6)");
    assertEquals(true, status);
    ResultSet resultSet = statement.getResultSet();
    assertEquals(false, resultSet.isClosed());
    int count = 0;
    while (resultSet.next()) {
      count += 1;
    }
    assertTrue(count > 0);
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("squirrelBehavior1");
  }
  
  @Test public void squirrelBehaviorGetCatelogs() throws Exception {
    showBorder("squirrelBehaviorGetCatelogs");
    ResultSet resultSet = connection.getMetaData().getCatalogs();
    while(resultSet.next()) {
    }
    showBorder("squirrelBehaviorGetCatelogs");
  }
}
