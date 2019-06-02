<li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${lang.toUpperCase()}</a>
    <div class="dropdown-menu" aria-labelledby="dropdown01">
        <div class="dropdown-item" >
            <form role="form" method="post" action="set-language" style="margin-bottom: 0px;">
                <input type="hidden" name="language" value="en">
                <button class="btn btn-flat" type="submit">EN</button>
            </form>
        </div>
        <div class="dropdown-item" >
            <form role="form" method="post" action="set-language" style="margin-bottom: 0px;">
                <input type="hidden" name="language" value="ru">
                <button class="btn btn-flat" type="submit">RU</button>
            </form>
        </div>
    </div>
</li>