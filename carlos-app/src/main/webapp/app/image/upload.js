// 列表数据请求url
const uploadUrl = rootPath + 'console/image/upload';


function upload(_this) {
    if (validateImage(_this)) {
        var postData = {};
        var fileElementId = _this.id;
        //上传文件
        $.ajaxFileUpload({
            url: uploadUrl,
            secureuri: false,
            fileElementId: fileElementId,
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

