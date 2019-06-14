<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Add Facility">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.facility.edit"/></h1>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-sm-12">
                <form method="post" action="facility/save" class="form" role="form">
                    <input type="hidden" id="id" name="id" value="${data.getId()}">
                    <div class="form-group">
                        <fmt:message key="label.facility.enter-name" var="enterName" />
                        <label for="name"><fmt:message key="label.name"/>:</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="${enterName}" value="${data.getName()}">
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>