<%@ page import="net.example.data.model.Group" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <title>New User</title>
</head>
<body>
<t:exception/>
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <h1>Add user</h1>
            <form method="post" action="add-user" class="form" role="form">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter Your Name">
                </div>
                <div class="form-group">
                    <label for="age">Age:</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Enter Your Age">
                </div>

                <div class="col-auto my-1">
                    <label class="mr-sm-2" for="group_id">Group</label>
                    <select class="custom-select mr-sm-2" id="group_id" name="group_id">
                        <option selected>Choose...</option>

                        <%
                            List<Group> groups = (List<Group>) request.getAttribute("groups");
                            for (Group group : groups) {
                        %>
                        <option value="<%=group.getId()%>"><%=group.getName()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <div class="form-group">
                    <label for="group">Comment:</label>
                    <textarea class="form-control" rows="5" id="group"></textarea>
                </div>

                <div class="form-group">
                    <label for="comment">Comment:</label>
                    <textarea class="form-control" rows="5" id="comment"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
