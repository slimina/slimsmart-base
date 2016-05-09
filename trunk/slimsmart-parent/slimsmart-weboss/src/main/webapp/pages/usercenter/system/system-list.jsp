<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/pages/usercenter/system/system-list.js"></script>
</head>
<body style="margin-top: 10px;">
	<form id="usercenter-system-list-form">
		<table style="width: 100%; margin-bottom: 20px;">
			<tr>
				<td class="tr">系统名称：</td>
				<td class="tl"><input type="text" name="name" class="easyui-textbox"/></td>
				<td class="tr">系统状态：</td>
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
										value: '维护中'
									},{
										key: '2',
										value: '关闭'
									}]" />
				</td>
				<td colspan="2">&nbsp;</td>
				 <td class="tc" colspan="2">
					<a href="#" class="easyui-linkbutton" id="usercenter-system-list-search-btn" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
					<a href="#" class="easyui-linkbutton" id="usercenter-system-list-reset-btn" data-options="iconCls:'icon-back'" style="width:80px">重置</a>
				 </td>
			</tr>
		</table>
	</form>
	<div id="usercenter-system-grid"></div>
	<div id="usercenter-system-add-dialog"></div>
</body>
</html>