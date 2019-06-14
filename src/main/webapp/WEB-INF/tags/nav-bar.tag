<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <h5 class="my-0 mr-md-auto font-weight-normal navbar-brand clickable" onclick="navigate('welcome')">My App</h5>
    <ul class="navbar-nav my-2 my-md-0 mr-md-3">
        <t:if-role is="OWNER,ADMIN">
            <t:nav-item url="apartments/list" label="label.apartments"/>
        </t:if-role>
        <t:if-role is="ADMIN">
            <t:nav-item url="facility/list" label="label.facilities"/>
        </t:if-role>
        <t:if-role isNot="UNKNOWN">
            <t:nav-item url="files/list" label="label.files"/>
        </t:if-role>
        <t:if-role is="USER,ADMIN">
            <t:nav-item url="group/list" label="label.groups"/>
        </t:if-role>
        <t:if-role is="ADMIN">
            <t:nav-item url="user/list" label="label.users"/>
        </t:if-role>
        <t:nav-language/>
    </ul>

    <div class="btn-group" >
        <t:if-role is="UNKNOWN">
            <a class="btn btn-outline-secondary" href="register">Sign up</a>
            <a class="btn btn-outline-primary" href="login">Sign in</a>
        </t:if-role>
        <t:if-role isNot="UNKNOWN">
            <a class="btn btn-outline-primary" href="logout">Sign out</a>
        </t:if-role>
    </div>
</div>