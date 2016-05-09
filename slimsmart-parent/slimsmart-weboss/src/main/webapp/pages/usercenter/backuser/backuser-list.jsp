<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/pages/usercenter/backuser/backuser-list.js"></script>
</head>
<body style="margin-top: 10px;">
	<form id="usercenter-backuser-list-form">
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">登录名：</td>
				<td class="tl"><input type="text" name="loginName" class="easyui-textbox"/></td>
				<td class="tr">姓名：</td>
				<td class="tl"><input type="text" name="name" class="easyui-textbox"/></td>
				<td class="tr">联系方式：</td>
				<td class="tl"><input type="text" name="phone" class="easyui-textbox"/></td>
				<td class="tr">用户状态：</td>
				<td class="tl">
					<input name="status"  class="easyui-combobox"  data-options="required:false,editable:false,
								  valueField: 'key',textField: 'value',
								  data: [{
								  		key: '',
										value: '全部'
									},{
										key: '0',
										value: '正常'
									},{
										key: '1',
										value: '锁定'
									},{
										key: '2',
										value: '注销'
									}]" />
				</td>
			</tr>
			<tr>
				<td colspan="8">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6">&nbsp;</td>
				<td class="tc" colspan="2">
					<a href="#" class="easyui-linkbutton" id="usercenter-backuser-list-search-btn" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					<a href="#" class="easyui-linkbutton" id="usercenter-backuser-list-reset-btn" data-options="iconCls:'icon-back'" style="width:80px">重置</a>
				 </td>
			</tr>
		</table>
	</form>
	<div id="usercenter-backuser-grid"></div>
	<div id="usercenter-backuser-add-dialog"></div>
</body>
</html>