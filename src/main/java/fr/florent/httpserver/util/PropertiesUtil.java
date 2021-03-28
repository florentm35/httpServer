package fr.florent.httpserver.util;

import fr.florent.httpserver.exception.system.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties prop = null;

    public static void load(String ressource) throws SystemException {
        prop = new Properties();

        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(ressource)) {
            prop.load(input);
        } catch (final IOException io) {
            throw new SystemException(io);
        }

    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }

}
