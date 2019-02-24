<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Greetings from Secured! It's ${requestScope.currentDateFromBackend == null ? "some unknown time" : requestScope.currentDateFromBackend} on our server</h1>
    <c:if test="${not empty requestScope.currentDateFromBackend}">
        <div>
            This is secured. Try to access this jsp directly <a href="WEB-INF/jsp-secured/greetings-secured.jsp">Try it</a>
        </div>
    </c:if>
</body>
</html>
