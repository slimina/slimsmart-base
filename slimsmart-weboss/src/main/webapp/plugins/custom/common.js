Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
								: "/u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};
function formateDate(val){
	return new Date(val).pattern("yyyy-MM-dd HH:mm:ss");
}

function formateDateNoTime(val){
	return new Date(val).pattern("yyyy-MM-dd");
}

/**
 * 针对panel window dialog三个组件拖动时会超出父级元素的修正
 * 如果父级元素的overflow属性为hidden，则修复上下左右个方向
 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
 * @param left
 * @param top
 * @returns
 */
var easyuiPanelOnMove = function(left, top) {
	var parentObj = $(this).panel('panel').parent();
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
	var width = $(this).panel('options').width;
	var height = $(this).panel('options').height;
	var parentWidth = parentObj.width();
	var parentHeight = parentObj.height();
	//if(parentObj.css("overflow")=="hidden"){
		if(left > parentWidth-width){
			$(this).window('move', {
				"left":parentWidth-width
			});
		}
		if(top > parentHeight-height){
			$(this).window('move', {
				"top":parentHeight-height
			});
		}
	//}
};
//$.fn.panel.defaults.onMove = easyuiPanelOnMove;
//$.fn.window.defaults.onMove = easyuiPanelOnMove;
//$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

//获取当前登陆用户ID
function getindexBoss(){
    if(parent.indexBossss){
		return parent.indexBoss;
	}else if(parent.parent.indexBoss){
		return parent.parent.indexBoss;
	}else if(parent.parent.parent.indexBoss){
		return parent.parent.parent.indexBoss;
	}else if(parent.parent.parent.parent.indexBoss){
		return parent.parent.parent.parent.indexBoss;
	}else if(parent.parent.parent.parent.parent.indexBoss){
		return parent.parent.parent.parent.parent.indexBoss;
	}else{
		return null;
	}
};

/**
 * 检查权限标识是否需要显示
 * @param id
 * @returns {Boolean}
 */
var indexBoss = indexBoss || null;
function checkBtnShowAuth(id){
	return true;
	if(!id){
		return false;
	}
	if(!indexBoss){
		indexBoss = getindexBoss();
	}
	if(!indexBoss || !indexBoss.cache || !indexBoss.cache.funcList || !indexBoss.cache.funcList.length){
		return false;
	}
	var reval = false;
	for(var i = 0,l=indexBoss.cache.funcList.length;i<l;i++){
		if(indexBoss.cache.funcList[i].code ==id){
			reval = true;
			break;
		}
	}
	return reval;
};

/**
 * 图片放大
 * @param imgesOb
 * @param titlese
 * @param divid
 */
function openImgesDiv(imgesOb,titlese,divid){
	var renderDiv = null;
	if(!divid){
		renderDiv = $(document.body);
	}else{
		renderDiv = $("#"+divid);
	}
	var imgesUrlPath = imgesOb.src;
	renderDiv.append("<div class='none'  onclick='closeImgesDivWindow();' id='tranDiv'><div id='tranDivBack'> </div> <div align='center' id='infoDiv'></div> </div>");
	$("#infoDiv").html(""+"<img src='"+imgesUrlPath+"'  title='"+titlese+"'/>");
	$("#infoDiv").attr("style","position:absolute;z-Index:2347483583;width:100%;height:100%;");
	$("#tranDivBack").attr("style","position:absolute;left:0px; top:0px; width:100%; height:100%;background-color:#E4E4E4;filter:alpha(Opacity=30);z-Index:2247483583;");
	$("#tranDiv").attr("style","position:absolute; left:0px;top:0px;overflow: scroll;height:"+document.body.clientHeight+ "px;width:"+document.body.clientWidth+ "px;display:none;margin:0 auto;text-align:center;z-Index:2247483583;");
	$("#tranDiv").fadeIn("slow");
}
function closeImgesDivWindow(){
	$("#tranDiv").fadeOut("slow");
}

function strToJson(str){
	var json = (new Function("return " + str))();
	return json;
};

/**
 * 转换成金额显示 ###,##0.00
 * @param num
 * @returns {String}
 */
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
/**
 * 解决浏览器不同的四舍五入的BUG，采用MATH方法。
 * @param len
 * @returns {Number}
 */
//修改 toFixed BUG
Number.prototype.toFixed = function(d) {
	var s = this + "";
	if (!d)
		d = 0;
	if (s.indexOf(".") == -1)
		s += ".";
	s += new Array(d + 1).join("0");
	if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
		var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true;
		if (a == d + 2) {
			a = s.match(/\d/g);
			if (parseInt(a[a.length - 1]) > 4) {
				for ( var i = a.length - 2; i >= 0; i--) {
					a[i] = parseInt(a[i]) + 1;
					if (a[i] == 10) {
						a[i] = 0;
						b = i != 1;
					} else
						break;
				}
			}
			s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"),
					"$1.$2");
		}
		if (b)
			s = s.substr(1);
		return (pm + s).replace(/\.$/, "");
	}
	return this + "";
};

//计算字符串字节的长度
function byteRangeLength(str){
	if(!str){
		return 0;
	}
	var length = str.length;
	for ( var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) > 127) {
			length++;
		}
	}
	return length;
}

var REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g;
var REGX_HTML_DECODE = /&\w+;|&#(\d+);/g;

/**
 * html encode
 * @param s
 * @returns
 */
function encodeHtml(s){
    return (typeof s != "string") ? s :
        s.replace(REGX_HTML_ENCODE,
                  function($0){
                      var c = $0.charCodeAt(0), r = ["&#"];
                      c = (c == 0x20) ? 0xA0 : c;
                      r.push(c); r.push(";");
                      return r.join("");
    });
};
/**
 * html decode
 * @param s
 * @returns
 */
function decodeHtml(s){
    var HTML_DECODE = REGX_HTML_DECODE;
    s = (s != undefined) ? s : "";
    return (typeof s != "string") ? s :
        s.replace(REGX_HTML_DECODE,
                  function($0, $1){
                      var c = HTML_DECODE[$0];
                      if(c == undefined){
                          // Maybe is Entity Number
                          if(!isNaN($1)){
                              c = String.fromCharCode(($1 == 160) ? 32:$1);
                          }else{
                              c = $0;
                          }
                      }
                      return c;
                  });
};

/**
 * 页面全局 ajax 请求完成处理
 */
$(document).ajaxComplete(function(event, request, settings) {
	if('error'==request.statusText && request.status!=0){
		alert('系统服务暂不可用，请联系系统管理员！');
	 }
	request=null;
});

if (typeof console === "undefined" || typeof console.log === "undefined") {
    console = {};
    console.log = function () {
    };
}
var logger = console;
