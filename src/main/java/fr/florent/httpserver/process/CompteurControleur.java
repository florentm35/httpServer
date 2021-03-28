package fr.florent.httpserver.process;

import fr.florent.httpserver.http.HttpMethod;
import fr.florent.httpserver.http.HttpStatus;
import fr.florent.httpserver.process.annotation.Mapping;
import fr.florent.httpserver.process.annotation.Path;
import fr.florent.httpserver.request.Request;
import fr.florent.httpserver.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/compteur")
public class CompteurControleur extends AbstractProcess {

    public static Map<String, Integer> compteur = new ConcurrentHashMap<>() {
        {
            put("Tomate", 5);
            put("Oignon", 15);
        }
    };

    @Mapping(method = HttpMethod.GET)
    public Response findAll(Request request, Response response) {

        return response.setBody(CompteurFormater.getJson(compteur)).setStatus(HttpStatus.HTTP_OK);
    }

    @Mapping(method = HttpMethod.GET, path = "/(.*)")
    public Response findByName(Request request, Response response) {


        String compteurKey = request.getPathParam().get(0);

        if (!compteur.containsKey(compteurKey)) {
            return response.setBody(String.format("Le compteur %s n'a pas été trouvé", compteurKey)).setStatus(HttpStatus.HTTP_NOT_FOUND);
        }

        return response.setBody(CompteurFormater.getJson(compteurKey, compteur.get(compteurKey))).setStatus(HttpStatus.HTTP_OK);
    }

    @Mapping(method = HttpMethod.POST)
    public Response create(Request request, Response response) {

        String name = super.getPostParam(request, "name");

        if (name == null) {
            return response.setBody("Name parameter not found").setStatus(HttpStatus.HTTP_BAD_REQUEST);
        }
        int intValue = 0;

        String value = super.getPostParam(request, "value");
        if (value != null) {
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                return response.setBody(String.format("Value : %s is not an integer", value)).setStatus(HttpStatus.HTTP_BAD_REQUEST);
            }
        }

        compteur.put(name, intValue);

        return response.setBody(CompteurFormater.getJson(name, compteur.get(name))).setStatus(HttpStatus.HTTP_CREATED);
    }

    @Mapping(method = HttpMethod.PUT, path = "/(.*)")
    public Response update(Request request, Response response) {

        String compteurKey = request.getPathParam().get(0);

        if (!compteur.containsKey(compteurKey)) {
            return response.setBody(String.format("Le compteur %s n'a pas été trouvé", compteurKey)).setStatus(HttpStatus.HTTP_NOT_FOUND);
        }

        int intValue = 0;
        String value = super.getPostParam(request, "value");
        if (value != null) {
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                return response.setBody(String.format("Value : %s is not an integer", value)).setStatus(HttpStatus.HTTP_BAD_REQUEST);
            }
        } else {
            return response.setBody(String.format("Value parameter not found", value)).setStatus(HttpStatus.HTTP_BAD_REQUEST);
        }

        compteur.put(compteurKey, intValue);

        return response.setStatus(HttpStatus.HTTP_NO_CONTENT);
    }

    @Mapping(method = HttpMethod.DELETE, path = "/(.*)")
    public Response delete(Request request, Response response) {

        String compteurKey = request.getPathParam().get(0);

        if (!compteur.containsKey(compteurKey)) {
            return response.setBody(String.format("Le compteur %s n'a pas été trouvé", compteurKey)).setStatus(HttpStatus.HTTP_NOT_FOUND);
        }

        compteur.remove(compteurKey);

        return response.setStatus(HttpStatus.HTTP_NO_CONTENT);
    }

}
