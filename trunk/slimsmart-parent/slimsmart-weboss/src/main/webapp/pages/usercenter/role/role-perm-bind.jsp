	<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div id="usercenter-role-bind-perm" style="margin: 0px 0px 0px 0px;">
	 	<div id="usercenter-role-bind-perm-render" style="width: 96%;">
		</div>
	</div>
	<div id="usercenter-role-bind-perm-progressbar-div" style="margin: 0px 0px 0px 0px;padding-left: 120px;display: none;">
		<div id="usercenter-role-bind-perm-progressbar" style="width:200px;margin-top: 100px;padding-left: 10px;">
			<img src="<%=contextPath%>/images/loading.gif" alt="数据提交中" />
		</div>
		<div style="margin-top: 10px;">数据提交中...</div>
	</div>
</body>
</html>