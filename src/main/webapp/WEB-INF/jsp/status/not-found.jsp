<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:generic-page title="Not Found" showNavBar="false" showFooter="false">
    <jsp:body>
        <div class="page-wrap d-flex flex-row align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-12 text-center">
                        <span class="display-1 d-block">404</span>
                        <div class="mb-4 lead">The page you are looking for was not found.</div>
                        <a href="welcome" class="btn btn-link">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:generic-page>
