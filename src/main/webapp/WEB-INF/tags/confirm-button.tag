<%@attribute name="onConfirm" %>
<%@attribute name="styleClass" %>

<div class="clickable confirm data-callback ${styleClass}" data-toggle="modal" data-target="#confirm-modal" data-trigger="#confirmChanges" data-callback="${onConfirm}">
    <jsp:doBody/>
</div>
