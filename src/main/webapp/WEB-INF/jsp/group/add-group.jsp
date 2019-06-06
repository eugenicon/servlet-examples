<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Add Group">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.add-group"/></h1>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-sm-12">
                <form method="post" action="group/add" class="form" role="form">
                    <div class="form-group">
                        <fmt:message key="label.enter-group-name" var="enterGroupName" />
                        <label for="name"><fmt:message key="label.name"/>:</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="${enterGroupName}" value="${group.getName()}">
                    </div>

                    <div class="form-group">
                        <label for="comment"><fmt:message key="label.comment"/>:</label>
                        <textarea class="form-control" rows="5" id="comment"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>