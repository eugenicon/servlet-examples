<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Greetings! It's ${requestScope.currentDateFromBackend == null ? "some unknown time" : requestScope.currentDateFromBackend} on our server</h1>
</body>
</html>
