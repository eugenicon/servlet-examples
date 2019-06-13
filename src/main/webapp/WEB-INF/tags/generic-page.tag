<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="title" %>
<%@attribute name="showNavBar" type="java.lang.Boolean" %>
<%@attribute name="showFooter" type="java.lang.Boolean" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title id="title">${title}</title>
    <link rel="shortcut icon" href="static/img/favicon.ico" />

    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>

    <script src="static/lib/jquery/3.4.1/jquery.min.js"></script>
    <script src="static/lib/popper.js/1.14.7/popper.min.js"></script>
    <script src="static/lib/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="static/lib/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/lib/font-awesome/5.9.0/css/all.css">

    <link rel="stylesheet" href="static/css/styles.css">
    <script src="static/js/scripts.js"></script>
</head>
<body>
<c:if test="${showNavBar != false}">
    <t:nav-bar/>
</c:if>
<div id="pageheader">
    <jsp:invoke fragment="header"/>
</div>
<div id="body">
    <main role="main" class="container">
        <jsp:doBody/>
    </main>
</div>
<c:if test="${showFooter != false}">
    <div id="pagefooter">
        <jsp:invoke fragment="footer"/>

        <footer class="footer">
            <div class="container">
                <span class="text-muted">Copyright 2019, Bla-Bla-Bla Inc.</span>
            </div>
        </footer>
    </div>
</c:if>
</body>
</html>