<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cfn" uri="/WEB-INF/tld/customTagLibrary" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:generic-page title="${data.getName()}">
    <jsp:attribute name="header">
        <h1>${data.getName()}</h1>
    </jsp:attribute>

    <jsp:body>
        <div>
            <div class="col-10 offset-1">
                <div class="text-muted" style="padding-bottom: 10px;">
                        ${data.getAddress()}
                </div>

                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <c:set var="firstImage" value="${data.getImages().get(0)}"/>
                    <ol class="carousel-indicators">
                        <c:forEach var="image" items="${data.getImages()}">
                            <li data-target="#carouselExampleControls" data-slide-to="${data.getImages().indexOf(image)}" class="${image == firstImage ? 'active' : ''}"></li>
                        </c:forEach>
                    </ol>
                    <div class="carousel-inner">
                        <c:forEach var="image" items="${data.getImages()}">
                            <div class="carousel-item ${image == firstImage ? 'active' : ''}">
                                <t:img-base64 item="${image}" styleClass="d-block w-100"/>
                            </div>
                        </c:forEach>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

                <div class="text">
                        ${data.getDescription()}
                </div>

                <div style="padding-top: 25px;">
                    <table class="table-borderless">
                        <tr>
                            <td class="font-weight-bold">Owner:</td>
                            <td>${data.getOwner().getName()}</td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Apartment Type:</td>
                            <td>${data.getType()}</td>
                        </tr>
                        <tr>
                            <td class="font-weight-bold">Places:</td>
                            <td>${data.getNumberOfPlaces()}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

    </jsp:body>
</t:generic-page>