/**
 *
 */
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
const dataUrl = rootPath + "console/item/itemList";
const deleteUrl = rootPath + "console/item/del";
const editUrl = rootPath + "console/item/edit";
const saveOrUpdateUrl = rootPath + "console/item/saveOrUpdate";
const categoryUrl = rootPath + "console/category/getCategoryList";

$(document).ready(function () {

    loadCategory($("select[name='category']"));
    // 加载数据
    loadData(pageCur, pageSize, dataUrl);
    $(".searchData").on('click', function () {
        pageCur = 1;
        loadData(pageCur, pageSize, dataUrl);
    });

});


function loadCategory(element) {
    var result = {};
    $.ajax({
        url: categoryUrl,
        data: {},
        type: 'post',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.data) {
                var html = '';
                data.data.forEach((item) => {
                    var mid = item.id;
                    var name = item.categoryName;
                    html += '<option  value="' + mid + '">' + name + '</option>';
                });
                $(element).each(function () {
                    $(this).append(html);
                    $(this).find("option:first").attr("selected", true);
                });

            }
        }
    });
    return result;
}


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
        row.find("#itemId").text(item.itemId);
        row.find("#itemName").text(item.itemName);
        row.find("#itemNum").text(item.itemNum);
        row.find("#categoryId").text(item.categoryId);
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


    $(".table .btn-success").on('click', function () {
        var itemId = $(this).closest("tr").find("#itemId").text();
        window.location.href = editUrl + "?" + $.param({"itemId": itemId});
    });


    $(".table .edit").on('click', function () {
        showDialog(this);
    });

}


// 查看详情
function showDialog(_this) {
    var dialog = $("#editDialog");
    if ($(_this).hasClass("btn-xs")) {
        dialog.find("h4").text("修改");
    } else {
        dialog.find("h4").text("添加");
    }
    // 弹窗数据清空
    dialog.find(":input").val("");
    dialog.modal("show");
    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var itemId = rowNode.find("#itemId").text();
    if (itemId == null) {
        return;
    }
    $.ajax({
        type: "post",
        url: infoUrl,
        dataType: 'json',
        data: {
            "itemId": itemId
        },
        async: false,
        success: function (data) {
            if (data.status === 0) {
                $.each(data.data, function (key, value) {
                    dialog.find("input[name='" + key + "']").val(value);
                });


            }
        }
    });

}

function saveOrUpdate() {
    var dialog = $("#editDialog");
    // 弹窗数据清空
    var postData = {};
    postData.itemId = dialog.find("input[name='itemId']").val();
    postData.categoryId = dialog.find("select[name='category']").val();
    postData.itemName = dialog.find("input[name='itemName']").val();
    postData.itemNum = dialog.find("input[name='itemNum']").val();
    postData.description = dialog.find("textarea[name='description']").val();
    postData.special = dialog.find("textarea[name='special']").val();

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
