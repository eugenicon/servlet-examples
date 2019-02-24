<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Greetings! It's ${requestScope.currentDateFromBackend == null ? "some unknown time" : requestScope.currentDateFromBackend} on our server</h1>
    <c:if test="${not empty requestScope.currentDateFromBackend}">
        <div>
            This is a bit insecure, because we can get right to the jsp bypassing servlet. <a href="jsp/greetings.jsp">Try it</a>
        </div>
    </c:if>
    <c:if test="${empty requestScope.currentDateFromBackend}">
        <div>
            See - no date from backend:( Now try this <a href="../greeting-secured">Try it</a>
        </div>
    </c:if>
</body>
</html>
