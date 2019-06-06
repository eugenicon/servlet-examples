<%@attribute name="onConfirm" %>

<div class="clickable confirm data-callback" data-toggle="modal" data-target="#confirm-modal" data-trigger="#confirmChanges" data-callback="${onConfirm}">
    <jsp:doBody/>
</div>
