String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
// 正整数正则表达式(包括0)
var positiveIntegerPattern = /^\d+$/;
var emailPattern = /^("(?:\\[ -~]|[ !#-\[\]-~])+"|[!#-'*+\-\/\d=?A-Z^_`a-z{|}~]+(?:\.[!#-'*+\-\/\d=?A-Z^_`a-z{|}~]+)*)@((?:[\dA-Za-z](?:[-\dA-Za-z]*[\dA-Za-z])?\.)+[A-Za-z][-A-Za-z]*[A-Za-z])$/;
var mobilePattern = /^1[3|4|5|7|8][0-9]\d{4,8}$/;

var piRegExp = new RegExp(positiveIntegerPattern);
var emailRegExp = new RegExp(emailPattern);
var mobileRegExp = new RegExp(mobilePattern);

//判断字符串是否为null或者""
function isBlank(str){
	if(str == null || str.trim().length == 0){
		return true;
	} else {
		return false;
	}
}

// 判断字符串是否正整数
function isPositiveInteger(str){
	if(piRegExp.test(str)){
		return true;
	}
	
	return false;
}

// 判断字符串是否是邮件地址
function isEmail(str){
	if(emailRegExp.test(str)){
		return true;
	}
	
	return false;
}

function isMobile(str){
	if(mobileRegExp.test(str)){
		return true;
	}
	
	return false;
}

//数字或者小数
function isNumberOrDecimal( s ){
    var regu = /^\d+\.?\d{0,}$/;
    if (regu.test(s)) {
        return true;
    } else {
        return false;
    }
}


/* 
用途：检查输入对象的值是否符合整数格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false

*/
function isInteger( str ){
   var regu = /^[-]{0,1}[0-9]{1,}$/;
   return regu.test(str);
}