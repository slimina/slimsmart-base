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
	<form id="usercenter-backuser-add-form" method="post">
		<input type="hidden" name ="id" />
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">登录名：</td>
				<td class="tl">
					<input type="text" id="loginName" name="loginName" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
				<td class="tr">姓名：</td>
				<td class="tl">
					<input type="text" name="name" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
			</tr>
			<tr name="usercenter-backuser-password-tr">
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr name="usercenter-backuser-password-tr">
				<td class="tr">密码：</td>
				<td class="tl">
					<input type="password" name="password" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
				<td class="tr">确认密码：</td>
				<td class="tl">
					<input type="password" name="password2" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">所属角色：</td>
				<td class="tl"><input type="text" name="roleId" class="easyui-combobox" 
					data-options="required:true,editable:false,
					  		  valueField: 'id',
        					  textField: 'name',
        					  url: '<%=contextPath%>/usercenter/role/findList.do'"/></td>
				</td>
				<td class="tr">联系电话：</td>
				<td class="tl">
					<input type="text" name="phone" class="easyui-textbox" data-options="required:true,validType:['telphone','length[1,20]']"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">电子邮箱：</td>
				<td class="tl">
					<input type="text" name="email"   class="easyui-textbox" data-options="required:false,validType:['email','length[1,40]']"/>
				</td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">备注：</td>
				<td class="tl" colspan="3">
					<textarea  rows="5" cols="57" name="remark"  data-options="required:false,validType:'length[1,200]'"></textarea>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>