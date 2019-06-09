<%@attribute name="isNot" type="java.lang.String" %>
<%@attribute name="is" type="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="allowed" value="${(is == null || is.isEmpty()) ? true : fn:contains(is, auth.role)}" />
<c:set var="restricted" value="${isNot.isEmpty() ? false : fn:contains(isNot, auth.role)}" />
<c:if test="${allowed and not restricted}">
    <jsp:doBody/>
</c:if>
