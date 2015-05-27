package org.apache.calcite.jdbc;

import java.sql.ResultSet;

import org.apache.calcite.jdbc.CalciteConnectionImpl.ContextImpl;

public class CalciteConnectionHelper {
  
  private boolean debug = false;
  private String inlineModel = null;
  
  public CalciteConnectionHelper() {
    this.inlineModel = TnpmConnectionFactory.csvModel;
    this.debug = false;
  }
  
  public CalciteConnectionHelper(String inlineMode) {
    this.inlineModel = inlineMode;
    this.debug = false;
  }
  
  public CalciteConnectionHelper(String inlineMode, boolean debug) {
    this.inlineModel = inlineMode;
    this.debug = debug;
  }
  
  public CalciteConnectionImpl getCalciteConnectionImpl() throws Exception {
    CalciteConnectionImpl connection = 
        (CalciteConnectionImpl) 
          TnpmConnectionFactory.getCalciteLocalConnection(inlineModel);
    ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
    if (debug) {
      while (rs.next()) {
        System.out.println(
            "TABLE_SCHEM|_|" + rs.getObject("TABLE_SCHEM") + "|_|" +
            "TABLE_NAME|_|" + rs.getObject("TABLE_NAME"));
      }
    }
    return connection;
  }
  
  public CalcitePrepare.Context getContext() throws Exception {
    return new ContextImpl(getCalciteConnectionImpl());
  }
}