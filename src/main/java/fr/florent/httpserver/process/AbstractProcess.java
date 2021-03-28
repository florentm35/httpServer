package fr.florent.httpserver.process;

import fr.florent.httpserver.http.HttpStatus;
import fr.florent.httpserver.request.Request;
import fr.florent.httpserver.response.Response;


public class AbstractProcess {


    public Response doProcess(Request request, Response response) {

        return response.setStatus(HttpStatus.HTTP_NOT_FOUND);
    }

    protected String getPostParam(Request request, String key) {

        if (key != null) {
            String[] params = request.getBody().split("&");

            for (int i = 0; i < params.length; i++) {
                String[] param = params[i].split("=");

                if (key.equals(param[0])) {
                    return param[1];
                }

            }
        }

        return null;
    }

    protected String getQueryParam(Request request, String key) {

        return null;
    }

}
