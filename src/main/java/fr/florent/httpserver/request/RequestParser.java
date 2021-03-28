package fr.florent.httpserver.request;

import fr.florent.httpserver.exception.http.BadRequestException;
import fr.florent.httpserver.exception.http.EmptyRequestException;
import fr.florent.httpserver.exception.system.SystemException;
import fr.florent.httpserver.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RequestParser {

    private static final int ACTION_HEADER = 0;
    private static final int HEADER = 1;
    private static final int BODY = 2;
    private static final int END = 3;


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


            StringBuilder body = new StringBuilder();

            int state = ACTION_HEADER;
            boolean hasBody = false;

            String str;
            while (state != END && (str = plec.readLine()) != null) {

                switch (state) {
                    case ACTION_HEADER:

                        readActionHeader(str, request);
                        state = HEADER;
                        break;
                    case HEADER:
                        if (str.length() == 0) {
                            if (hasBody) {
                                state = BODY;
                            } else {
                                state = END;
                            }
                        } else {
                            String header = parseRequestHeader(str, request);

                            if (HttpHeaders.CONTENT_LENGTH.equals(header.toLowerCase())) {
                                hasBody = true;
                            }
                        }
                        break;
                    case BODY:
                        body.append(str);
                        break;
                }

            }

            String strBody = body.toString();

            if (strBody != null && !strBody.isEmpty()) {
                request.setBody(strBody);
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
     * Format excepted : {@link EnumAction} String {@link HttpProtocole}
     *
     * @param requestData
     * @param request
     */
    private static void readActionHeader(String requestData, Request request) {
        String[] actionHeader = requestData.split(" ");

        if (actionHeader.length != 3) {
            throw new BadRequestException("Actions header not found");
        }

        request.setAction(EnumAction.getAction(actionHeader[0]));

        if (request.getAction() == null) {
            throw new BadRequestException(String.format("Action : %s, not implemented", actionHeader[0]));
        }

        request.setPath(actionHeader[1]);

        request.setProtocole(HttpProtocole.parse(actionHeader[2]));

        if (request.getProtocole() == null) {
            throw new BadRequestException(String.format("Protocole : %s, not implemented", actionHeader[2]));
        }

    }
}
