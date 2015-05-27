package org.apache.calcite.avatica;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.calcite.BaseTest;
import org.apache.calcite.avatica.TnpmHttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class AvaticaBaseTest extends BaseTest {
  
  final public static String port = "8765";
  final public static String calciteRemoteJdbcUrl = "jdbc:avatica:remote:";
  protected static TnpmHttpServer tnpmHttpServer;
  protected static Connection connection;
  
  @BeforeClass public static void beforeClass() throws Exception {
    tnpmHttpServer = new TnpmHttpServer();
    tnpmHttpServer.start();
    
    Thread.sleep(2000);
    
    connection = DriverManager.getConnection(
        calciteRemoteJdbcUrl + "url=http://localhost:" + port);
    assertEquals(false, connection.isClosed());
  }
  
  @AfterClass public static void afterClass() throws Exception {
    // tnpmHttpServer.stop();
    // tnpmHttpServer = null;
  }
  
}
