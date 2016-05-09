<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=contextPath%>/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=contextPath%>/plugins/toast/toast.js"></script>
<script type="text/javascript">
var REQUEST_URL="<%=contextPath%>";
var COOKIE_NAME = 'USERNAME'; 

if(this.parent!=this){
	top.location.href = REQUEST_URL+"/login";
}
 
function login_submit(){
	var loginName = $.trim($("#loginName").val());
	if(!loginName){
		Toast.fail("登录名不能为空");
		$("#loginName").focus();
		return;
	}
	var password = $.trim($("#password").val());
	if(!password){
		Toast.fail("密码不能为空");
		$("#password").focus();
		return;
	}
	var kaptchaCode = $.trim($("#kaptchaCode").val());
	if(!kaptchaCode){
		Toast.fail("验证码不能为空");
		$("#kaptchaCode").focus();
		return;
	}
	$.ajax({
		url : REQUEST_URL+"/loginAuth",
		data:{
			loginName : loginName ,
			password : password ,
			kaptchaCode : kaptchaCode
		},
		type:'post',    
	    cache:false,    
	    dataType:'json',    
	    success:function(data) {
	    	if(data && data.code == "0"){
	    		Toast.success(data.message);
				location.href = REQUEST_URL+"/index";
			}else{
				 $("#kaptchaCode").val('');
				 $("#kaptchaCode-img").click();
				if(!data){
					 Toast.fail("登录失败，请联系管理员");
				}else{
					Toast.fail(data.message);
				}
			}
		},    
	     error : function() {    
	    	 $("#kaptchaCode").val('');
	    	 $("#kaptchaCode-img").click();
	    	 Toast.fail("登录失败，请联系管理员");
	     }    
	});
};
$(document).ready(function () {
	$("#rememberMe").click(function(){  
		   if(this.checked){  
			   var date = new Date();
			   date.setTime(date.getTime() + (30*24*60*60*1000)); 
		     $.cookie(COOKIE_NAME, $("#loginName").val() , { path: '/', expires:date});  
		   }else{  
		     $.cookie(COOKIE_NAME, null, { path: '/' });
		   }  
	});
	$("#loginBtn").click(function(){
		login_submit();
	});
	/**
	 * 回车键提交
	 */
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$("#loginBtn").click();
		}
	};
	 if($.cookie(COOKIE_NAME)){  
		   $("#loginName").val($.cookie(COOKIE_NAME));  
		   $("#rememberMe").attr("checked","checked");
	}
});
</script>
</head>
<body>
	<div class="Main">
		<ul>
			<li class="top"></li>
			<li class="top2"></li>
			<li class="topA"></li>
			<li class="topB"><span> 
			</span></li>
			<li class="topC"></li>
			<li class="topD">
				<ul class="login">
					<li><span class="left">用户名：</span> <span style=""> <input
							id="loginName" name="loginName" value="" placeholder="请输入您的登陆名" type="text"  class="txt" />

					</span></li>
					<li><span class="left">密 码：</span> <span style=""> <input
							id="password" value="" type="password" placeholder="请输入您的密码" name="password" class="txt" />
					</span></li>
					<li>
						<table >
							<tr>
								<td><span class="left">验证码：</span></td>
								<td><span><input id="kaptchaCode"  name="kaptchaCode" value="" type="text" class="txtCode" placeholder="请输入验证码"/></span></td>
								<td><img src="<%=contextPath%>/kaptcha.jpg"  width="110" id="kaptchaCode-img" height="30" style="cursor: pointer;" onclick="this.src='<%=contextPath%>/kaptcha.jpg?t='+Math.random();" title="点击我，换一张" /></td>
							</tr>
						</table>
					</li>
					<li><span class="left">记住我：</span> <input id="rememberMe"
						type="checkbox"  value="1" /></li>
				</ul>
			</li>
			<li class="topE"></li>
			<li class="middle_A"></li>
			<li class="middle_B"></li>
			<li class="middle_C"><span class="btn"> 
			<img id="loginBtn" style="cursor: pointer;" src="<%=contextPath%>/images/login/btnlogin.gif" />
			</span></li>
			<li class="middle_D"></li>
			<li class="bottom_A"></li>
			<li class="bottom_B">
				<p>1.请您避免在网吧、咖啡馆等公共场所或使用公用免费WIFI登录</p>
				<p>2.为了保证您正常使用运营系统，推荐您使用IE8.0以上版本的浏览器</p>
			</li>
		</ul>
	</div>
</body>
</html>
