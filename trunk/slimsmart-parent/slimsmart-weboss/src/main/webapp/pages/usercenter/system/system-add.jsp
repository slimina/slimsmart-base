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
	<form id="usercenter-system-add-form" method="post">
		<input type="hidden" name ="id" />
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">名称：</td>
				<td class="tl">
					<input type="text" name="name"  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
				<td class="tr">状态：</td>
				<td class="tl">
					<input name="status"  class="easyui-combobox"  data-options="required:true,editable:false,
								  valueField: 'key',textField: 'value',
								  data: [{
										key: '0',
										value: '正常'
									},{
										key: '1',
										value: '维护中'
									},{
										key: '2',
										value: '关闭'
									}]" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">系统编码：</td>
				<td class="tl">
					<input type="text" name="code" id="usercenter-system-add-form-code"  class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
				<td class="tr">访问地址：</td>
				<td class="tl">
					<input type="text" name="url"  class="easyui-textbox" data-options="required:false,validType:'length[1,100]'"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">备注：</td>
				<td class="tl">
					<textarea  rows="3" cols="30" name="remark"  data-options="required:false,validType:'length[1,200]'"></textarea>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>