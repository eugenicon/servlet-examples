<%@tag description="Application navigation bar" pageEncoding="UTF-8" %>
<%@attribute name="url" %>
<%@attribute name="label" %>

<li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'].contains(url) ? 'active' : ''}">
    <a class="nav-link" href="${url}">${label} <span class="sr-only">(current)</span></a>
</li>