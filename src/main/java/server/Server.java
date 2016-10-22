package server;

import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.log.JettyLogHandler;
import server.jmx.controller.JMXInit;
import server.model.AccountService;
import server.model.TokenStatusUpdater;
import server.model.dao.UserProfileHibernate;

/**
 * Created by eugene on 10/7/16.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Server {
    private static Integer PORT = 8080;
    private static String ROOT_SERVLET_PATH = "/*";

    private static Logger LOG = org.apache.logging.log4j.LogManager.getLogger("server");


    static {
        JMXInit.init();
        AccountService.setDao(new UserProfileHibernate());
    }

    public static void main(String[] args){
        Thread tokenupdater = new Thread(new TokenStatusUpdater());


        org.eclipse.jetty.server.Server jettyServer = new org.eclipse.jetty.server.Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder jersey = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, ROOT_SERVLET_PATH);

        jersey.setInitOrder(0);
        jersey.setInitParameter("jersey.config.server.provider.packages", "server.api");

        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            tokenupdater.start();
            LOG.info(String.format("server started at %d", PORT));
            jettyServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
