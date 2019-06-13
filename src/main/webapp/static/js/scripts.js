let htmlBaseElement = document.querySelector('base');

const base = (htmlBaseElement || {}).href;

document.onloadend(function () {
    const es = document.getElementsByTagName('a');
    for (let i = 0; i < es.length; i++) {
        let href = es[i].getAttribute('href');
        if (href && href.startsWith("#")) {
            es[i].onclick(e => e.preventDefault());
        }
    }
});

function navigate(url) {
    window.location.href=url;
}

function post(path, params) {
    const method='post';

    const form = document.createElement('form');
    form.method = method;
    form.action = path;

    for (const key in params) {
        if (params.hasOwnProperty(key)) {
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = params[key];

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

function prepareFileInputs() {
    $(".input-file").before(
        function() {
            if ( ! $(this).prev().hasClass('input-ghost') ) {
                var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name",$(this).attr("name"));
                element.change(function(){
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function(){
                    element.click();
                });
                $(this).find("button.btn-reset").click(function(){
                    element.val(null);
                    $(this).parents(".input-file").find('input').val('');
                });
                $(this).find('input').css("cursor","pointer");
                $(this).find('input').mousedown(function() {
                    $(this).parents('.input-file').prev().click();
                    return false;
                });
                return element;
            }
        }
    );
}
$(function() {
    prepareFileInputs();
});

function cloneElement(source, target) {
    let clone = $(source + ':first').clone();
    $(target).append(clone);
}