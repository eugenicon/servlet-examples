<%@tag description="Error template" pageEncoding="UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%> <h1 style="white-space: pre;"><%=error%>
</h1> <%
    }
%>