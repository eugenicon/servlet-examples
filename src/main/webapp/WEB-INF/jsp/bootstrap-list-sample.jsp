<%@ page import="net.example.data.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <title>Bootstrap Page</title>
</head>
<body>
<%
    Date date = (Date) request.getAttribute("currentDateFromBackend");
%>
<h1>Greetings! </h1>
<h3>It's  <%=date == null ? "some unknown time" : date%> on our server</h3>
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>User Name</th>
                    <th>Age</th>
                </tr>
                </thead>
                <% List<User> users = (List<User>) request.getAttribute("listOfData");
                    for (User user : users) { %>

                <tr>
                    <td>
                        <%=user.getName()%>
                    </td>
                    <td>
                        <%=user.getAge()%>
                    </td>
                </tr>

                <%} %>
            </table>

            <a href="add-user-bootstrap" class="btn btn-primary">Add</a>
        </div>
    </div>
</div>
</body>
</html>
