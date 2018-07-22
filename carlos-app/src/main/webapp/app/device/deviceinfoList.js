/**
 * 收款日结算订单
 */
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
var dataUrl = rootPath + "deviceInfo/list";
var deleteUrl = rootPath + "deviceInfo/del";

var updateStatusUrl = rootPath + "deviceInfo/updateStatus";
var editUrl = rootPath + "deviceSetting/edit";

var recordUrl = rootPath + "deviceRecord/index";

var saveOrUpdateUrl = rootPath + "deviceInfo/saveOrUpdate";


var configInfoUrl = rootPath + "deviceConfig/info";

var configSaveOrUpdateUrl = rootPath + "deviceConfig/saveOrUpdate";
$(document).ready(function () {



    // 加载数据
    loadData(pageCur, pageSize, dataUrl);
    $(".searchData").on('click', function () {
        pageCur = 1;
        loadData(pageCur, pageSize, dataUrl);
    });

});


// 获取请求服务器数据
function getServerParams() {
    var postData = {};
    return postData;
}

// 数据加载
function loadData(currentPage, pageSize, url) {
    var postData = getServerParams();
    postData.startRow = (currentPage - 1) * pageSize;
    postData.pageSize = pageSize;
    $.ajax({
        type: "post",
        url: url,
        dataType: 'json',
        data: postData,
        async: false,
        success: dataCallbackShow
    })
}
// 数据填充
function dataCallbackShow(data) {
    // 表单清空
    $("#lists").empty();
    $("#page").empty();

    // 数据空判断
    if (data.data == null || data.data.datas == null || data.data.datas.length == 0) {
        $("#lists").html('<tr ><td valign="top" colspan="9"  class="center">无符合条件数据！</td></tr>');
        return;
    }

    // 总数
    var total = data.data.iTotalDisplayRecords;
    var items = data.data.datas;
    var currentPage = pageCur;
    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        // 克隆tr模板
        var row = rowTemplate.clone();
        row.find("#deviceId").text(item.deviceId);
        row.find("#serialNum").text(item.serialNum);
        row.find("#deviceNum").text(item.deviceNum);
        row.find("#cityName").text(item.cityName);
        row.find("#provinceName").text(item.provinceName);
        row.find("#ip").text(item.clientIP);
        row.find("#gmtCreated").text(getSmpFormatDateByLong(item.gmtCreated, true));
        row.find("#gmtModified").text(getSmpFormatDateByLong(item.gmtModified, true));
        row.find("input[name='online']").prop('checked', item.online == 1 ? true : false);
        row.appendTo("#lists");
    });

    // 绑定分页方法
    paginator("#page", currentPage, pageSize, total, dataUrl);


    //编辑
    $(".table .delStatus").on('click', function () {
        var postData = {};
        postData.id = $(this).closest("tr").find("#id").text();
        var isDel = $(this).closest("tr").find("input[name='delStatus']").prop('checked');
        postData.isDel = isDel ? 1 : 0;
        $.ajax({
            url: updateStatusUrl,
            data: postData,
            type: 'post',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (null != data) {
                    loadData(pageCur, pageSize, dataUrl);

                }
            }
        });
    });

    //编辑
    $(".table .config").on('click', function () {
        showDialog(this);
    });


    //删除
    $(".table .del").on('click', function () {
        var postData = {};
        postData.id = $(this).closest("tr").find("#id").text();
        $.ajax({
            url: deleteUrl,
            data: postData,
            type: 'post',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (null != data) {
                    loadData(pageCur, pageSize, dataUrl);

                }
            }
        });
    });
    //设备记录
    $(".table .chart").on('click', function () {
        var deviceId = $(this).closest("tr").find("#deviceId").text();
        window.location.href = recordUrl + "?" + $.param({"deviceId": deviceId});
    });

    $(".table .edit").on('click', function () {
        var deviceId = $(this).closest("tr").find("#deviceId").text();
        window.location.href = editUrl + "?" + $.param({"deviceId": deviceId});
    });

}


// 查看详情
function showDialog(_this) {
    var dialog = $("#configDialog");
    // 弹窗数据清空
    dialog.find(":input").val("");
    dialog.modal("show");
    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var deviceId = rowNode.find("#deviceId").text();
    $.ajax({
        type: "post",
        url: configInfoUrl,
        dataType: 'json',
        data: {
            "deviceId": deviceId
        },
        async: false,
        success: function (data) {
            if (data.status === 0) {
                $.each(data.data, function (key, value) {
                    console.log("设备配置信息---" + JSON.stringify(data.data));
                    dialog.find("input[name='" + key + "']").val(value);
                });


            }
        }
    });

}

function saveOrUpdate() {
    var dialog = $("#configDialog");
    // 弹窗数据清空
    var postData = {};
    postData.id = dialog.find("input[type=hidden]").val();
    postData.parentId = dialog.find("#parentModule").val();
    postData.name = dialog.find("input[name='name']").val();
    postData.url = dialog.find("input[name='url']").val();
    postData.icon = dialog.find("input[name='icon']").val();
    var isLink = dialog.find("input[type=checkbox]").prop('checked');
    postData.isLink = isLink ? 1 : 0;
    $.ajax({
        type: "post",
        url: saveOrUpdateUrl,
        dataType: 'json',
        data: postData,
        async: false,
        success: function (data) {
            if (data != null) {
                // 弹窗数据清空
                dialog.find(":input").val("");
                dialog.modal('hide');
                loadData(pageCur, pageSize, dataUrl);
            }
        }
    });

}

// 分页方法 element jquery selector
function paginator(element, currentPage, pageSize, totalCount, url) {
    var pageCount = Math.ceil(totalCount / pageSize);
    var options = {
        currentPage: currentPage,
        totalPages: pageCount,
        bootstrapMajorVersion: 3,
        alignment: "right",
        numberOfPages: 5,
        size: "normal",
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "尾页";
                case "page":
                    return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page) {
            // 加载数据
            loadData(page, pageSize, url);
            pageCur = page;
        }
    }
    // 渲染分页模块
    $(element).bootstrapPaginator(options);
}
