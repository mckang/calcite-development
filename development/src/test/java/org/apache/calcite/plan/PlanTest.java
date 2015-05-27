package org.apache.calcite.plan;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.Ignore;
import org.junit.Test;

public class PlanTest {
  @Test 
  public void testGetPlan() throws Exception {
    try {
      String sql = "select RNUM from TJOIN1";
      SqlParser parser = SqlParser.create(sql);
      SqlNode node = parser.parseStmt();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
