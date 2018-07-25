package ru.ars.ncedu.task9.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.ars.ncedu.task9.requestdatabase.SessionHibernate;

public class StartServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(ServerReqResp.class);
        context.addServlet(servletHolder,"/ncedu/task9");

        SessionHibernate.getSessionFactory();

        server.start();
        server.join();
    }
}
