package org.apache.calcite.avatica;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import static org.junit.Assert.*;

public class AvaticaDatabaseMetaDataTest extends AvaticaBaseTest {
  @Test
  /*
   * 1. JdbcMeta returns null
   * 2. MetaImpl return a EmptyResultSet
   */
  public void testGetInfoType() throws Exception {
    DatabaseMetaData metadata = connection.getMetaData();
    ResultSet resultSet = metadata.getTypeInfo();
    int count = 0;
    while(resultSet.next()) {
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      int columnCount = resultSetMetaData.getColumnCount();
      //String data = "";
      for (int i = 0; i < columnCount; i++) {
        //data += "|_|" + resultSetMetaData.getCatalogName(i) + "|_|" + resultSet.getObject(i);
      }
      count += 1;
    }
    assertTrue(count > 0);
  }
}
