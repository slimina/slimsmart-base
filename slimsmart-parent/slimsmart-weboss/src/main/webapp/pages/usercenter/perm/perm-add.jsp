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
	<form id="usercenter-perm-add-form" method="post">
		<input type="hidden" name ="id" />
		<input type="hidden" name ="parentId" />
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">名称：</td>
				<td class="tl">
					<input type="text"  name="name" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
				</td>
				<td class="tr">编码：</td>
				<td class="tl">
					<input type="text" name="code" class="easyui-textbox" data-options="required:true,validType:'length[1,40]'"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">类型：</td>
				<td class="tl">
					<input name="type"  class="easyui-combobox"  data-options="required:true,editable:false,
								  valueField: 'key',textField: 'value',
								  data: [{
										key: '0',
										value: '菜单 '
									},{
										key: '1',
										value: '按钮'
									}]" />
				</td>
				<td class="tr">索引：</td>
				<td class="tl">
					<input type="text" name="orderNum" class="easyui-numberbox" value="0" data-options="required:true,min:0,max:99"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">所属系统：</td>
				<td class="tl"><input type="text" id="usercenter-perm-add-form-systemId" name="systemId" class="easyui-combobox" 
					data-options="required:true,editable:false,
					  		  valueField: 'id',
        					  textField: 'name',
        					  onLoadSuccess : function(){
	        					  var systemId = $('input[name=systemId]','#usercenter-perm-list-form').val();
									if(systemId){
										$('#usercenter-perm-add-form-systemId','#usercenter-perm-add-form').combobox('setValue',systemId);
									}
        					  },
        					  url: '<%=contextPath%>/usercenter/system/findList.do'"/></td>
				</td>
				<td class="tr">所属上级：</td>
				<td class="tl"><input type="text" id="usercenter-perm-add-pid-tree"/></td>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="tr">图标URI：</td>
				<td class="tl">
					<input type="text" name="iconUrl" value="0,0"  class="easyui-textbox" data-options="required:false,validType:'length[1,100]'"/>
				</td>
				<td class="tr">请求URI：</td>
				<td class="tl">
					<input type="text" name="pageUrl"  class="easyui-textbox" data-options="required:false,validType:'length[1,100]'"/>
				</td>
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