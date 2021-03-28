package fr.florent.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.request.RequestProcess;
import org.apache.log4j.Logger;

public class Server {

    private static final int port = 8080;

    private Logger LOGGER = Logger.getLogger(Server.class.getName());

    public void launch() throws IOException {

        ServerSocket s = new ServerSocket(port);

        while (true) {
            Socket socket = s.accept();

            RequestProcess process = new RequestProcess(socket);
            try {
                process.process();
            } catch (SystemException e) {
                LOGGER.error(e);
            }
        }
    }
}


