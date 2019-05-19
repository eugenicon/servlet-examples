package net.example.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Reflection {

    public static List<Class> getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packageUrl = packageName.replace(".", File.separator);
        URL resourceUrl = classLoader.getResource(packageUrl);
        try {
            Path path = Paths.get(Objects.requireNonNull(resourceUrl).toURI());
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(file -> {
                        try {
                            String filePath = file.getPath();
                            String className = filePath.substring(filePath.indexOf(packageUrl), filePath.lastIndexOf("."));
                            return Class.forName(className.replace(File.separator, "."));
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> Map<Method, A> getAnnotatedMethods(Class type, Class<A> annotationType) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(method -> new Pair<>(method, ((A) Arrays.stream(method.getDeclaredAnnotations())
                        .filter(annotation -> annotationType.isAssignableFrom(annotation.annotationType())).findFirst().orElse(null))))
                .filter(pair -> pair.getRight() != null)
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}
