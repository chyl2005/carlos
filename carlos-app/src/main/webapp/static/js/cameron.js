/*==================
 * @authors Adrian
 * @date    2015-5-19 12:40:20
 * @version
 ===================*/


$(function () {
    // body...
    var win_W = $(window).width(),
        win_H = $(window).height();


    if (win_W > 720) {
        // Search
        $('.search-ipt').focus(function () {
            $(this).css({
                "z-index": "1",
                "padding-left": "40px",
                "border": "1px solid #e8e8e8"
            }).animate({
                "width": "120px"
            }, "fast")
        }).blur(function () {
            $(this).css({
                "z-index": "3",
                "padding-left": "0px",
                "border": "none"
            }).animate({
                "width": "40px"
            }, "fast")
        })
    }


    // Nav
    $('.oper-item--toggle').bind('click', function () {
        $('.nav').stop(true, false).slideToggle();
    })

    //Logo
    $(window).scroll(function () {
        var top = $(document).scrollTop();

        if (top > 400) {
            $('.logo-wrap').addClass('active');
        } else {
            $('.logo-wrap').removeClass('active');
        }
    })


    //Tab
    $('.honor-owl-item').on('click', function () {
        $('.honor-cont .tab-bd-item').eq($(this).parent().index()).fadeIn()
            .siblings('.tab-bd-item').hide();
        $(this).addClass('current').parent().siblings().children().removeClass('current');
    })


    //Back to top
    $(window).scroll(function () {
        if ($(window).scrollTop() > 100) {
            $(".backtop").fadeIn(1500);
        }
        else {
            $(".backtop").fadeOut(1500);
        }
    });
    $(".backtop").click(function () {
        $('body,html').animate({scrollTop: 0}, 1000);
        return false;
    });


    //banner
    $(window).scroll(function () {
        $('.banner img').css('margin-top', $(window).scrollTop() / 1);
    });


    $('.lr').css({
        'height': win_H - 350,
    })


    //Select
    $(".lang-sele").TransformSelect();


})