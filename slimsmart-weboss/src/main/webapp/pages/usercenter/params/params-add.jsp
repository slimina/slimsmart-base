	<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div style="margin: 10px 10px 10px 10px;">
	<form id="usercenter-params-add-form" method="post">
		<input type="hidden" name ="id" />
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">参数编码：</td>
				<td class="tl" colspan="3">
					<input type="text" name="key" style="width: 300px;"  class="easyui-textbox" data-options="required:true,validType:'length[1,40]'"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">参数值：</td>
				<td class="tl" colspan="3">
					<input type="text" name="value" style="width: 300px;height: 60px;" class="easyui-textbox" data-options="required:false,multiline:true,validType:'length[1,1000]'"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">备注：</td>
				<td class="tl" colspan="3">
					<textarea  rows="3" cols="40" name="desc"  data-options="required:false,validType:'length[1,200]'"></textarea>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>