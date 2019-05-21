<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@tag description="Application navigation bar" pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="#">My App</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <t:nav-item url="welcome" label="home"/>
            <t:nav-item url="group-list" label="groups"/>
            <t:nav-item url="user-list-bootstrap" label="users"/>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${sessionScope.lang}</a>
                <div class="dropdown-menu" aria-labelledby="dropdown01">
                    <div class="dropdown-item" >
                        <form role="form" method="post" action="set-language" style="margin-bottom: 0px;">
                            <input type="hidden" name="language" value="en">
                            <button class="btn btn-flat" type="submit">EN</button>
                        </form>
                    </div>
                    <div class="dropdown-item" >
                        <form role="form" method="post" action="set-language" style="margin-bottom: 0px;">
                            <input type="hidden" name="language" value="ru">
                            <button class="btn btn-flat" type="submit">RU</button>
                        </form>
                    </div>
                </div>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>