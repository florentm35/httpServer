package fr.florent.httpserver.request;

import fr.florent.httpserver.http.HttpMethod;
import fr.florent.httpserver.http.HttpProtocole;
import fr.florent.httpserver.process.annotation.Mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private HttpMethod method;
    private String path;

    private HttpProtocole protocole;

    private HashMap<String, String> headers;

    private String body;

    private List<String> pathParam;


    public Request addHeader(String key, String value) {

        if (headers == null) {
            headers = new HashMap<String, String>();
        }

        headers.put(key, value);

        return this;
    }

    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        return Collections.unmodifiableMap(headers);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }


    public void setMethod(HttpMethod method) {
        this.method = method;
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


    public List<String> getPathParam() {
        return pathParam;
    }

    public void setPathParam(List<String> pathParam) {
        this.pathParam = pathParam;
    }
}
