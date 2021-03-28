package fr.florent.httpserver.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private EnumAction action;
    private String path;

    private HttpProtocole protocole;

    private HashMap<String, String> headers;

    private String body;


    public Request addHeader(String key, String value) {

        if (headers == null) {
            headers = new HashMap<String, String>();
        }

        headers.put(key, value);

        return this;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public EnumAction getAction() {
        return action;
    }

    public String getPath() {
        return path;
    }


    public void setAction(EnumAction action) {
        this.action = action;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpProtocole getProtocole() {
        return protocole;
    }

    public void setProtocole(HttpProtocole protocole) {
        this.protocole = protocole;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
