package net.example.servlet;

import net.example.config.ComponentInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    private RequestResolver requestResolver;

    @Override
    public void init() throws ServletException {
        requestResolver = ComponentInitializer.getInstance().getRequestResolver();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestResolver.resolveGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestResolver.resolvePostRequest(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}