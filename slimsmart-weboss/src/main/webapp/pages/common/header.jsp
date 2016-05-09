<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>运营管理后台</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- 		<link rel="shortcut icon" type="image/x-icon" href="<%=contextPath%>/images/favicon.ico" /> --%>
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/plugins/easyui/themes/bootstrap/easyui.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/plugins/easyui/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/common.css" />
		<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/datagrid-detailview.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/custom/slimgrid.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/custom/validator.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/toast/toast.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/plugins/custom/common.js"></script>
		<script type="text/javascript">
			var REQUEST_URL="<%=contextPath%>";
		</script>
    

