<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="User ${user.getName()}">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.user.info"/></h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <table class="col">
                <tr>
                    <td class="title">Id</td>
                    <td>${user.getId()}</td>
                </tr>
                <tr>
                    <td class="title"><fmt:message key="label.name"/></td>
                    <td>${user.getName()}</td>
                </tr>
                <tr>
                    <td class="title"><fmt:message key="label.age"/></td>
                    <td>${user.getAge()}</td>
                </tr>
                <tr>
                    <td class="title"><fmt:message key="label.group"/></td>
                    <td>${user.getGroup().getName()}</td>
                </tr>
            </table>
        </div>
        <div class="row floating-button">
            <a href="user/list" class="col btn btn-secondary "><fmt:message key="label.users"/></a>
        </div>
    </jsp:body>
</t:generic-page>