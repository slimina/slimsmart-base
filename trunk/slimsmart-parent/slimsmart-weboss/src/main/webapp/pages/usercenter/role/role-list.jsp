<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/pages/usercenter/role/role-list.js"></script>
</head>
<body style="margin-top: 10px;">
	<form id="usercenter-role-list-form">
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">角色名称：</td>
				<td class="tl"><input type="text" name="name" class="easyui-textbox"/></td>
				<td colspan="4">&nbsp;</td>
				 <td class="tc" colspan="2">
					<a href="#" class="easyui-linkbutton" id="usercenter-role-list-search-btn" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					<a href="#" class="easyui-linkbutton" id="usercenter-role-list-reset-btn" data-options="iconCls:'icon-back'" style="width:80px">重置</a>
				 </td>
			</tr>
		</table>
	</form>
	<div id="usercenter-role-grid"></div>
	<div id="usercenter-role-add-dialog"></div>
</body>
</html>