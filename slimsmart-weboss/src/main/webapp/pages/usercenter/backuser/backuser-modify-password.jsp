<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div style="margin: 10px 10px 10px 10px;">
	<form id="usercenter-backuser-modify-password-form" method="post">
		<input type="hidden" name ="id" />
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">旧密码：</td>
				<td class="tl"><input type="password" name="oldPassword" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/></td>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">新密码：</td>
				<td class="tl"><input type="password" id="usercenter-backuser-password"   name="password" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/></td>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">确认密码：</td>
				<td class="tl"><input type="password"  class="easyui-textbox" data-options="required:true,validType:['length[1,20]','equalTo[\'#usercenter-backuser-password\']']"/></td>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>