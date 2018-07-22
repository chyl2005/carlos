// 列表数据请求url
var dataUrl = rootPath + "deviceRecord/list";

$(document).ready(function () {


    Highcharts.setOptions({global: {useUTC: false}});
    // 加载数据
    loadData(dataUrl);
    $(".searchData").on('click', function () {
        loadData(dataUrl);
    });

});


// 获取请求服务器数据
function getServerParams() {
    var postData = {};

    postData.deviceId = $("#deviceId").val();
    postData.startTime = $("#startTime").val();
    postData.endTime = $("#endTime").val();
    return postData;
}

function download() {
    var postData = getServerParams();
    var url = rootPath + "deviceRecord/download?" + $.param(postData);
    window.location.href = url;

}

// 数据加载
function loadData(url) {
    var postData = getServerParams();
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


    var formats = '%Y-%m-%d';
    var interval = 3600 * 1000;


    var title = {
        text: '设备指标'
    };
    var subtitle = {
        text: ''
    };
    var xAxis = {
        type: 'datetime',
        tickPixelInterval: 120,
        title: {
            text: '时间'
        },
        dateTimeLabelFormats: {
            millisecond: '%m-%d %H:%M',
            second: '%m-%d %H:%M',
            minute: '%m-%d %H:%M',
            hour: '%m-%d %H:%M',
            day: '%m-%d %H:%M',
            month: '%m-%d %H:%M',
            year: '%m-%d %H:%M'
        }
    }


    var legend = {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 0
    };

    var waterHigh = [];
    var sensorDepth = [];
    var waterDepth = [];
    var surfaceHigh = [];


    var airTemperature = [];
    var waterTemperature = [];
    //电压
    var voltage = [];
    //信号
    var signal = [];

    var items = data.data;
    items.forEach((item) => {

        waterHigh.push({
            x: item.collectTime,
            y: item.waterHigh
        });
        waterDepth.push({
            x: item.collectTime,
            y: item.waterDepth
        });
        sensorDepth.push({
            x: item.collectTime,
            y: item.sensorDepth
        });
        surfaceHigh.push({
            x: item.collectTime,
            y: item.surfaceHigh
        });

        airTemperature.push({
            x: item.collectTime,
            y: item.airTemperature
        });
        waterTemperature.push({
            x: item.collectTime,
            y: item.waterTemperature
        });
        voltage.push({
            x: item.collectTime,
            y: item.voltage
        });
        signal.push({
            x: item.collectTime,
            y: item.signal
        });


    });

    //数值后缀
    var valueSuffixMap = new HashMap();
    valueSuffixMap.put("地面高程", "米");
    valueSuffixMap.put("水位标高", "米");
    valueSuffixMap.put("水位埋深", "米");
    valueSuffixMap.put("传感器埋深", "米");
    valueSuffixMap.put("气温", '\xB0C');
    valueSuffixMap.put("水温", '\xB0C');
    valueSuffixMap.put("电压", "V");
    valueSuffixMap.put("信号", "");

    var tooltip = {
        formatter: function () {
            var showText = '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y) + valueSuffixMap.get(this.series.name);
            return showText;
        }
    }


    //图表1
    var series1 = [];
    series1.push({
        name: '水面高程',
        data: waterHigh
    });
    series1.push({
        name: '水深',
        data: waterDepth
    });
    series1.push({
        name: '传感器埋深',
        data: sensorDepth
    });
    series1.push({
        name: '地表高程',
        data: surfaceHigh
    });

    var yAxis1 = {
        title: {
            text: '(米)'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    };

    var charts1 = {};
    charts1.type = 'spline';
    charts1.title = title;
    charts1.subtitle = subtitle;
    charts1.xAxis = xAxis;
    charts1.yAxis = yAxis1;
    charts1.tooltip = tooltip;
    charts1.legend = legend;
    charts1.series = series1;
    charts1.credits = {
        enabled: false
    };


    //图表2
    var series2 = [];
    series2.push({
        name: '气温',
        data: airTemperature
    });
    series2.push({
        name: '水温',
        data: waterTemperature
    });
    series2.push({
        name: '电压',
        data: voltage,
        yAxis: 1
    });
    series2.push({
        name: '信号',
        data: signal,
        yAxis: 1
    });


    var yAxis2 = [
        {
            title: {
                text: 'Temperature (\xB0C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        {
            title: {
                text: '数值',
                style: {
                    color: '#00AA00'
                }
            },
            opposite: true
        }

    ];


    var charts2 = {};
    charts2.type = 'spline';
    charts2.title = title;
    charts2.subtitle = subtitle;
    charts2.xAxis = xAxis;
    charts2.yAxis = yAxis2;
    charts2.tooltip = tooltip;
    charts2.legend = legend;
    charts2.series = series2;
    charts2.credits = {
        enabled: false
    };

    $('#charts1').highcharts(charts1);

    $('#charts2').highcharts(charts2);


}
