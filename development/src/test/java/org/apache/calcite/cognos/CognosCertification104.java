package org.apache.calcite.cognos;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.calcite.jdbc.TnpmConnectionFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class CognosCertification104 {
  @Test
  public void testDatabaseMetaDataGetConnection() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      Connection connection2 = connection.getMetaData().getConnection();
      assertEquals(false, connection2.isClosed());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
  }
  
  @Test
  public void testDatabaseMetadataGetTypeInfo() {
    try {
      Connection connection =
          TnpmConnectionFactory.getCalciteLocalConnection(null);
      ResultSet resultSet = connection.getMetaData().getTypeInfo();
      int count = 0;
      while(resultSet.next()) {
        count += 1;
      }
      assertTrue(count > 0);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
