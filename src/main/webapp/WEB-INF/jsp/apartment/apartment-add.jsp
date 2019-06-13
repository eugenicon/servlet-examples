<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cfn" uri="/WEB-INF/tld/customTagLibrary" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:generic-page title="Add Group">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.apartment.edit"/></h1>
      <script src="static/js/gallery.js"></script>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-12">
                <form method="post" action="apartments/save" class="form" role="form">
                    <input type="hidden" id="id" name="id" value="${data.getId()}">

                    <nav>
                        <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
                            <a class="nav-item nav-link active" id="nav-tab-1" data-toggle="tab" href="#nav-content-1" role="tab" aria-controls="nav-home" aria-selected="true">Profile</a>
                            <a class="nav-item nav-link" id="nav-tab-2" data-toggle="tab" href="#nav-content-2" role="tab" aria-controls="nav-profile" aria-selected="false">Description</a>
                            <a class="nav-item nav-link" id="nav-tab-3" data-toggle="tab" href="#nav-content-3" role="tab" aria-controls="nav-contact" aria-selected="false">Images</a>
                        </div>
                    </nav>
                    <div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-content-1" role="tabpanel" aria-labelledby="nav-tab-1">

                            <div class="form-group">
                                <fmt:message key="label.apartment.enter-name" var="enterName"/>
                                <label for="name"><fmt:message key="label.name"/>:</label>
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="${enterName}" value="${data.getName()}">
                            </div>
                            <div class="form-group">
                                <label class="mr-sm-2" for="type"><fmt:message key="label.apartment.type"/></label>
                                <select class="custom-select mr-sm-2" id="type" name="type">
                                    <c:if test="${data.getType() == null}">
                                        <fmt:message key="label.apartment.enter-type" var="enterType"/>
                                        <option disabled hidden selected>${enterType}...</option>
                                    </c:if>
                                    <c:if test="${data.getType() != null}">
                                        <option value="${data.getType()}">${data.getType()}</option>
                                    </c:if>
                                    <c:forEach var="item" items="${types}">
                                        <c:if test="${data.getType() != item}">
                                            <option value="${item}">${item}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="numberOfPlaces"><fmt:message key="label.apartment.number-of-places"/>:</label>
                                <input type="number" class="form-control" id="numberOfPlaces" name="numberOfPlaces" value="${data.getNumberOfPlaces()}">
                            </div>
                            <div class="form-group">
                                <label for="address"><fmt:message key="label.address"/>:</label>
                                <textarea class="form-control" rows="1" id="address" name="address">${data.getAddress()}</textarea>
                            </div>

                        </div>
                        <div class="tab-pane fade" id="nav-content-2" role="tabpanel" aria-labelledby="nav-tab-2">

                            <textarea class="form-control" rows="11" id="description" name="description">${data.getDescription()}</textarea>

                        </div>
                        <div class="tab-pane fade" id="nav-content-3" role="tabpanel" aria-labelledby="nav-tab-3">
                            <div class="container">
                                <div class="row">
                                    <c:forEach var="image" items="${images}">
                                        <div class="col-lg-3 col-md-4 col-xs-6 thumb">

                                            <div class="thumbnail" data-image-id="" data-title="${image.getName()}"
                                               data-image="data:image/jpg;base64,${image.getEncoded()}">
                                                <t:img-base64 item="${image}" styleClass="img-thumbnail" />
                                                <div class="options">
                                                    <input type="checkbox" name="images[]" value="${image.getId()}" style="width: 15px; height: 15px;" ${data.getImages().contains(image) ? 'checked': ''}>
                                                    <i class="fas fa-search clickable" data-toggle="modal" data-target="#image-gallery"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="row">
                                    <div class="modal fade" id="image-gallery" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title" id="image-gallery-title"></h4>
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <img id="image-gallery-image" class="img-responsive col-md-12" src="">
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary float-left" id="show-previous-image"><i class="fa fa-arrow-left"></i>
                                                    </button>

                                                    <button type="button" id="show-next-image" class="btn btn-secondary float-right"><i class="fa fa-arrow-right"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>