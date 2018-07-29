var saveOrUpdateUrl = rootPath + "console/item/rel/saveOrUpdate";
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
const dataUrl = rootPath + "console/item/rel/imageList";

const deleteUrl = rootPath + "console/item/rel/del";
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
    postData.itemId = $("#content").find("input[name='itemId']").val();
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
        $("#lists").html('<tr ><td valign="top" colspan="8"  class="center">无符合条件数据！</td></tr>');
        return;
    }
    // 总数
    var total = data.data.totalDisplayRecords;
    var items = data.data.datas;
    var currentPage = pageCur;


    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        var row = rowTemplate.clone();
        row.find("#imageId").text(item.id);
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
        saveOrUpdateDialog(this);
    });

    //删除
    $(".btn-danger").on('click', function () {
        var pdata = {};
        pdata.imageId = $(this).closest("tr").find("#imageId").text();
        pdata.itemId =  $("#content").find("input[name='itemId']").val();
        $.ajax({
            url: deleteUrl,
            data: pdata,
            type: 'get',
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


function upload(_this) {
    if (validateImage(_this)) {
        var postData = {};
        postData.itemId = $("#content").find("input[name='itemId']").val();
        var fileElementId = _this.id;
        //上传文件
        $.ajaxFileUpload({
            url: rootPath + 'console/image/upload',//处理图片脚本
            secureuri: false,
            fileElementId: fileElementId,//file控件id
            dataType: 'json',
            data: postData,
            success: function (data) {
                if (data.status == 0) {
                    $("#" + fileElementId).attr("data", data.data.imageId);
                } else {
                    alert("上传图片失败,失败原因" + data.data.message);
                }
            }
        });
    }
}


//校验图片格式及大小 Add Date 2012-6-14 LIUYI
function validateImage(obj) {
    var file = obj;
    var tmpFileValue = file.value;

    //校验图片格式
    if (/^.*?\.(gif|png|jpg|jpeg|bmp)$/.test(tmpFileValue.toLowerCase())) {
        return true;
    } else {
        alert("只能上传jpg、jpeg、png、bmp或gif格式的图片！");
        return false;
    }

    if (file.value != "") {

    } else {
        alert("请选择上传的文件!");
        return false;
    }
}


function saveOrUpdate() {
    // 弹窗数据清空
    var postData = {};
    postData.itemId = $("#content").find("input[name='itemId']").val();

    var imageId = $("#content").find("input[name='uploadFile']").attr("data");
    var imageIds = [];
    imageIds.push(imageId)
    postData.imageIds = imageIds;


    $.ajax({
        type: "post",
        url: saveOrUpdateUrl,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(postData),
        async: false,
        success: function (data) {
            if (data.status === 0) {
                pageCur = 1;
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
