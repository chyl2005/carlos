$(document).ready(function () {


    $.ajax({
        type: 'post',
        url: rootPath + 'index/left',
        async: true,
        dataType: "json",
        success: function (data) {
            var html = '';
            if (data.status === 0) {
                data.data.forEach((menu) => {

                    var menuHtml = '<li > <a href="@url" class="@isLink"> '
                        + '<i class="@icon"></i> '
                        + '<span class="menu-text">@name</span>'
                        + ' @angle-down</a>'
                        + ' <b class="arrow"></b>';

                    if (menu.isLink === 1) {
                        menuHtml = menuHtml.replace("@url", rootPath + menu.url);
                        menuHtml = menuHtml.replace("@isLink", "");
                        menuHtml = menuHtml.replace("@angle-down", '');
                    } else {
                        menuHtml = menuHtml.replace("@url", "#");
                        menuHtml = menuHtml.replace("@isLink", "dropdown-toggle");
                        menuHtml = menuHtml.replace("@angle-down", '<b class="arrow fa fa-angle-down"></b>');
                    }
                    menuHtml = menuHtml.replace("@name", menu.name);
                    menuHtml = menuHtml.replace("@icon", menu.icon);
                    html += menuHtml;
                    if (menu.subMenus != null && menu.subMenus.length > 0) {
                        for (var i = 0; i < menu.subMenus.length; i++) {
                            var subMenu = menu.subMenus[i];
                            if (i === 0) {
                                html += '<ul class="submenu">';
                            }
                            var subMenuHtml = '<li > <a href="@url"> <i class="menu-icon fa fa-caret-right"></i>@name </a> <b class="arrow"></b> </li>';
                            subMenuHtml = subMenuHtml.replace("@url", rootPath + subMenu.url);
                            subMenuHtml = subMenuHtml.replace("@name", subMenu.name);
                            html += subMenuHtml;
                            if (i === menu.subMenus.length - 1) {
                                html += ' </ul>';
                            }


                        }
                    }

                    html += '</li>';

                });
            }

            $('.nav-list').html(html);

            $(".submenu li a").each(function () {
                var path = document.location.pathname;
                var _href = $(this).attr("href");
                if (_href.indexOf(path) > -1) {
                    $(this).parent().addClass("active").siblings().removeClass('active');
                    //$(this).closest(".submenu").show().siblings().hide();
                    $(this).closest(".submenu").parent().addClass("open").siblings().removeClass('open');
                }
            });
        }
    });


});