<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String contextPath = request.getContextPath();
String msg = "";
String errorInfo = "";
if(exception!=null){
	msg = exception.getMessage();
	StringWriter sw = new StringWriter();  
	PrintWriter pw = new PrintWriter(sw);  
	exception.printStackTrace(pw);
	errorInfo  = sw.toString();
}
String[] ss = errorInfo.split("\\n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>BOSS管理后台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon"
	href="<%=contextPath%>/images/favicon.ico" />
<script type="text/javascript">
console.log("异常信息:"+"<%=msg%>");
var errorInfo =[];
<%for(String s : ss){s = s.trim();%>
 errorInfo.push(" <%=s%>");
<%}%>
	console.log(errorInfo.join("\n"));
</script>
<script type="text/javascript">
	if (this.parent != this) {
		top.location.href = "<%=contextPath%>/error/500.jsp";
	}
</script>
</head>
<body>
	<div style="text-align: center;">
		<h2>不好意思啊，网站出现错误了！</h2>
	</div>
	<br />
	<a href="<%=contextPath%>" style="font-size: 14px; margin: 0px 40px;">点击我返回首页</a>
</body>
</html>