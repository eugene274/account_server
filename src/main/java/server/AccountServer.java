package server;

import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by eugene on 10/7/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AccountServer implements Runnable {

    private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(AccountServer.class);

    public void run(){
        Integer PORT = 8080;
        org.eclipse.jetty.server.Server jettyServer = new org.eclipse.jetty.server.Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        String ROOT_SERVLET_PATH = "/*";
        ServletHolder jersey = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, ROOT_SERVLET_PATH);

        jersey.setInitOrder(0);
        jersey.setInitParameter("jersey.config.server.provider.packages", "server.api");

        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            LOG.info(String.format("server started at %d", PORT));
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
