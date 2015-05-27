package org.apache.calcite.avatica;

import java.sql.SQLException;

import org.apache.calcite.avatica.jdbc.JdbcMeta;
import org.apache.calcite.avatica.remote.LocalJsonService;
import org.apache.calcite.avatica.remote.LocalService;
import org.apache.calcite.avatica.remote.Service;
import org.apache.calcite.jdbc.TnpmConnectionFactory;

public class TnpmLocalServiceFactory implements Service.Factory {
  @Override public Service create(AvaticaConnection connection) {
    try {
      String jdbcUrl =
          TnpmConnectionFactory.localJdbcUrl
          + "model=" + TnpmConnectionFactory.modelFile;
      Meta meta = new JdbcMeta(jdbcUrl);
      Service service = new LocalService(meta);
      return new LocalJsonService(service);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}