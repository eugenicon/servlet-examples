<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <h5 class="my-0 mr-md-auto font-weight-normal navbar-brand clickable" onclick="navigate('welcome')">My App</h5>
    <ul class="navbar-nav my-2 my-md-0 mr-md-3">
        <c:if test="${auth.role eq 'USER' or auth.role eq 'ADMIN'}">
            <t:nav-item url="group/list" label="groups"/>
        </c:if>
        <c:if test="${auth.role eq 'ADMIN'}">
            <t:nav-item url="user/list" label="users"/>
        </c:if>
        <t:nav-language/>
    </ul>
    <c:if test="${auth.role eq 'UNKNOWN'}">
        <a class="btn btn-outline-primary" href="login">Sign in</a>
    </c:if>
    <c:if test="${auth.role != 'UNKNOWN'}">
        <a class="btn btn-outline-primary" href="logout">Sign out</a>
    </c:if>
</div>