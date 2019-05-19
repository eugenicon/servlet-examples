<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page title="Add User">
    <jsp:attribute name="header">
      <h1>Add User</h1>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-sm-12">
                <form method="post" action="add-user" class="form" role="form">
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Enter Your Name" value="${user.getName()}">
                    </div>
                    <div class="form-group">
                        <label for="age">Age:</label>
                        <input type="number" class="form-control" id="age" name="age" placeholder="Enter Your Age" value="${user.getAge()}">
                    </div>

                    <div class="form-group">
                        <label class="mr-sm-2" for="group_id">Group</label>
                        <select class="custom-select mr-sm-2" id="group_id" name="group_id" >
                            <c:if test="${user == null || user.getGroup() == null}">
                                <option disabled hidden selected>Choose...</option>
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
                        <label for="group">Comment:</label>
                        <textarea class="form-control" rows="5" id="group"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>