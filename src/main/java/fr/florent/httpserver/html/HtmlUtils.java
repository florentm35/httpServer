package fr.florent.httpserver.html;

import fr.florent.httpserver.HttpStatus;

public class HtmlUtils {

    public static String getHtmlForStatus(HttpStatus status) {

        return "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                "<html>\n" +
                "<head>\n" +
                "   <title>" + status.code + " " + status.messsage + "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "   <p> " + status.messsage + "</p>\n" +
                "</body>\n" +
                "</html>";

    }

}
