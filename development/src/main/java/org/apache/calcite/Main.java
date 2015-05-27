package org.apache.calcite;

import org.apache.calcite.avatica.TnpmHttpServer;

public class Main {
  public static void main(String[] args) throws Exception {
    startTnpmHttpServer(true);
  }
  
  private static void startTnpmHttpServer(boolean join) throws Exception {
    TnpmHttpServer tnpmHttpServer = new TnpmHttpServer(join);
    tnpmHttpServer.setAdditionalArgs("MySQL");
    tnpmHttpServer.start();
  }
}

