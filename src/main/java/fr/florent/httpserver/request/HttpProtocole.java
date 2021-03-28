package fr.florent.httpserver.request;

public enum HttpProtocole {

    HTTP1_0("HTTP/1.0"), HTTP1_1("HTTP/1.1");

    public String code;

    HttpProtocole(String code) {
        this.code = code;
    }

    public static HttpProtocole parse(String str) {

        for (HttpProtocole protocole : HttpProtocole.values()) {
            if (protocole.code.equals(str)) {
                return protocole;
            }
        }

        return null;
    }
}
