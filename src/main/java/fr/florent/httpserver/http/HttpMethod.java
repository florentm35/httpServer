package fr.florent.httpserver.http;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    /**
     * Return {@link HttpMethod} by str gived
     *
     * @param str
     * @return {@link HttpMethod}
     */
    public static HttpMethod getAction(String str) {
        for (var action : HttpMethod.values()) {
            if (action.name().equals(str)) {
                return action;
            }
        }
        return null;
    }
}
