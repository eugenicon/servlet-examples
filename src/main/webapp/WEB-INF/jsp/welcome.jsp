<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:generic-page title="Welcome">
    <jsp:body>
        <div style="text-align: center">
            <img src="static/img/welcome.png">
        </div>

        <p>
            <a href="welcome">welcome</a>
        </p>
        <p>
            <a href="user-list-regular">user-list</a>
        </p>
        <p>
            <a href="add-user-regular">add-user</a>
        </p>
        <p>
            <a href="user-list">user-list-bootstrap</a>
        </p>

        <p>
            <a href="group-list">group-list</a>
        </p>

        <p>
            <a href="add-group">add-group</a>
        </p>

        <p>
            <a href="problem-page">problem-page</a>
        </p>

        <p>
            <a href="template-example">template-example</a>
        </p>
    </jsp:body>
</t:generic-page>