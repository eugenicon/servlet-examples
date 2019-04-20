package net.example.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class ResourceReader {
    private static final Logger LOGGER = Logger.getLogger(ResourceReader.class.getName());
    private static final String CLASSPATH_PREFIX = "classpath:";

    public static InputStream getResourceAsStream(String path) {
        boolean isClasspathResource = path != null && path.startsWith(CLASSPATH_PREFIX);
        try {
            if (isClasspathResource) {
                return ResourceReader.class.getClassLoader().getResourceAsStream(path.substring(CLASSPATH_PREFIX.length()));
            } else {
                return new FileInputStream(path);
            }
        } catch (Exception e) {
            if (!isClasspathResource) {
                return ResourceReader.getResourceAsStream(CLASSPATH_PREFIX + path);
            } else {
                LOGGER.warning("Could not read resource for path " + path);
            }
        }

        return new ByteArrayInputStream(new byte[]{});
    }

    public static String getResourceAsString(String path) {
        try(Scanner sc = new Scanner(ResourceReader.getResourceAsStream(path))) {
            return sc.useDelimiter("\\A").next();
        } catch (Exception e) {
            LOGGER.warning("Could not read lines of resource for path " + path);
        }
        return "";
    }

    public static Properties getResourceAsProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(ResourceReader.getResourceAsStream(path));
        } catch (IOException e) {
            LOGGER.warning("Could not read properties for resource " + path);
        }
        return properties;
    }
}
