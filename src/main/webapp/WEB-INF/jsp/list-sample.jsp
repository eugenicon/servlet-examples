<%@ page import="net.example.data.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Date date = (Date) request.getAttribute("currentDateFromBackend");
%>
<h1>Greetings! It's  <%=date == null ? "some unknown time" : date%> on our server</h1>
<table>
    <% List<User> users = (List<User>) request.getAttribute("listOfData");
        for (User user : users) { %>

            <tr>
                <td>
                    <%=String.format("%s of age %s", user.getName(), user.getAge())%>
                </td>
            </tr>

        <%} %>
</table>
<a href="add-user">Add</a>
</body>
</html>
