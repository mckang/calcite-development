package org.apache.calcite.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.calcite.avatica.Meta;
import org.junit.Test;

public class CalcitePreparedStatementTest extends CalciteMetaImplTest {
  
  @Test public void inspectPreparedStatementResultSetSqlTrue()
      throws Exception {
    inspectPreparedStatementResultSet(meta, ch, sqlCondTrue);
  }
  
  @Test public void inspectPreparedStatementResultSetSqlFalse()
      throws Exception{
    inspectPreparedStatementResultSet(meta, ch, sqlCondFalse);
  }
  
  @Test public void inspectPreparedStatementExecuteResultSetSqlTrue()
      throws Exception{
    inspectPreparedStatementExecuteResultSet(meta, ch, sqlCondTrue);
  }
  
  @Test public void inspectPreparedStatementExecuteResultSetSqlFalse()
      throws Exception{
    inspectPreparedStatementExecuteResultSet(meta, ch, sqlCondFalse);
  }
  
  /**************************************************************************/
  
  public static void inspectPreparedStatementResultSet(
      CalciteMetaImpl meta, Meta.ConnectionHandle ch, String sql)
          throws Exception {
    showBorder("inspectPreparedStatementResultSet");
    showAttribute("SQL", sql);
    Meta.StatementHandle h = createStatementHandle(meta, ch, sql);
    CalcitePreparedStatement preparedStatement = 
        (CalcitePreparedStatement)
          meta.getConnection().getFactory().newPreparedStatement(
            meta.getConnection(), h, h.signature, 
              ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
              Statement.CLOSE_CURRENT_RESULT);
    ResultSet resultSet = preparedStatement.executeQuery();
    showAttribute("If ResultSet is closed", resultSet.isClosed());
    assertEquals(false, resultSet.isClosed());
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("inspectPreparedStatementResultSet");
  }
  
  public static void inspectPreparedStatementExecuteResultSet (
      CalciteMetaImpl meta, Meta.ConnectionHandle ch, String sql)
          throws Exception {
    showBorder("inspectPreparedStatementExecuteResultSet");
    showAttribute("SQL", sql);
    Meta.StatementHandle h = createStatementHandle(meta, ch, sql);
    CalcitePreparedStatement preparedStatement = 
        (CalcitePreparedStatement)
          meta.getConnection().getFactory().newPreparedStatement(
              meta.getConnection(), h, h.signature, 
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
                Statement.CLOSE_CURRENT_RESULT);
    boolean status = preparedStatement.execute();
    showAttribute("Inspect execute status", status);
    ResultSet resultSet = preparedStatement.getResultSet();
    showAttribute("If ResultSet is closed", resultSet.isClosed());
    assertEquals(false, resultSet.isClosed());
    resultSet.close();
    assertEquals(true, resultSet.isClosed());
    showBorder("inspectPreparedStatementExecuteResultSet");
  }
  
}
