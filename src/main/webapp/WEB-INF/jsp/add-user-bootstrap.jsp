<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Add User">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.add-user"/></h1>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-sm-12">
                <form method="post" action="add-user" class="form" role="form">
                    <div class="form-group">
                        <fmt:message key="label.enter-user-name" var="enterUserName" />
                        <label for="name"><fmt:message key="label.name"/>:</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="${enterUserName}" value="${user.getName()}">
                    </div>
                    <div class="form-group">
                        <fmt:message key="label.enter-user-age" var="enterUserAge" />
                        <label for="age"><fmt:message key="label.age"/>:</label>
                        <input type="number" class="form-control" id="age" name="age" placeholder="${enterUserAge}" value="${user.getAge()}">
                    </div>

                    <div class="form-group">
                        <label class="mr-sm-2" for="group_id"><fmt:message key="label.group"/></label>
                        <select class="custom-select mr-sm-2" id="group_id" name="group_id" >
                            <c:if test="${user == null || user.getGroup() == null}">
                                <fmt:message key="label.enter-user-group" var="enterUserGroup" />
                                <option disabled hidden selected>${enterUserGroup}...</option>
                            </c:if>
                            <c:if test="${user.getGroup() != null}">
                                <option value="${user.getGroup().getId()}" >${user.getGroup().getName()}</option>
                            </c:if>
                            <c:forEach var="item" items="${groups}">
                                <c:if test="${user.getGroup().getId() != item.getId()}">
                                    <option value="${item.getId()}" >${item.getName()}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="group"><fmt:message key="label.comment"/>:</label>
                        <textarea class="form-control" rows="5" id="group"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>