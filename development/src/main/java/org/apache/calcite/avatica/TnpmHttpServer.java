package org.apache.calcite.avatica;

import java.net.ServerSocket;
import java.util.ArrayList;

import org.apache.calcite.avatica.jdbc.JdbcMeta;
import org.apache.calcite.avatica.server.HttpServer;
import org.apache.calcite.avatica.server.Main;
import org.apache.calcite.jdbc.TnpmConnectionFactory;

public class TnpmHttpServer {
  private Thread t;
  private TnpmHttpServerRunnable tnpmHttpServerRunnable;
  private String[] additionalArgs = new String[] {};
 
  public TnpmHttpServer() {
    tnpmHttpServerRunnable = new TnpmHttpServerRunnable(false,
        TnpmConnectionFactory.class.getName(), JdbcMeta.class.getName());
  }

  public TnpmHttpServer(boolean join) {
    tnpmHttpServerRunnable = new TnpmHttpServerRunnable(join,
        TnpmConnectionFactory.class.getName(), JdbcMeta.class.getName());
  }
  
  public TnpmHttpServer(boolean join, String connectionFactory) {
    tnpmHttpServerRunnable = new TnpmHttpServerRunnable(join,
        connectionFactory, JdbcMeta.class.getName());
  }

  public TnpmHttpServer(boolean join, String connectionFactory,
      String metaImpl) {
    tnpmHttpServerRunnable = new TnpmHttpServerRunnable(join,
        connectionFactory, metaImpl);
  }

  public void start() throws Exception {
    t = new Thread(tnpmHttpServerRunnable);
    tnpmHttpServerRunnable.setAdditionalArgs(additionalArgs);
    t.start();
  }

  public void stop() throws Exception {
    t = null;
  }
  
  public void setAdditionalArgs(String ... additionalArgs) {
    this.additionalArgs = additionalArgs;
  }
}

class TnpmHttpServerRunnable implements Runnable {

  private static HttpServer httpServer;
  private ServerSocket serverSocket;
  private boolean join = false;
  private String connectionFactory;
  private String metaImpl;
  private String[] additionalArgs = new String[] {};
  
  public TnpmHttpServerRunnable(boolean join, String connectionFactory,
      String metaImpl, String ... additionalArgs) {
    this.join = join;
    this.connectionFactory = connectionFactory;
    this.metaImpl = metaImpl;
  }

  public void setAdditionalArgs(String ... additionalArgs) {
    this.additionalArgs = additionalArgs;
  }

  @Override
  public void run() {
      init();
  }

  private void init() {
    try {
      if(portAvailability(8765)) {
        ArrayList<String> args = new ArrayList<String>();
        args.add(connectionFactory);
        args.add(metaImpl);
        if(additionalArgs.length != 0) {
          for(int i = 0; i < additionalArgs.length; i++) {
            args.add(additionalArgs[i]);
          }
        }
        httpServer = Main.start(args.toArray(new String[]{}));

        if(join)
          httpServer.join();
      } else {
        System.out.println("[INFO]: Reusing HTTP Server.");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private boolean portAvailability(int port) {
    boolean status = false;
    try {
      serverSocket = new ServerSocket(port);
      serverSocket.close();
      status = true;
    } catch (Exception ex) {
      status = false;
    }
    return status;
  }

}
