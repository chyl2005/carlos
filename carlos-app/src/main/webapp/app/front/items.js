const dataUrl = rootPath + "/itemDatas";

var thrFoot = false;
const win_H = $(window).height();
const win_W = $(window).width();
//商品列表
$(document).ready(function () {

    loadData(url);


    $('.section-bg').css({
        'height': win_H - 362,
    })


});


// 数据加载
function loadData(currentPage, pageSize, url) {
    $.ajax({
        type: "post",
        url: url,
        dataType: 'json',
        data: {},
        async: false,
        success: dataCallbackShow
    })
}

// 数据填充
function dataCallbackShow(data) {
    var items = data.data;
    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        var row = rowTemplate.clone();
        row.find("h2").html(item.categoryName);
        row.find("span").text(item.description);
        var url = "/itemList" + "?" + $.param({"categoryId": item.categoryId});
        row.find("a").attr("href", url);
        row.appendTo("#fullpage");

    });


    $('#fullpage').fullpage({
        anchors: ['Page1', 'Page2', 'Page3', 'Page4'],
        navigation: true,
        navigationPosition: 'right',
        navigationTooltips: ['', '', '', '', ''],
        showActiveTooltip: false,
        loopBottom: true,
        verticalCentered: false,

        afterResize: function () {
            // console.log(hdH);
            var pluginContainer = $(this);
            console.log();
        },
        onLeave: function (index, nextIndex, direction) {
            var leavingSection = $(this);
            // alert(thrFoot);
            //after leaving
            if (index != 4) {
                thrFoot = false;
                $('#fullpage').css({'margin-top': '0px'});
            }
            if (index == 4 && nextIndex == 1) {
                thrFoot = true;
                showFoo();
                return false;
            }
            if (index == 4 && nextIndex == 3) {

                if (thrFoot) {
                    thrFoot = false;
                    $('#fullpage').css({'margin-top': '0px'});
                    return false;
                }

            }
        }
    });

}

function showFoo() {
    if (win_W > 1000) {
        $('#fullpage').css({'margin-top': '-268px'});
    } else {

        $('#fullpage').css({'margin-top': '-229px'});
    }
}



