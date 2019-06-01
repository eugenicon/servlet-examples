package net.example.resolver;

import net.example.controller.Controller;
import net.example.data.validation.Valid;
import net.example.data.validation.ValidationException;
import net.example.data.validation.ValidationService;
import net.example.tranforemer.TransformationService;
import net.example.view.RedirectView;
import net.example.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.example.util.Reflection.getAnnotatedMethods;
import static net.example.util.Reflection.isAnnotated;

@Component
public class RequestResolver {
    private static final Logger LOGGER = LogManager.getLogger(RequestResolver.class);

    private static final String VIEW_ATTRIBUTE = "VIEW_ATTRIBUTE";

    private final ValidationService validationService;
    private final TransformationService transformationService;

    private final Map<String, Function<HttpServletRequest, View>> getControllers = new HashMap<>();
    private final Map<String, Function<HttpServletRequest, View>> postControllers = new HashMap<>();
    private final Map<String, Function<HttpServletRequest, View>> errorControllers = new HashMap<>();

    public RequestResolver(TransformationService transformationService, ValidationService validationService, List<Controller> controllers) {
        this.transformationService = transformationService;
        this.validationService = validationService;
        for (Controller controller : controllers) {
            getAnnotatedMethods(controller.getClass(), GetMapping.class).forEach((method, mapping) -> getControllers.put(mapping.value(), request -> invokeController(controller, method, request)));
            getAnnotatedMethods(controller.getClass(), PostMapping.class).forEach((method, mapping) -> postControllers.put(mapping.value(), request -> invokeController(controller, method, request)));
            getAnnotatedMethods(controller.getClass(), ExceptionMapping.class).forEach((method, mapping) -> errorControllers.put(getExceptionMapping(controller, mapping.value()), request -> invokeController(controller, method, request)));
        }
    }

    private View invokeController(Controller controller, Method method, HttpServletRequest request) {
        try {
            List<Object> args = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                Object value = transformationService.transform(request, parameter);
                if (value != null && isAnnotated(parameter, Valid.class)) {
                    List<String> validationErrors = validationService.validate(value);
                    if (!validationErrors.isEmpty()) {
                        throw new ValidationException(validationErrors);
                    }
                }
                args.add(value);
            }
            return (View) method.invoke(controller, args.toArray());
        } catch (Exception e) {
            Throwable cause = e.getCause() == null ? e : e.getCause();
            LOGGER.warn("error while dispatching {} request {}", request.getMethod(), request.getRequestURI(), cause);

            String exceptionMapping = getExceptionMapping(controller, cause.getClass());
            if (errorControllers.containsKey(exceptionMapping)) {
                request.setAttribute("error", cause);
                return errorControllers.get(exceptionMapping).apply(request);
            } else {
                throw new RuntimeException(cause);
            }
        }
    }

    private String getExceptionMapping(Controller controller, Class<? extends Throwable> eClass) {
        return String.format("%s:%s", controller, eClass);
    }

    public void resolveGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, getControllers);
    }

    public void resolvePostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, postControllers);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, Map<String, Function<HttpServletRequest, View>> getControllers) throws IOException, ServletException {
        LOGGER.info("dispatching {} request {}", request.getMethod(), request.getRequestURI());
        try {
            dispatch(getView(request, getControllers), request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getCause());
            request.getRequestDispatcher("/view/error").forward(request, response);
        }
    }

    private void dispatch(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (view instanceof RedirectView) {
            request.getSession().setAttribute(VIEW_ATTRIBUTE, view.getView());
            response.sendRedirect(view.getPageUrl());
        } else if (view != null){
            String pageUrl = (view.getPageUrl().endsWith(".jsp") ? "WEB-INF/jsp/" : "") + view.getPageUrl();
            view.getParams().forEach(request::setAttribute);
            request.getRequestDispatcher("/" + pageUrl).forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private View getView(HttpServletRequest request, Map<String, Function<HttpServletRequest, View>> controllerSource) {
        View originView = (View) request.getSession().getAttribute(VIEW_ATTRIBUTE);
        request.getSession().removeAttribute(VIEW_ATTRIBUTE);

        String key = resolvePath(request, controllerSource.keySet());
        View destinationView = Optional.ofNullable(controllerSource.get(key)).map(t -> t.apply(request)).orElse(null);
        if (originView != null && destinationView != null) {
            originView.getParams().forEach(destinationView::addParameter);
        }
        return destinationView;
    }

    private String resolvePath(HttpServletRequest request, Set<String> keys) {
        String key = request.getRequestURI().replace(request.getContextPath() + "/view", "");
        if (keys.contains(key)) {
            return key;
        }

        for (String k : keys) {
            String[] pathParts = k.split("\\{");
            StringBuilder pattern = new StringBuilder(pathParts[0]);
            for (int i = 1; i < pathParts.length; i++) {
                String[] subParts = pathParts[i].split("\\}");
                pattern.append("(.+)");
                for (int j = 1; j < subParts.length; j++) {
                    pattern.append(subParts[j]);
                }
            }
            pattern.append("$");
            Matcher matcher = Pattern.compile(pattern.toString()).matcher(key);
            if (matcher.find()) {
                for (int i = 1; i < pathParts.length; i++) {
                    String paramName = pathParts[i].split("\\}")[0];
                    String paramValue = matcher.group(i);
                    request.setAttribute(paramName, paramValue);
                }

                return k;
            }
        }
        return "";
    }
}
