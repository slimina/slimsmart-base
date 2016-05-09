<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<style>
<!--
.tree-node{
	padding: 2px;
}
-->
</style>

<script type="text/javascript" src="<%=contextPath%>/pages/usercenter/perm/perm-list.js"></script>
</head>
<body style="margin-top: 10px;">
	<form id="usercenter-perm-list-form">
		<input type="hidden" name="systemId"/>
		<table style="width: 100%; margin-bottom: 20px;margin-left: 100px;">
			<tr>
				<td class="tr">名称：</td>
				<td class="tl"><input type="text" name="name" class="easyui-textbox"/></td>
				<td class="tr">状态：</td>
				<td class="tl">
					<input name="status"  class="easyui-combobox"  data-options="required:false,editable:false,
								  valueField: 'key',textField: 'value',
								  data: [{
								  		key: '',
										value: '全部'
									},{
										key: '0',
										value: '启用'
									},{
										key: '1',
										value: '禁用'
									}]" />
				</td>
				<td class="tc" colspan="2">
					<a href="#" class="easyui-linkbutton" id="usercenter-perm-list-search-btn" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					<a href="#" class="easyui-linkbutton" id="usercenter-perm-list-reset-btn" data-options="iconCls:'icon-back'" style="width:80px">重置</a>
				 </td>
				 <td colspan="4">&nbsp;</td>
			</tr>
		</table>
	</form>
	<div style="width: 100%">
		<ul id="usercenter-system-tree" style="width: 140px;float: left;"></ul>
		<div id="usercenter-perm-grid" style="width: 100%;float: right;"></div>
	</div>
	<div id="usercenter-perm-add-dialog"></div>
</body>
</html>