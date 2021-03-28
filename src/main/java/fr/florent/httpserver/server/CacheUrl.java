package fr.florent.httpserver.server;

import fr.florent.httpserver.http.HttpMethod;
import fr.florent.httpserver.process.annotation.Mapping;
import fr.florent.httpserver.process.annotation.Path;
import fr.florent.httpserver.request.Request;
import fr.florent.httpserver.util.ResourceHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CacheUrl {

    private static HashMap<HttpMethod, HashMap<String, Method>> cacheUrl = new HashMap<>();

    public static void scan() {

        for (Package packageScan : Package.getPackages()) {
            Set<Class<?>> lstClass = ResourceHelper.getTypesAnnotatedWith(packageScan.getName(), Path.class);

            if (lstClass != null && !lstClass.isEmpty()) {

                for (Class tClass : lstClass) {
                    Path pathAnnotation = (Path) tClass.getAnnotation(Path.class);
                    Set<Method> lstMethod = ResourceHelper.getAnnotedMethodFromClass(tClass, Mapping.class);

                    if (lstMethod != null && !lstMethod.isEmpty()) {

                        for (Method method : lstMethod) {
                            addMapping(pathAnnotation.value(), method);

                        }

                    }

                }
            }

        }
    }

    private static void addMapping(String parentPath, Method method) {

        Mapping mapping = method.getAnnotation(Mapping.class);

        synchronized (cacheUrl) {

            if (!cacheUrl.containsKey(mapping.method())) {
                cacheUrl.put(mapping.method(), new HashMap<>());
            }
            var map = cacheUrl.get(mapping.method());
            map.put(parentPath + mapping.path(), method);

        }

    }

    public static Method getMethodFormRequest(Request request) {

        synchronized (cacheUrl) {
            var map = cacheUrl.get(request.getMethod());

            if (map != null) {

                // On supprime les query paramètre pour le mapping
                String path = request.getPath().split("\\?")[0];

                for (Map.Entry<String, Method> entry : map.entrySet()) {
                    if (entry.getKey().contains(".*")) {

                        Pattern pattern = Pattern.compile(entry.getKey());

                        Matcher matcher = pattern.matcher(path);
                        if (matcher.find()) {
                            List<String> param = new ArrayList<>();
                            request.setPathParam(param);
                            for (int i = 0; i < matcher.groupCount(); i++) {
                                param.add(matcher.group(i+1));
                            }

                            return entry.getValue();
                        }

                    } else if (entry.getKey().equals(path)) {
                        return entry.getValue();
                    }
                }
            }
        }
        return null;
    }

}
