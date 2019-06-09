<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:generic-page title="Error" showNavBar="false" showFooter="false">
    <jsp:body>
        <div class="page-wrap d-flex flex-row align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-12 text-center">
                        <span class="display-1 d-block">500</span>
                        <div class="mb-4 lead">Oops! Something went really wrong...
                            <div >${error.getMessage()}</div>
                        </div>
                        <a href="welcome" class="btn btn-link">Back to Home</a>
                        <a data-toggle="collapse" href="#collapse1" class="btn btn-link">Stacktrace</a>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse">
                        <div class="card card-body" style="white-space: pre-line;">
                            <t:stack-trace exception="${error}"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:generic-page>
