package fr.florent.httpserver.response;

import fr.florent.httpserver.http.HttpStatus;
import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.html.HtmlUtils;
import fr.florent.httpserver.http.HttpProtocole;
import fr.florent.httpserver.request.Request;

import java.io.*;
import java.util.Date;
import java.util.Map;

public class ResponseFormater {

    public static Response createResponse(Request request, OutputStream stream) {
        Response response = new Response(HttpProtocole.HTTP1_0, stream);

        response.addHeader("Date", new Date().toString());
        response.addHeader("Server", "Mon super server");
        return response;
    }

    public static void print(Response response) throws SystemException {

        try (PrintWriter pred = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(response.getOutputStream())),
                false);) {

            HttpStatus status = response.getStatus();
            String actionHeader = String.format("%s %s %s", response.getProtocole().code, status.code, status.messsage);

            pred.println(actionHeader);

            for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
                pred.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
            }

            if (response.getBody() == null && !response.getStatus().equals(HttpStatus.HTTP_NO_CONTENT)) {
                response.setBody(HtmlUtils.getHtmlForStatus(response.getStatus()));
            }


            int size = response.getBody() == null ? 0 : response.getBody().length();
            pred.println(String.format("%s: %d", "Content-Length", size));

            pred.println(String.format(""));
            if (response.getBody() != null) {
                pred.println(response.getBody());
            }

            pred.flush();
        }

    }
}
