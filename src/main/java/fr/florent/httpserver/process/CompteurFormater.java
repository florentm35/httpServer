package fr.florent.httpserver.process;

import java.util.Map;

public class CompteurFormater {

    public static String getJson(String key, int value) {
        return String.format("{\n\t\"%s\" : %d\n}", key, value);
    }

    public static String getJson(Map<String, Integer> compteur) {
        boolean isFirst = true;
        StringBuilder body = new StringBuilder("{");

        for (Map.Entry<String, Integer> entry : compteur.entrySet()) {

            if (isFirst) {
                isFirst = false;
            } else {
                body.append(",");
            }

            body.append(getJson(entry.getKey(), entry.getValue()));
        }

        body.append("}");

        return body.toString();

    }

}
