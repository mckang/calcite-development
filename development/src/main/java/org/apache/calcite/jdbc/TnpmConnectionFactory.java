package org.apache.calcite.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import org.apache.calcite.avatica.Meta;
import org.apache.calcite.avatica.jdbc.JdbcMeta;

public class TnpmConnectionFactory implements Meta.Factory {

  public final static String csvModel = 
      "inline:" 
          + "{" 
          +     "version: '1.0',"
          +     "defaultSchema: 'SALES',"
          +     "schemas: ["
          +       "{"
          +           "name: 'SALES',"
          +           "type: 'custom',"
          +           "factory: "
          +             "'org.apache.calcite.adapter.csv.CsvSchemaFactory',"
          +           "operand: {"
          +             "directory: "
          +             "'/home/sherpa/incubator-calcite/"
          +             "example/csv/src/test/resources/sales'"
          +           "},"
          +           "tables: ["
          +             "{"
          +                "name: 'VTM',"
          +                "type: 'view',"
          +                "sql: 'SELECT * FROM TTM'"
          +             "}"
          +           "]"
          +       "}"
          +     "]"
          + "}";
  
  public final static String jdbcModel = 
      "inline:"
          + "{" 
          +    "version: '1.0',"
          +    "defaultSchema: 'cognos',"
          +    "schemas: ["
          +    "{"
          +       "name: 'cognos',"
          +       "type: 'custom', "
          +       "factory: 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory',"
          +       "operand: {"
          +         "jdbcDriver: 'com.mysql.jdbc.Driver',"
          +         "jdbcUrl: 'jdbc:mysql://10.211.27.0/cognos',"
          +         "jdbcUser: 'root',"
          +         "jdbcPassword: 'skyline01'"
          +       "}"
          +    "}"
          +   "]" 
          + "}";
  
  public final static String hsqldbModel = 
      "inline:"
          + "{" 
          +    "version: '1.0',"
          +    "defaultSchema: 'cognos',"
          +    "schemas: ["
          +    "{"
          +       "name: 'cognos',"
          +       "type: 'custom', "
          +       "factory: 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory',"
          +       "operand: {"
          +         "jdbcDriver: 'org.hsqldb.jdbc.JDBCDriver',"
          +         "jdbcUrl: 'jdbc:hsqldb:hsql://192.168.0.100/cognos',"
          +         "jdbcUser: 'SA',"
          +         "jdbcPassword: ''"
          +       "}"
          +    "}"
          +   "]" 
          + "}";

  public final static String modelFile = 
      "/home/sherpa/incubator-calcite/example/csv/src/test/resources/model.json";

  public final static String localJdbcUrl = "jdbc:calcite:";

  private static void showMeta(String meta) {
    System.out.println("[TnpmConnectionFactory.create] Implement with " + meta);
  }

  public static Connection getCalciteLocalConnection(String inlineModel) {
    Connection connection = null;
    Properties info = new Properties();
    if(inlineModel == null)
      info.put("model", csvModel);
    else 
      info.put("model", inlineModel);

    try {
      connection = DriverManager.getConnection(localJdbcUrl, info);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    return connection;
  }

  @Override
  public Meta create(List<String> args) {
    try {
      if (args.get(0) == "org.apache.calcite.avatica.jdbc.JdbcMeta") {
        showMeta(args.get(0));
        if(args.size() > 1 && args.get(1).equalsIgnoreCase("MySQL")) {
          return new JdbcMeta(localJdbcUrl + "model=" + jdbcModel);
        } else {
          return new JdbcMeta(localJdbcUrl + "model=" + modelFile);
        }
      } else {
        showMeta(CalciteMetaImpl.class.getName());
        final Connection connection = getCalciteLocalConnection(csvModel);
        return new CalciteMetaImpl((CalciteConnectionImpl) connection);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}
