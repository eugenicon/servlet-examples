<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Files">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.files"/></h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th><fmt:message key="label.name"/></th>
                        <th><fmt:message key="label.size"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>

                    <c:forEach var="item" items="${listOfData}">
                        <tr>
                            <td class="align-middle">${item.getId()}</td>
                            <td class="align-middle">${item.getName()}</td>
                            <td class="align-middle">${item.getPrettySize()}</td>
                            <td style="width: 205px;">
                                <t:img-base64 item="${item}" width="180"/>
                            </td>
                            <td class="tr-table-options align-middle">
                                <div class="table-options">
                                    <i class="fas fa-pen" onclick="navigate('files/edit/${item.getId()}')"></i>
                                    <t:confirm-button onConfirm="post('files/delete/${item.getId()}')">
                                        <i class="fas fa-times"></i>
                                    </t:confirm-button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <a href="files/add" class="btn btn-primary floating-button"><fmt:message key="label.add"/></a>
            </div>
        </div>

        <t:confirm-modal/>
    </jsp:body>
</t:generic-page>