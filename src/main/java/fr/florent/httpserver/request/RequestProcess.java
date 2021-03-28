package fr.florent.httpserver.request;

import fr.florent.httpserver.Server;
import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.process.AbstractProcess;
import fr.florent.httpserver.response.Response;
import fr.florent.httpserver.response.ResponseFormater;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestProcess {

    private Logger LOGGER = Logger.getLogger(Server.class.getName());

    private Socket socket;

    public RequestProcess(Socket socket) {
        this.socket = socket;
    }

    public void process() throws SystemException {
        // Un BufferedReader permet de lire par ligne.


        LOGGER.info("Message receive");
        try {

            Request request = RequestParser.parseRequest(socket.getInputStream());

            Response response = ResponseFormater.createResponse(request, socket.getOutputStream());

            // TODO : change this
            AbstractProcess process = new AbstractProcess();

            process.doProcess(request, response);

            ResponseFormater.print(response);


        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new SystemException(e);
            }
        }

    }
}
