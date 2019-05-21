<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Users">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.users"/></h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th><fmt:message key="label.name"/></th>
                        <th><fmt:message key="label.age"/></th>
                        <th><fmt:message key="label.group"/></th>
                    </tr>
                    </thead>

                    <c:forEach var="item" items="${listOfData}">
                        <tr>
                            <td>${item.getId()}</td>
                            <td>${item.getName()}</td>
                            <td>${item.getAge()}</td>
                            <td>${item.getGroup().getName()}</td>
                        </tr>
                    </c:forEach>
                </table>

                <a href="add-user-bootstrap" class="btn btn-primary floating-button"><fmt:message key="label.add"/></a>
            </div>
        </div>
    </jsp:body>
</t:generic-page>