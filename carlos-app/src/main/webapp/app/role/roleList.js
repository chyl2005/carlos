/**
 * 角色
 */
var pageCur = 1;
var pageSize = 10;
// 列表数据请求url
var dataUrl = rootPath + "role/list";


var saveAuthUrl = rootPath + "role/saveAuth";
var roleModuleUrl = rootPath + "role/getAllModuleAuthority";
var roleSaveOrUpdateUrl = rootPath + "role/saveOrUpdate";

var deleteUrl = rootPath + "role/deleteEntity";
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
        $("#lists").html('<tr ><td valign="top" colspan="6"  class="center">无符合条件数据！</td></tr>');
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
        row.find("#id").text(item.id);
        row.find("#roleName").text(item.roleName);
        row.find("#gmtCreated").text(getSmpFormatDateByLong(item.gmtCreated, true));
        row.find("#gmtModified").text(getSmpFormatDateByLong(item.gmtModified, true));
        row.find("#isDel").text(item.isDel == 1 ? "已删除" : "未删除");
        row.appendTo("#lists");
    });
    // 绑定分页方法
    paginator("#page", currentPage, pageSize, total, dataUrl);

    //编辑
    $(".edit").on('click', function () {
        showRoleDialog(this);
    });

    //删除
    $(".del").on('click', function () {
        var postData = {};
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

                }
            }
        });
    });
    //角色授权
    $(".config").on('click', function () {
        showMenuDialog(this);
    });


}

// 编辑页面
function showMenuDialog(_this) {
    var dialog = $("#menuDialog");
    dialog.find(":input").val("");
    // 获取选中行数据
    var rowNode = $(_this).closest("tr");
    var roleId = rowNode.find("#id").text();
    var roleName = rowNode.find("#roleName").text();
    dialog.find("input[name='roleId']").val(roleId);
    // 数据填充
    dialog.find("input[name='roleName']").val(roleName);
    // 加载权限
    loadRoleModule(roleId);
    dialog.modal("show");
}


// 编辑页面
function showRoleDialog(_this) {
    var dialog = $("#roleDialog");
    dialog.find(":input").val("");
    dialog.modal("show");
    // 获取选中行数据
    if ($(_this).hasClass("btn-xs")) {
        var rowNode = $(_this).closest("tr");
        var roleId = rowNode.find("#id").text();
        var roleName = rowNode.find("#roleName").text();
        dialog.find("input[name='roleId']").val(roleId);
        // 数据填充
        dialog.find("input[name='roleName']").val(roleName);
        dialog.find("h4").text("编辑");
    } else {
        dialog.find("h4").text("添加");
    }

}


function saveOrUpdateRole() {
    var dialog = $("#roleDialog");
    // 弹窗数据清空
    var postData = {};
    postData.id = dialog.find("input[name='roleId']").val();
    postData.roleName = dialog.find("input[name='roleName']").val();

    $.ajax({
        type: "post",
        url: roleSaveOrUpdateUrl,
        dataType: 'json',
        data: postData,
        async: false,
        success: function (data) {
            if (data != null) {
                dialog.modal('hide');
                loadData(pageCur, pageSize, dataUrl);
            }
        }
    });

}

/**
 * 当前模块的子模块
 */
function selectMySubModule(_this) {
    $(_this).closest('div').find('input:checkbox').prop("checked", _this.checked);
}
/**
 * 选中父级模块
 */
function selectRootModule(_this) {
    $(_this).closest('div').find('#rootMod').prop("checked", _this.checked);
}

function loadRoleModule(roleId) {
    $.ajax({
        type: "post",
        url: roleModuleUrl,
        dataType: 'json',
        data: {
            "roleId": roleId
        },
        async: false,
        success: function (data) {

            if (data.status === 0) {
                $("#menuDialog").find("#modules").html("");
                var html = "";
                data.data.forEach((menu) => {
                    html += '	<div style="padding: 10px;" >';
                    var checkbox = '<input type="checkbox" id="rootMod" value="@moduleId"  @checked  onchange="selectMySubModule(this)"/>@moduleName :';
                    // 根模块
                    html += checkbox.replace("@moduleId", menu.id).replace("@checked",
                        menu.isAuthority == 1 ? 'checked="checked"' : "").replace("@moduleName", menu.name);


                    menu.subMenus.forEach((subMenu) => {
                        var checkbox1 = '<span><input type="checkbox" value="@moduleId"  @checked   onchange="selectRootModule(this)"/>@moduleName </span>';
                        // 根模块
                        html += checkbox1.replace("@moduleId", subMenu.id).replace("@checked",
                            subMenu.isAuthority == 1 ? 'checked="checked"' : "").replace("@moduleName", subMenu.name);

                    });
                    html += '</div>';
                });
                $("#menuDialog").find(".menus").html(html);
            }
        }
    });
}
/**
 * 保存权限
 *
 * @date 2016年3月16日 下午5:24:22
 * @param
 */
function saveOrUpdateAuthority() {
    var dialog = $("#menuDialog");
    // 弹窗数据清空
    var menus = []
    dialog.find('input:checkbox').each(function (i) {
        if ($(this).prop("checked")) {
            var roleId = dialog.find("input[name='roleId']").val();
            menus.push({
                "roleId": roleId,
                "menuId": $(this).val()
            });
        }
    });


    $.ajax({
        type: "post",
        url: saveAuthUrl,
        dataType: 'json',
        data: JSON.stringify(menus),
        async: false,
        contentType: "application/json",
        success: function (data) {
            if (data.status === 0) {
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
