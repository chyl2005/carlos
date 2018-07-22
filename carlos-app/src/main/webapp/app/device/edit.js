var settingUrl = rootPath + "deviceSetting/setting";

var uploadSettingUrl = rootPath + "deviceSetting/uploadSetting";

var saveOrUpdateUrl = rootPath + "deviceSetting/saveOrUpdate";
$(document).ready(function () {

    var deviceId = $("#deviceId").val();
    if (!isBlank(deviceId)) {
        getSetting(deviceId);
        getUploadSetting(deviceId);
    }

    $(".searchData").on('click', function () {
        var did = $("#deviceId").val();
        if (!isInteger(did)) {
            alert("请输入正确设备ID");
        }
        getSetting(did);
        getUploadSetting(did);
    });

});


function getSetting(deviceId) {

    $.ajax({
        type: "get",
        url: settingUrl,
        dataType: 'json',
        data: {deviceId: deviceId},
        async: false,
        success: function (data) {
            if (data.status === 0) {
                var item = data.data;
                $(".setting").find("input[name='serverIp']").val(item.serverIp);
                $(".setting").find("input[name='serverPort']").val(item.serverPort);
                $(".setting").find("input[name='serialNum']").val(item.serialNum);
                $(".setting").find("input[name='deviceNum']").val(item.deviceNum);
                $(".setting").find("input[name='phoneNum1']").val(item.phoneNum1);
                $(".setting").find("input[name='phoneNum2']").val(item.phoneNum2);
                $(".setting").find("input[name='sensorDepth']").val(item.sensorDepth);
                $(".setting").find("input[name='surfaceHigh']").val(item.surfaceHigh);
                $(".setting").find("input[name='uploadTime']").val(item.uploadTime);
                $(".setting").find("input[name='wakeInterval']").val(item.wakeInterval);
                $(".setting").find("input[name='gmtModified']").val(getSmpFormatDateByLong(item.gmtModified, true));
            } else {
                alert(data.message);
            }
        }
    });
}


function getUploadSetting(deviceId) {
    $.ajax({
        type: "get",
        url: uploadSettingUrl,
        dataType: 'json',
        data: {deviceId: deviceId},
        async: false,
        success: function (data) {
            if (data.status === 0) {
                var item = data.data;
                $(".uploadSetting").find("input[name='serverIp']").val(item.serverIp);
                $(".uploadSetting").find("input[name='serverPort']").val(item.serverPort);
                $(".uploadSetting").find("input[name='serialNum']").val(item.serialNum);
                $(".uploadSetting").find("input[name='deviceNum']").val(item.deviceNum);
                $(".uploadSetting").find("input[name='phoneNum1']").val(item.phoneNum1);
                $(".uploadSetting").find("input[name='phoneNum2']").val(item.phoneNum2);
                $(".uploadSetting").find("input[name='sensorDepth']").val(item.sensorDepth);
                $(".uploadSetting").find("input[name='surfaceHigh']").val(item.surfaceHigh);
                $(".uploadSetting").find("input[name='uploadTime']").val(item.uploadTime);
                $(".uploadSetting").find("input[name='wakeInterval']").val(item.wakeInterval);
                $(".uploadSetting").find("input[name='gmtModified']").val(getSmpFormatDateByLong(item.gmtModified, true));

            } else {
                alert(data.message);
            }
        }
    });

}


function saveOrUpdate() {
    // 弹窗数据清空
    var postData = {};
    postData.deviceId =  $("#deviceId").val();
    postData.serverIp = $(".setting").find("input[name='serverIp']").val();
    postData.serverPort = $(".setting").find("input[name='serverPort']").val();
    postData.serialNum = $(".setting").find("input[name='serialNum']").val();
    postData.phoneNum1 = $(".setting").find("input[name='phoneNum1']").val();
    postData.phoneNum2 = $(".setting").find("input[name='phoneNum2']").val();
    postData.sensorDepth = $(".setting").find("input[name='sensorDepth']").val();
    postData.surfaceHigh = $(".setting").find("input[name='surfaceHigh']").val();
    postData.uploadTime = $(".setting").find("input[name='uploadTime']").val();
    postData.wakeInterval = $(".setting").find("input[name='wakeInterval']").val();

    $.ajax({
        type: "post",
        url: saveOrUpdateUrl,
        dataType: 'json',
        data: postData,
        async: false,
        success: function (data) {
            if (data.status === 0) {
                getSetting($("#deviceId").val());
            } else {
                alert(data.message);
            }
        }
    });

}
