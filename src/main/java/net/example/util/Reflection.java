package net.example.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Reflection {

    public static List<Class> getClassesAnnotated(String packageName, Class<? extends Annotation> annotation) {
        List<Class> classes = getClasses(packageName);
        return classes.stream()
                .filter(c -> isAnnotated(c, annotation))
                .collect(Collectors.toList());
    }

    public static List<Class> getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packageUrl = packageName.replace(".", File.separator);
        URL resourceUrl = classLoader.getResource(packageUrl);
        try {
            Path path = Paths.get(Objects.requireNonNull(resourceUrl).toURI());
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(file -> getClass(packageUrl, file))
                    .filter(Objects::nonNull)
                    .flatMap(c -> {
                        List<Class<?>> classes = new ArrayList<>(Arrays.asList(c.getClasses()));
                        classes.add(c);
                        return classes.stream();
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static List<Class> getGenericTypes(Type type) {
        if (type instanceof ParameterizedType) {
            return Arrays.stream(((ParameterizedType) type).getActualTypeArguments())
                    .map(Reflection::getClass)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static boolean isAnnotated(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return Arrays.stream(element.getAnnotations()).anyMatch(a -> a.annotationType().isAssignableFrom(annotationType));
    }

    private static Class<?> getClass(String packageUrl, File file) {
        String filePath = file.getPath();
        String className = filePath.substring(filePath.indexOf(packageUrl), filePath.lastIndexOf("."));
        return getClass(className.replace(File.separator, "."));
    }

    private static Class getClass(Type type) {
        String className = type.getTypeName();
        if (type instanceof ParameterizedType) {
            className = ((ParameterizedType) type).getRawType().getTypeName();
        }
        return getClass(className);
    }

    private static Class<?> getClass(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            return null;
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
