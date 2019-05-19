package net.example.servlet;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static net.example.util.Reflection.getAnnotatedMethods;

public class RequestResolver {
    private static final Logger LOGGER = LogManager.getLogger(RequestResolver.class);

    private static final String VIEW_ATTRIBUTE = "VIEW_ATTRIBUTE";

    private Map<String, Function<HttpServletRequest, View>> getControllers = new HashMap<>();
    private Map<String, Function<HttpServletRequest, View>> postControllers = new HashMap<>();
    private Map<String, Function<HttpServletRequest, View>> errorControllers = new HashMap<>();

    public RequestResolver(TransformationService transformationService, Object... controllers) {
        for (Object controller : controllers) {
            getAnnotatedMethods(controller.getClass(), GetMapping.class).forEach((method, mapping) -> getControllers.put(mapping.value(), request -> invokeController(transformationService, controller, method, request)));
            getAnnotatedMethods(controller.getClass(), PostMapping.class).forEach((method, mapping) -> postControllers.put(mapping.value(), request -> invokeController(transformationService, controller, method, request)));
            getAnnotatedMethods(controller.getClass(), ExceptionMapping.class).forEach((method, mapping) -> errorControllers.put(getExceptionMapping(controller, mapping.value()), request -> invokeController(transformationService, controller, method, request)));
        }
    }

    private View invokeController(TransformationService transformationService, Object controller, Method method, HttpServletRequest request) {
        try {
            Object[] args = Arrays.stream(method.getParameterTypes()).map(t -> transformationService.transform(request, t)).toArray();
            return (View) method.invoke(controller, args);
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

    private String getExceptionMapping(Object controller, Class<? extends Throwable> eClass) {
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
            request.setAttribute("error", e);
            request.getRequestDispatcher("/view/error").forward(request, response);
        }
    }

    private void dispatch(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (view instanceof RedirectView) {
            request.getSession().setAttribute(VIEW_ATTRIBUTE, view.getView());
            response.sendRedirect(view.getPageUrl());
        } else if (view != null){
            view.getParams().forEach(request::setAttribute);
            request.getRequestDispatcher("/" + view.getPageUrl()).forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private View getView(HttpServletRequest request, Map<String, Function<HttpServletRequest, View>> controllerSource) {
        String requestURI = request.getRequestURI().replace(request.getContextPath() + "/view", "");
        return getView(requestURI, request, controllerSource);
    }

    private View getView(String key, HttpServletRequest request, Map<String, Function<HttpServletRequest, View>> controllerSource) {
        View originView = (View) request.getSession().getAttribute(VIEW_ATTRIBUTE);
        request.getSession().removeAttribute(VIEW_ATTRIBUTE);

        View destinationView = Optional.ofNullable(controllerSource.get(key)).map(f -> f.apply(request)).orElse(null);
        if (originView != null && destinationView != null) {
            originView.getParams().forEach(destinationView::addParameter);
        }
        return destinationView;
    }
}
