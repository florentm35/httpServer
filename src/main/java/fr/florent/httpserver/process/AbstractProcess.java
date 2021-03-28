package fr.florent.httpserver.process;

import fr.florent.httpserver.HttpStatus;
import fr.florent.httpserver.html.HtmlUtils;
import fr.florent.httpserver.request.Request;
import fr.florent.httpserver.response.Response;


public class AbstractProcess {



    public Response doProcess(Request request, Response response) {

        return response.setStatus(HttpStatus.HTTP_OK);
    }

}
