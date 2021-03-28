package fr.florent.httpserver.server;

import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.request.RequestProcess;
import fr.florent.httpserver.util.PropertiesUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {


    private Logger LOGGER = Logger.getLogger(Server.class.getName());


    public void launch() throws IOException {

        int port = 8080;


        String value = PropertiesUtil.getValue("server.port");
        if (value != null) {
            port = Integer.parseInt(value);
        }
        ServerSocket s = new ServerSocket(port);


        int maxPoolSize = 10;

        value = PropertiesUtil.getValue("server.pool.size");
        if (value != null) {
            maxPoolSize = Integer.parseInt(value);
        }

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(maxPoolSize);


        while (true) {
            Socket socket = s.accept();

            RequestProcess process = new RequestProcess(socket);
            executor.submit(() -> {
                try {
                    process.process();

                } catch (SystemException e) {
                    LOGGER.error(e);
                }
            });
        }
    }
}


