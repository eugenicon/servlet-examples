<%@attribute name="exception" required="true" type="java.lang.Exception"%>

<jsp:scriptlet>
  exception.printStackTrace(new java.io.PrintWriter(out));
</jsp:scriptlet>
