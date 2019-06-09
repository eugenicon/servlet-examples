<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Apartments">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.apartments"/></h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row d-flex justify-content-center">
                <c:forEach var="item" items="${listOfData}">
                    <div class="card apartment-card">
                        <c:if test="${!item.getImages().isEmpty()}" >
                            <t:img-base64 styleClass="card-img-top" item="${item.getImages().get(0)}"/>
                        </c:if>
                        <div class="card-body">
                            <h5 class="card-title">${item.getName()}</h5>
                            <c:if test="${item.getImages().isEmpty()}" >
                                <p class="card-text apartment-card">${item.getDescription()}</p>
                            </c:if>
                            <small class="text-muted">${item.getAddress()}</small>
                        </div>
                        <div class="card-footer">
                            <a href="apartments/edit/${item.getId()}" class="card-link">Edit</a>
                            <a href="#" class="card-link">View</a>
                            <t:confirm-button styleClass="card-link" onConfirm="post('apartments/delete/${item.getId()}')">
                                <a class="hyperlink">Delete</a>
                            </t:confirm-button>
                        </div>
                    </div>
                </c:forEach>

                <a href="apartments/add" class="btn btn-primary floating-button"><fmt:message key="label.add"/></a>
        </div>

        <t:confirm-modal/>
    </jsp:body>
</t:generic-page>