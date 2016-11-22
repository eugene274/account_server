package server;

import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by eugene on 10/7/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AccountServer extends Thread {
  private final Server jettyServer;
  private final String rootPath;
  private final int port;

  private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(AccountServer.class);


  public AccountServer() {
    this(8080,"/*");
  }

  public AccountServer(int port, String rootPath) {
    this.jettyServer = new Server(port);
    this.rootPath = rootPath;
    this.port = port;
  }

  public void run(){
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    ServletHolder jersey = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, rootPath);

    jersey.setInitOrder(0);
    jersey.setInitParameter("jersey.config.server.provider.packages", "server.api");

    jettyServer.setHandler(context);

    try {
      jettyServer.start();
      LOG.info(String.format("server started at %d", port));
      jettyServer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args){
    new AccountServer().run();
  }
}
