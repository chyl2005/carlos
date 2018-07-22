/**
 * *********  操作实例  **************
 *   var map = new HashMap();
 *   map.put("key1","Value1");
 *   map.put("key2","Value2");
 *   map.put("key3","Value3");
 *   map.put("key4","Value4");
 *   map.put("key5","Value5");
 *   alert("size："+map.size()+" key1："+map.get("key1"));
 *   map.remove("key1");
 *   map.put("key3","newValue");
 *   var values = map.values();
 *   for(var i in values){
*       document.write(i+"："+values[i]+"   ");
*   }
 *   document.write("<br>");
 *   var keySet = map.keySet();
 *   for(var i in keySet){
*       document.write(i+"："+keySet[i]+"  ");
*   }
 *   alert(map.isEmpty());
 */
function HashMap() {
    //定义长度
    var length = 0;
    //创建一个对象
    var obj = new Object();

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function () {
        return length == 0;
    };

    /**
     * 判断对象中是否包含给定Key
     */
    this.containsKey = function (key) {
        return (key in obj);
    };

    /**
     * 判断对象中是否包含给定的Value
     */
    this.containsValue = function (value) {
        for (var key in obj) {
            if (obj[key] == value) {
                return true;
            }
        }
        return false;
    };

    /**
     *向map中添加数据
     */
    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            length++;
        }
        obj[key] = value;
    };

    /**
     * 根据给定的Key获得Value
     */
    this.get = function (key) {
        return this.containsKey(key) ? obj[key] : null;
    };

    /**
     * 根据给定的Key删除一个值
     */
    this.remove = function (key) {
        if (this.containsKey(key) && (delete obj[key])) {
            length--;
        }
    };

    /**
     * 获得Map中的所有Value
     */
    this.values = function () {
        var _values = new Array();
        for (var key in obj) {
            _values.push(obj[key]);
        }
        return _values;
    };

    /**
     * 获得Map中的所有Key
     */
    this.keySet = function () {
        var _keys = new Array();
        for (var key in obj) {
            _keys.push(key);
        }
        return _keys;
    };

    /**
     * 获得Map的长度
     */
    this.size = function () {
        return length;
    };

    /**
     * 清空Map
     */
    this.clear = function () {
        length = 0;
        obj = new Object();
    };
}

//扩展Date的format方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
/**
 *转换日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == true || isFull == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    } else {
        pattern = "yyyy-MM-dd";
    }
    return getFormatDate(date, pattern);
}

/**
 *转换当前日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatNowDate(isFull) {
    return getSmpFormatDate(new Date(), isFull);
}

/**
 *转换long值为日期字符串
 * @param l long值
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */

function getSmpFormatDateByLong(l, isFull) {
    return getSmpFormatDate(new Date(l), isFull);
}

/**
 *转换long值为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */

function getFormatDateByLong(l, pattern) {
    return getFormatDate(new Date(l), pattern);
}

/**
 *转换日期对象为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    return date.format(pattern);
}

