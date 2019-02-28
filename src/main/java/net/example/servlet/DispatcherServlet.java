package net.example.servlet;

import net.example.controller.UserController;
import net.example.controller.WelcomeController;
import net.example.service.UserService;
import net.example.tranforemer.UserTransformer;
import net.example.view.RedirectView;
import net.example.view.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = -1641096228274971485L;

    private Map<String, Function<HttpServletRequest, View>> getControllers = new HashMap<>();
    private Map<String, Function<HttpServletRequest, View>> postControllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        WelcomeController controller = new WelcomeController();
        UserController userController = new UserController(new UserService());

        getControllers.put("/", r -> controller.doWelcome());
        getControllers.put("/welcome", r -> controller.doWelcome());
        getControllers.put("/user-list", r -> userController.getUserList());
        getControllers.put("/add-user", r -> userController.showAddUserPage());

        UserTransformer userTransformer = new UserTransformer();

        postControllers.put("/add-user", r -> userController.addUser(userTransformer.transform(r)));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        View view = getView(request, getControllers);
        if (view != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(view.getPageUrl());
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        View view = getView(request, postControllers);
        if (view != null) {
            if (view instanceof RedirectView) {
                view = getView(view.getPageUrl(), request, getControllers);
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(view.getPageUrl());
            requestDispatcher.forward(request, response);
        }
    }

    private View getView(HttpServletRequest request, Map<String, Function<HttpServletRequest, View>> controllerSource) {
        String requestURI = request.getRequestURI().replace(request.getContextPath() + "/view", "");
        return getView(requestURI, request, controllerSource);
    }

    private View getView(String key, HttpServletRequest request, Map<String, Function<HttpServletRequest, View>> controllerSource) {
        Function<HttpServletRequest, View> viewFunction = controllerSource.get(key);

        View view = null;
        if (viewFunction != null) {
            view = viewFunction.apply(request);
            view.getParams().forEach(request::setAttribute);
        }
        return view;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}