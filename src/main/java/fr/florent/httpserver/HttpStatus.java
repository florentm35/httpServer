package fr.florent.httpserver;

public enum HttpStatus {

    HTTP_OK("200", "OK"), HTTP_BAD_REQUEST("400", "Bad Request"),
    HTTP_NOT_FOUNDHTTP_STATUS("404", "Not Found"), HTTP_NOT_IMPLEMENTED("501", "Not Implemented");


    public String code;
    public String messsage;

    HttpStatus(String code, String messsage) {
        this.code = code;
        this.messsage = messsage;
    }
}
