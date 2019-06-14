<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Facilities">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.facilities"/></h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th><fmt:message key="label.name"/></th>
                        <th></th>
                    </tr>
                    </thead>

                    <c:forEach var="item" items="${listOfData}">
                        <tr>
                            <td>${item.getId()}</td>
                            <td>${item.getName()}</td>
                            <td class="tr-table-options">
                                <div class="table-options">
                                    <i class="fas fa-pen" onclick="navigate('facility/edit/${item.getId()}')"></i>
                                    <t:confirm-button onConfirm="post('facility/delete/${item.getId()}')">
                                        <i class="fas fa-times"></i>
                                    </t:confirm-button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <a href="facility/add" class="btn btn-primary floating-button"><fmt:message key="label.add"/></a>
            </div>
        </div>

        <t:confirm-modal/>
    </jsp:body>
</t:generic-page>