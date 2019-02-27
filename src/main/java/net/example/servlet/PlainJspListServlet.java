package net.example.servlet;

import net.example.data.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet("/list")
public class PlainJspListServlet extends HttpServlet {
    private static final Random RANDOM = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = IntStream.range(0, 5).mapToObj(i -> new User("name" + i, RANDOM.nextInt(70))).collect(Collectors.toList());
        req.setAttribute("currentDateFromBackend", new Date());
        req.setAttribute("listOfData", users);

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp-secured/list-sample.jsp");
        view.forward(req, resp);
    }
}