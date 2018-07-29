/**
 *
 */
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
const dataUrl = rootPath + "console/category/list";
const deleteUrl = rootPath + "console/category/del";
const saveOrUpdateUrl = rootPath + "console/category/saveOrUpdate";


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
        $("#lists").html('<tr ><td valign="top" colspan="7"  class="center">无符合条件数据！</td></tr>');
        return;
    }

    // 总数
    var total = data.data.totalDisplayRecords;
    var items = data.data.datas;
    var currentPage = pageCur;
    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        // 克隆tr模板
        var row = rowTemplate.clone();
        row.find("#id").text(item.id);
        row.find("#categoryName").text(item.categoryName);
        row.find("#gmtCreated").text(getSmpFormatDateByLong(item.gmtCreated, true));
        row.find("#gmtModified").text(getSmpFormatDateByLong(item.gmtModified, true));
        row.find("input[name='isDel']").prop('checked', item.isDel == 1 ? true : false);
        row.appendTo("#lists");
    });

    // 绑定分页方法
    paginator("#page", currentPage, pageSize, total, dataUrl);


    //删除
    $(".table .del").on('click', function () {
        var postData = {};
        postData.id = $(this).closest("tr").find("#itemId").text();
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





    $(".table .btn-info").on('click', function () {
        showDialog(this);
    });

}


// 查看详情
function showDialog(_this) {
    var dialog = $("#editDialog");
    // 弹窗数据清空
    dialog.find(":input").val("");
    dialog.modal("show");
    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var categoryId = rowNode.find("#id").text();
    var categoryName = rowNode.find("#categoryName").text();


    // 数据填充
    dialog.find("input[name='categoryId']").val(categoryId);
    dialog.find("input[name='categoryName']").val(categoryName);

    if ($(_this).hasClass("btn-xs")) {
        dialog.find("h4").text("修改");
    } else {
        dialog.find("h4").text("添加");
    }

}

function saveOrUpdate() {
    var dialog = $("#editDialog");
    // 弹窗数据清空
    var postData = {};
    postData.id = dialog.find("input[name='categoryId']").val();
    postData.categoryName = dialog.find("input[name='categoryName']").val();


    $.ajax({
        type: "post",
        url: saveOrUpdateUrl,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
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
