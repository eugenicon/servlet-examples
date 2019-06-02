<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Login" showNavBar="false">
    <jsp:body>
        <div class="row text-center justify-content-center">
            <form class="form-group col-4" method="post" action="login">
                <img class="mb-4" src="static/img/accountlogin-icon.png" alt="" width="72" height="72">
                <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

                <div class="form-group">
                    <label for="userName" class="sr-only">Username</label>
                    <input type="email" id="userName" name="userName" class="form-control" placeholder="Email address" required autofocus value="${login.getUserName()}">
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required value="${login.getPassword()}">
                </div>

                <div class="form-group">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </div>
            </form>
        </div>
    </jsp:body>
</t:generic-page>