package fr.florent.httpserver.response;

import fr.florent.httpserver.HttpStatus;
import fr.florent.httpserver.request.HttpProtocole;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private HttpProtocole protocole;

    private HttpStatus status;

    private Map<String, String> headers;

    private OutputStream outputStream;

    private String body;

    public Response(HttpProtocole protocole, OutputStream inputStream) {
        this.protocole = protocole;
        this.outputStream = inputStream;
    }

    public Response setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public Response addHeader(String key, String value) {

        if (headers == null) {
            headers = new HashMap<>();
        }

        headers.put(key, value);

        return this;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public HttpProtocole getProtocole() {
        return protocole;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        return Collections.unmodifiableMap(headers);
    }

    public String getBody() {
        return body;
    }

    public Response setBody(String body) {
        this.body = body;
        return this;
    }
}
