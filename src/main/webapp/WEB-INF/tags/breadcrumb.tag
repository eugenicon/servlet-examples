<nav aria-label="breadcrumb" id="siteBreadcrumb">
    <ol class="breadcrumb">

    </ol>
</nav>
<script>
    //Breadcrumbs based on URL location
    var homeUrl = '${pageContext.request.contextPath}';
    var breadcrumb = $('#siteBreadcrumb ol.breadcrumb');
    if (breadcrumb) {
        var here = location.href.replace(/(\?.*)$/, '').split('/').slice(3);

        for (var i = 0; i < here.length; i++) {
            var part = here[i];
            var link = '/' + here.slice(0, i + 1).join('/');
            var pageName = (link === homeUrl) ? 'Home' : part.charAt(0).toUpperCase() + part.slice(1);
            breadcrumb.append('<li class="breadcrumb-item"><a href="' + link + '">' + pageName.replace(/\.(htm[l]?|asp[x]?|php|jsp)$/, '') + '</a></li>');
        }
    }
</script>