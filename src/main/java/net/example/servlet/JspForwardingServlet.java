package net.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/greeting")
public class JspForwardingServlet extends HttpServlet {
    private static final long serialVersionUID = 6837201192036769589L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentDateFromBackend", new Date());

        RequestDispatcher view = req.getRequestDispatcher("jsp/greetings.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        RequestDispatcher view = request.getRequestDispatcher("html/success.html");
        view.forward(request, response);
    }

}