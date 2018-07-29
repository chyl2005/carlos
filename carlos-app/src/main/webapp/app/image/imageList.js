var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
const dataUrl = rootPath + "console/image/imageList";
const markUrl = rootPath + "console/image/mark";
const deleteUrl = rootPath + "console/image/del";


const cateUrl = rootPath + "console/category/allCates";
$(document).ready(function () {


    loadData(pageCur, pageSize, dataUrl);
    $(".searchData").on('click', function () {
        pageCur = 1;
        // 加载数据
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
        type: "get",
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
        $("#lists").html('<tr ><td valign="top" colspan="6"  class="center">无符合条件数据！</td></tr>');
        return;
    }
    // 总数
    var total = data.data.totalDisplayRecords;
    var items = data.data.datas;
    var currentPage = pageCur;


    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        var row = rowTemplate.clone();
        row.find("#id").text(item.id);
        row.find("#imageName").text(item.imageName);
        row.find("#originPath").text(item.originPath);
        row.find("#gmtCreated").text(getSmpFormatDateByLong(item.gmtCreated, true));
        row.find("#gmtModified").text(getSmpFormatDateByLong(item.gmtModified, true));
        row.find("#isDel").text(item.state == 1 ? "删除" : "未删除");
        row.appendTo("#lists");

    });
    // 绑定分页方法
    paginator("#page", currentPage, pageSize, total, dataUrl);


    //编辑
    $(".btn-info").on('click', function () {
        showDialog(this);
    });

    //删除
    $(".btn-danger").on('click', function () {
        var pdata = {};
        pdata.imageId = $(this).closest("tr").find("#imageId").text();
        pdata.itemId = $("#itemId").val();
        $.ajax({
            url: deleteUrl,
            data: pdata,
            type: 'post',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (0 == data.status) {
                    loadData(pageCur, pageSize, dataUrl);
                } else {
                    alert(data.message);
                }
            }
        });
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
    var imageId = rowNode.find("#id").text();
    var imageName = rowNode.find("#imageName").text();
    dialog.find("input[name='imageId']").val(imageId);
    dialog.find("input[name='imageName']").val(imageName);
    loadCates();
}

function loadCates() {
    $.ajax({
        type: "post",
        url: cateUrl,
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.status === 0) {
                $("#editDialog").find(".labels").html("");
                var html = "";
                data.data.forEach((item) => {

                    var checkbox = '<input type="checkbox"  value="@id" />@name :';
                    // 根模块
                    html += checkbox.replace("@id", item.id).replace("@name", item.categoryName);

                });
                $("#editDialog").find(".labels").html(html);
            } else {
                alert(data.message);
            }
        }
    });
}


function saveOrUpdate() {
    var dialog = $("#editDialog");
    // 弹窗数据清空
    var postData = {};
    postData.imageId = dialog.find("input[name='imageId']").val();
    var categoryIds = [];

    dialog.find('input:checkbox').each(function (i) {
        if ($(this).prop("checked")) {
            categoryIds.push($(this).val());
        }
    });

    postData.categoryIds = categoryIds;

    $.ajax({
        type: "post",
        url: markUrl,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
        async: false,
        success: function (data) {
            if (data.status === 0) {
                // 弹窗数据清空
                dialog.find(":input").val("");
                dialog.modal('hide');
                loadData(pageCur, pageSize, dataUrl);
            } else {
                alert(data.message);
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
