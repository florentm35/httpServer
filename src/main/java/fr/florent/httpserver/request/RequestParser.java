package fr.florent.httpserver.request;

import fr.florent.httpserver.exception.http.BadRequestException;
import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.http.HttpHeaders;
import fr.florent.httpserver.http.HttpMethod;
import fr.florent.httpserver.http.HttpProtocole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    private static final int ACTION_HEADER = 0;
    private static final int HEADER = 1;
    private static final int END = 2;


    /**
     * Parse input stream and return {@link fr.florent.httpserver.request.Request}
     *
     * @param stream
     * @return {@link fr.florent.httpserver.request.Request}
     * @throws SystemException
     */
    public static Request parseRequest(InputStream stream) throws SystemException {

        var request = new Request();

        try {

            BufferedReader plec = new BufferedReader(
                    new InputStreamReader(stream));


            int state = ACTION_HEADER;

            String str;
            while (state != END && (str = plec.readLine()) != null) {

                switch (state) {
                    case ACTION_HEADER:

                        readActionHeader(str, request);
                        state = HEADER;
                        break;
                    case HEADER:
                        if (str.length() == 0) {
                            state = END;
                        } else {
                            parseRequestHeader(str, request);
                        }
                        break;
                }

            }


            String contentLength = request.getHeaders().get(HttpHeaders.CONTENT_LENGTH);
            if (contentLength != null) {

                int length = Integer.parseInt(contentLength);

                char[] bodyChar = new char[length];
                plec.read(bodyChar);

                request.setBody(new String(bodyChar));

            }


            return request;


        } catch (IOException e) {
            throw new SystemException(e);
        }

    }


    private static String parseRequestHeader(String requestData, Request request) {

        String[] dataHeader = requestData.split(": ");
        request.addHeader(dataHeader[0], dataHeader[1]);
        return dataHeader[0];
    }

    /**
     * Read the first line of request and remove it <br/>
     * Format excepted : {@link HttpMethod} String {@link HttpProtocole}
     *
     * @param requestData
     * @param request
     */
    private static void readActionHeader(String requestData, Request request) {
        String[] actionHeader = requestData.split(" ");

        if (actionHeader.length != 3) {
            throw new BadRequestException("Actions header not found");
        }

        request.setMethod(HttpMethod.getAction(actionHeader[0]));

        if (request.getMethod() == null) {
            throw new BadRequestException(String.format("Action : %s, not implemented", actionHeader[0]));
        }

        request.setPath(actionHeader[1]);

        request.setProtocole(HttpProtocole.parse(actionHeader[2]));

        if (request.getProtocole() == null) {
            throw new BadRequestException(String.format("Protocole : %s, not implemented", actionHeader[2]));
        }

    }
}
