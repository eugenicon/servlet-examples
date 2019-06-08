<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<t:generic-page title="Add File">
    <jsp:attribute name="header">
      <h1><fmt:message key="label.add-file"/></h1>
    </jsp:attribute>

    <jsp:body>
        <t:exception/>
        <div class="row">
            <div class="col-12">
                <form method="post" action="files/save" enctype="multipart/form-data" class="form" role="form">
                    <input type="hidden" id="id" name="id" value="${file.getId()}">
                    <t:file-chooser id="file" name="file" />
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.save"/></button>
                </form>
            </div>
        </div>
    </jsp:body>
</t:generic-page>