<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New User</title>
</head>
<body>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
        %> <h1><%=error%></h1> <%
    }
%>
<h1>Add user</h1>
<form method="post" action="add-user">
    Name: <input name="name" type="text"/> </br>
    Age:  <input name="age" type="number"/> </br>
    <button type="submit" >Save</button>
</form>
</body>
</html>
