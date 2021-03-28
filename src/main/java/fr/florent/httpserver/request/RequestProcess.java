package fr.florent.httpserver.request;

import fr.florent.httpserver.server.CacheUrl;
import fr.florent.httpserver.server.Server;
import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.process.AbstractProcess;
import fr.florent.httpserver.response.Response;
import fr.florent.httpserver.response.ResponseFormater;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestProcess {

    private Logger LOGGER = Logger.getLogger(Server.class.getName());

    private Socket socket;

    public RequestProcess(Socket socket) {
        this.socket = socket;
    }

    public void process() throws SystemException {
        try {

            Request request = RequestParser.parseRequest(socket.getInputStream());

            LOGGER.info(String.format("> %s %s", request.getMethod().name(), request.getPath()));

            Response response = ResponseFormater.createResponse(request, socket.getOutputStream());

            Method method = CacheUrl.getMethodFormRequest(request);

            if (method == null) {
                AbstractProcess process = new AbstractProcess();
                process.doProcess(request, response);
            } else {
                Class tClass = method.getDeclaringClass();
                Object obj = tClass.getConstructor().newInstance();
                method.invoke(obj, request, response);
            }

            ResponseFormater.print(response);

            LOGGER.info(String.format("< %s %s %s", request.getMethod().name(), request.getPath(), response.getStatus().code));

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
