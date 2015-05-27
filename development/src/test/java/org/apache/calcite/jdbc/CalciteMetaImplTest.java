package org.apache.calcite.jdbc;

import org.apache.calcite.BaseTest;
import org.apache.calcite.avatica.Meta;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalciteMetaImplTest extends BaseTest {
  
  static CalciteMetaImpl meta;
  static Meta.ConnectionHandle ch;
  
  static String sqlCondTrue = "select * from depts where 1 = 1";
  static String sqlCondFalse = "select * from depts where 1 = 2";
  
  @BeforeClass public static void beforeClass() throws Exception {
    meta = createMeta();
    ch = createConnectionHandle(meta);
  }
  
  @AfterClass public static void afterClass() throws Exception {  
    meta.closeConnection(ch);
  }
  
  @Test public void inspectIterableSqlCondTrue() {
    inspectIterable(meta, ch, sqlCondTrue, true);
  }
  
  @Test public void inspectIterableSqlCondFalse() {
    inspectIterable(meta, ch, sqlCondFalse, false);
  }

  public static void inspectIterable(
      CalciteMetaImpl meta, Meta.ConnectionHandle ch, String sql, 
      Object actual) {
    showBorder("inspectIterable");
    showAttribute("SQL", sql);
    Iterable<Object> iterable =
        createIterable(meta, createStatementHandle(meta, ch, sql));
    showAttribute("If Iterable Object has data", iterable.iterator().hasNext());
    assertEquals(actual, iterable.iterator().hasNext());
    showBorder("inspectIterable");
  }
  
  /**************************************************************************/
  
  public static Meta.ConnectionHandle createConnectionHandle(CalciteMetaImpl meta) {
    return new Meta.ConnectionHandle(meta.getConnection().id);
  }
  
  public static Meta.StatementHandle createStatementHandle(
      CalciteMetaImpl meta, Meta.ConnectionHandle ch, String sql) {
    return  meta.prepare(ch, sql, -1);
  }
  
  public static CalciteMetaImpl createMeta() {
    return new CalciteMetaImpl((CalciteConnectionImpl) 
        TnpmConnectionFactory.getCalciteLocalConnection(null));
  }
  
  public static Iterable<Object> createIterable(
      CalciteMetaImpl meta, Meta.StatementHandle h) {
    return meta.createIterable(h, h.signature, null, null);
  }
}
