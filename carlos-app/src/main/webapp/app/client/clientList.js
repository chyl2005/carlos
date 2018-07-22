var getClientsUrl = rootPath + "client/clients";
var getCommondsUrl = rootPath + "client/commonds";
var sendUrl = rootPath + "client/send";


$(document).ready(function () {

    loadClients();
    loadCommonds()

});


function loadClients() {
    $.ajax({
        url: getClientsUrl,
        data: {},
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.status === 0) {
                $('#clientlist').html("");
                var html = "";
                var items = data.data;
                items.forEach((item) => {
                    var address = item.address;
                    var clientInfo = item.clientInfo;
                    html += '<option value="' + address + '">' + clientInfo + '</option>';
                });

                $('#clientlist').html(html);
            } else {
                alert(data.message);
            }

        }
    });
}


function loadCommonds() {
    $.ajax({
        url: getCommondsUrl,
        data: {},
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.status === 0) {
                $('#command').html("");
                var html = '  <option value="">无</option>';
                var items = data.data;
                items.forEach((item) => {
                    var code = item.code;
                    var commond = item.commond;
                    html += '<option value="' + code + '">' + commond + '</option>';
                });

                $('#command').html(html);
            } else {
                alert(data.message);
            }

        }
    });
}


function sendMsg() {
    var postData = {};
    postData.address = $('#clientlist').val();
    postData.command = $('#command').val();
    postData.message = $('#message').val();

    $.ajax({
        type: "post",
        url: sendUrl,
        dataType: 'json',
        data: postData,
        async: false,
        success: function (data) {
            if (data.status === 0) {
                console.log("成功")
            } else {
                console.log(data.message);
                alert(data.message);
            }
        }
    });
}

