<%@ attribute name="styleClass" %>
<%@attribute name="item" required="true" type="net.example.data.model.FileData"%>
<%@attribute name="width" type="java.lang.Integer"%>

<img class="${styleClass}" src="data:image/jpg;base64,${item.getEncoded()}" width="${width}" alt="${item.getName()}"/>
