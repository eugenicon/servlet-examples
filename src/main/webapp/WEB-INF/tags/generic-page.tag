<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="title" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>
    <script>const base = document.getElementsByTagName("base")[0].href;</script>

    <link rel="stylesheet" href="static/css/styles.css">
    <script src="static/js/scripts.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <title id="title">${title}</title>
</head>
<body>
<t:nav-bar/>
<div id="pageheader">
    <jsp:invoke fragment="header"/>
</div>
<div id="body">
    <main role="main" class="container">
        <jsp:doBody/>
    </main>
</div>
<div id="pagefooter">
    <jsp:invoke fragment="footer"/>

    <footer class="footer">
        <div class="container">
            <span class="text-muted">Copyright 2019, Bla-Bla-Bla Inc.</span>
        </div>
    </footer>
</div>
</body>
</html>