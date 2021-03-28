package fr.florent.httpserver;

import fr.florent.httpserver.server.CacheUrl;
import fr.florent.httpserver.server.Server;
import fr.florent.httpserver.util.PropertiesUtil;
import org.apache.log4j.Logger;

import java.io.IOException;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {

        PropertiesUtil.load("application.properties");

        CacheUrl.scan();

        Server server = new Server();
        server.launch();

    }

}
