package net.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/greeting-secured")
public class JspForwardingServletSecured extends HttpServlet {
    private static final long serialVersionUID = 6837201192036769589L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentDateFromBackend", new Date());

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp-secured/greetings-secured.jsp");
        view.forward(req, resp);
    }
}