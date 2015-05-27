package org.apache.calcite.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.calcite.avatica.Meta;
import org.junit.Ignore;
import org.junit.Test;

public class CalciteStatementTest extends CalciteMetaImplTest {
  
  @Test public void inspectStatementExecuteResultSetSqlTrue()
      throws Exception{
    inspectStatementExecuteResultSet(meta, ch, sqlCondTrue, true);
  }
  
  @Test public void inspectStatementExecuteResultSetSqlFalse()
      throws Exception{
    inspectStatementExecuteResultSet(meta, ch, sqlCondFalse, true);
  }
  
  @Ignore @Test public void inspectStatementAsDmlInsert() {
    String sql = "insert into depts values (40, 'Software Engineering')";
    showAttribute("SQL", sql);
    Meta.StatementHandle h = createStatementHandle(meta, ch, sql);
    assertTrue(h.signature != null);
  }
  
  @Ignore @Test public void inspectStatementAsDmlUpdate() {
    String sql =
        "update depts set name = 'Sherpa Engineering' where deptno = 40";
    showAttribute("SQL", sql);
    Meta.StatementHandle h = createStatementHandle(meta, ch, sql);
    assertTrue(h.signature != null);
  }
  
  /**************************************************************************/
  
  public static void inspectStatementExecuteResultSet(
      CalciteMetaImpl meta, Meta.ConnectionHandle ch, String sql,
      Object actual) throws Exception {
    showBorder("inspectStatementExecuteResultSet");
    showAttribute("SQL", sql);
    Meta.StatementHandle h = createStatementHandle(meta, ch, sql);
    CalciteStatement statement = 
        (CalciteStatement) 
          meta.getConnection().getFactory().newStatement(
            meta.getConnection(), h, ResultSet.TYPE_FORWARD_ONLY,
              ResultSet.TYPE_SCROLL_INSENSITIVE, 
              Statement.CLOSE_CURRENT_RESULT);
    Boolean status = statement.execute(sql);
    assert(status);
    showAttribute("Inspect execute status", status);
    ResultSet resultSet = statement.getResultSet();
    resultSet.close();
    showAttribute("If ResultSet is closed", resultSet.isClosed());
    assertEquals(actual, resultSet.isClosed());
    showBorder("inspectStatementExecuteResultSet");
  }
}
