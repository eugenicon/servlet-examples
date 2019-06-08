<%@attribute name="name" %>
<%@attribute name="id" %>

<div class="form-group">
    <div class="input-group input-file" name="${name}" id="${id}">
        <input type="text" class="form-control input-group-prepend" placeholder='Choose a file...' />
        <span class="input-group-btn input-group-append">
            <button class="input-group-text btn-choose" type="button">Choose</button>
        </span>
    </div>
</div>
