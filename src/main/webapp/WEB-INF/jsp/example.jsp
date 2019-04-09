<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Template Page
    </jsp:attribute>
    <jsp:attribute name="header">
      <h1>Welcome</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <p id="copyright">Copyright 2019, Bla-Bla-Bla Inc.</p>
    </jsp:attribute>

    <jsp:body>
        <div class="row">
            <div class="col-sm-4">
                <p>Hi I'm the body in bootstrap!</p>
            </div>
        </div>
    </jsp:body>
</t:genericpage>