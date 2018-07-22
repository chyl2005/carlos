/**
 * 收款日结算订单
 */
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
const dataUrl = rootPath + "user/list";

const saveOrUpdateUrl = rootPath + "user/saveOrUpdate";

const roleUrl = rootPath + "user/getRoles";
const deleteUrl = rootPath + "user/deleteEntity";
const saveOrUpdateUserRoleUrl = rootPath + "user/saveOrUpdateUserRole";
$(document).ready(function () {

    // 加载数据
    loadData(pageCur, pageSize, dataUrl);
    $("#searchData").on('click', function () {
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
        $("#lists").html('<tr ><td valign="top" colspan="8"  class="center">无符合条件数据！</td></tr>');
        return;
    }
    // 总数
    var total = data.data.iTotalDisplayRecords;
    var items = data.data.datas;
    var currentPage = pageCur;


    var rowTemplate = $("#template").children();
    items.forEach((item) => {
        var row = rowTemplate.clone();
        row.find("#id").text(item.id);
        row.find("#userName").text(item.userName);
        row.find("#trueName").text(item.trueName);
        row.find("#roleName").text(item.roleName);
        row.find("#gmtCreated").text(getSmpFormatDateByLong(item.gmtCreated, true));
        row.find("#gmtModified").text(getSmpFormatDateByLong(item.gmtModified, true));
        row.find("#isDel").text(item.state == 1 ? "删除" : "未删除");
        row.appendTo("#lists");

    });
    // 绑定分页方法
    paginator("#page", currentPage, pageSize, total, dataUrl);


    $(".btn-success").on('click', function () {
        distributeRole(this);
    });
    //编辑
    $(".btn-info").on('click', function () {
        saveOrUpdateDialog(this);
    });

    //删除
    $(".btn-danger").on('click', function () {
        postData.id = $(this).closest("tr").find("#id").text();
        $.ajax({
            url: deleteUrl,
            data: postData,
            type: 'post',
            dataType: 'json',
            async: true,
            success: function (data) {
                if (0 == data.data.status) {
                    loadData(pageCur, pageSize, dataUrl);

                } else {
                    alert(data.message);
                }
            }
        });
    });


}

function saveOrUpdateDialog(_this) {
    var dialog = $("#saveOrUpdateDialog");
    // 弹窗数据清空
    dialog.find(":input").val("");
    dialog.modal("show");
    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var userId = rowNode.find("#id").text();
    var trueName = rowNode.find("#trueName").text();
    var userName = rowNode.find("#userName").text();
    dialog.find("input[name='userId']").val(userId);
    // 数据填充
    dialog.find("input[name='trueName']").val(trueName);
    dialog.find("input[name='userName']").val(userName);
}

// 修改或保存
function saveOrUpdate() {
    var dialog = $("#saveOrUpdateDialog");
    var postData = {};
    postData.id = dialog.find("input[name='userId']").val();
    postData.userName = dialog.find("input[name='userName']").val();
    postData.trueName = dialog.find("input[name='trueName']").val();
    postData.password = dialog.find("input[name='password']").val();

    $.ajax({
        url: saveOrUpdateUrl,
        data: postData,
        type: 'post',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.status === 0) {
                loadData(pageCur, pageSize, dataUrl);
                dialog.find(":input").val("");
                dialog.modal("hide");
            } else {
                alert(data.message);
            }
        }
    });
}

// 分配角色
function distributeRole(_this) {
    var dialog = $("#distributeRoleDialog");
    dialog.modal("show");
    // 弹窗数据清空
    dialog.find("#userId").val("");

    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var userId = rowNode.find("#id").text();
    dialog.find("#userId").val(userId);
    loadRoles(userId);

}

/**
 * 加载所有角色，根据用户 查询拥有哪些角色的权限
 *
 * @date 2016年3月21日 下午3:11:00
 * @param userId
 */
function loadRoles(userId) {
    $.ajax({
            type: "get",
            url: roleUrl,
            dataType: 'json',
            data: {
                "userId": userId
            },
            async: false,
            success: function (data) {
                if (data.status === 0) {
                    $("#distributeRoleDialog").find("#modules").html("");
                    var html = "";
                    data.data.forEach((item) => {
                        var checkbox1 = '<span><input type="checkbox" value="@roleId"  @checked   onchange="saveOrUpdateRole(this)""/>@roleName </span>';
                        // 根模块
                        html += checkbox1.replace("@roleId", item.id).replace("@checked",
                            item.isAuthority == 1 ? 'checked="checked"' : "").replace("@roleName",
                            item.roleName);

                    })

                    $("#distributeRoleDialog").find("#modules").html(html);
                } else {
                    alert(data.message);
                }
            }
        });
}

/**
 * 保存用户角色信息
 *
 * @date 2016年3月21日 下午4:04:40
 * @param _this
 */
function saveOrUpdateRole(_this) {

    var dialog = $("#distributeRoleDialog");
    var userId = dialog.find("#userId").val();
    var roleId = $(_this).val();
    var postData = {};
    // 选中 添加 取消选中删除
    postData.operate = _this.checked ? 1 : 0;
    postData.userId = userId;
    postData.roleId = roleId;

    $.ajax({
        url: saveOrUpdateUserRoleUrl,
        data: postData,
        type: 'post',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.status === 0) {
                alert("成功");
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
