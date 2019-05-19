<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page title="Users">
    <jsp:attribute name="header">
      <h1>Users</h1>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Group</th>
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

                <a href="add-user-bootstrap" class="btn btn-primary floating-button">Add</a>
            </div>
        </div>
    </jsp:body>
</t:generic-page>