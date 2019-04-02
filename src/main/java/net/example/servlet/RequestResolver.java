package net.example.servlet;

import net.example.controller.ErrorController;
import net.example.controller.GroupController;
import net.example.controller.UserController;
import net.example.controller.WelcomeController;
import net.example.tranforemer.UserTransformer;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class RequestResolver {
    private static final String VIEW_ATTRIBUTE = "VIEW_ATTRIBUTE";

    private Map<String, Function<HttpServletRequest, View>> getControllers = new HashMap<>();
    private Map<String, Function<HttpServletRequest, View>> postControllers = new HashMap<>();

    public RequestResolver(WelcomeController welcomeController, UserController userController, GroupController groupController, ErrorController errorController, UserTransformer userTransformer) {
        getControllers.put("/", r -> welcomeController.doWelcomeRedirect());
        getControllers.put("/welcome", r -> welcomeController.doWelcome());
        getControllers.put("/user-list", r -> userController.getUserList());
        getControllers.put("/add-user", r -> userController.showAddUserPage());
        getControllers.put("/user-list-bootstrap", r -> userController.getUserListBootstrap());
        getControllers.put("/add-user-bootstrap", r -> userController.showAddUserPageBootstrap());
        getControllers.put("/group-list", r -> groupController.getAll());
        getControllers.put("/problem-page", r -> errorController.getViewWithSomeTestException());
        getControllers.put("/error", r -> errorController.getErrorPage((Exception) r.getAttribute("error")));

        postControllers.put("/add-user", r -> userController.addUser(userTransformer.transform(r)));
    }

    public void resolveGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, getControllers);
    }

    public void resolvePostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request, response, postControllers);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, Map<String, Function<HttpServletRequest, View>> getControllers) throws IOException, ServletException {
        try {
            dispatch(getView(request, getControllers), request, response);
        } catch (Exception e) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/error");
            request.setAttribute("error", e);
            requestDispatcher.forward(request, response);
        }
    }

    private void dispatch(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (view instanceof RedirectView) {
            request.getSession().setAttribute(VIEW_ATTRIBUTE, view.getView());
            response.sendRedirect(view.getPageUrl());
        } else if (view != null){
            view.getParams().forEach(request::setAttribute);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + view.getPageUrl());
            requestDispatcher.forward(request, response);
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
