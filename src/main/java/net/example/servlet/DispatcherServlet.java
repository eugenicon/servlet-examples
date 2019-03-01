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
import java.util.Optional;
import java.util.function.Function;

public class DispatcherServlet extends HttpServlet {
    private static final String VIEW_ATTRIBUTE = "VIEW_ATTRIBUTE";

    private Map<String, Function<HttpServletRequest, View>> getControllers = new HashMap<>();
    private Map<String, Function<HttpServletRequest, View>> postControllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        WelcomeController controller = new WelcomeController();
        UserController userController = new UserController(new UserService());

        getControllers.put("/", r -> controller.doWelcomeRedirect());
        getControllers.put("/welcome", r -> controller.doWelcome());
        getControllers.put("/user-list", r -> userController.getUserList());
        getControllers.put("/add-user", r -> userController.showAddUserPage());
        getControllers.put("/user-list-bootstrap", r -> userController.getUserListBootstrap());
        getControllers.put("/add-user-bootstrap", r -> userController.showAddUserPageBootsrtap());

        UserTransformer userTransformer = new UserTransformer();

        postControllers.put("/add-user", r -> userController.addUser(userTransformer.transform(r)));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(getView(request, getControllers), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(getView(request, postControllers), request, response);
    }

    private void dispatch(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (view == null) {
            // do nothing
        } else if (view instanceof RedirectView) {
            request.getSession().setAttribute(VIEW_ATTRIBUTE, view.getView());
            response.sendRedirect(view.getPageUrl());
        } else {
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

    @Override
    public void destroy() {
        super.destroy();
    }
}