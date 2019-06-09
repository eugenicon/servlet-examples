package net.example.resolver;

import net.example.util.ResourceReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static net.example.util.Reflection.getClassesAnnotated;
import static net.example.util.Reflection.getGenericTypes;

@SuppressWarnings("unchecked")
public class DependencyResolver {
    private static final Logger LOGGER = LogManager.getLogger(DependencyResolver.class);
    private static DependencyResolver instance;

    private Map<Class, Object> componentInstances = new HashMap<>();

    private DependencyResolver() {
        Properties properties = ResourceReader.getResourceAsProperties("application.properties");
        componentInstances.put(Properties.class, properties);

        List<Class> componentClasses = getClassesAnnotated("net.example", Component.class);
        componentClasses.forEach(c -> resolveComponentInstance(componentClasses, c));
    }

    private Object resolveComponentInstance(List<Class> declaredComponentClasses, Class componentClass) {
        if (!componentInstances.containsKey(componentClass)) {
            try {
                componentInstances.put(componentClass, createInstance(declaredComponentClasses, componentClass));
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
        return componentInstances.get(componentClass);
    }

    private Object createInstance(List<Class> declaredComponentClasses, Class componentClass) throws Exception {
        Constructor constructor = componentClass.getDeclaredConstructors()[0];
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameters[i].getType();
            if (parameterType.equals(List.class)) {
                Class actualParameterType = getGenericTypes(parameters[i].getParameterizedType()).get(0);
                List<Class> classList = declaredComponentClasses.stream().filter(actualParameterType::isAssignableFrom).collect(Collectors.toList());
                args[i] = classList.stream().map(c -> resolveComponentInstance(declaredComponentClasses, c)).collect(Collectors.toList());
            } else if (parameterType.equals(Lazy.class)) {
                Class actualParameterType = getGenericTypes(parameters[i].getParameterizedType()).get(0);
                componentInstances.put(parameterType, Lazy.of(() -> resolveComponentInstance(declaredComponentClasses, actualParameterType)));
                args[i] = componentInstances.get(parameterType);
            } else if (componentInstances.containsKey(parameterType) || declaredComponentClasses.contains(parameterType)) {
                args[i] = resolveComponentInstance(declaredComponentClasses, parameterType);
            }
        }
        return constructor.newInstance(args);
    }

    private static DependencyResolver getInstance() {
        if (instance == null) {
            synchronized (DependencyResolver.class) {
                if (instance == null) {
                    instance = new DependencyResolver();
                }
            }
        }
        return instance;
    }

    public static <T> T getComponent(Class<T> type) {
        return (T) getInstance().componentInstances.get(type);
    }
}
