<%@page import="com.slimsmart.common.Constants"%>
<%@page import="com.shq.weboss.common.SessionData"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<%
pageContext.setAttribute("sessionData", (SessionData)session.getAttribute(Constants.BOSS_SESSION_KEY));
%>
<script type="text/javascript" src="<%=contextPath%>/pages/index.js"></script>
<script type="text/javascript">
indexBoss.cache.backUser={
		userId : "${sessionData.userId}",
		userName : "${sessionData.userName}",
		phone : "${sessionData.phone}",
		email : "${sessionData.email}",
		roleId : "${sessionData.roleId}",
		roleName : "${sessionData.roleName}"
};
</script>
</head>
<body>
<div class="main_headerBg" id="main-header-div">
				<div class="main_header">
					<div class="main_topInfo" style="width: 60px;padding-left: 20px;">
						<img src="<%=contextPath%>/images/logo.png" width="100" height="80" title="运营后台"/>
					</div>
					<ul class="mainNav fl ml1" id="main-nav-ul">
					</ul>
					<div
						style="float: right; margin-top: 5px; padding-right: 45px; color: #fff;">
						欢迎：<a href="javascript:void(0);" id ="main-backuser-tip"></a>  | <a href="javascript:void(0);" id="main-modifyPassword-btn">修改密码</a> | <a href="javascript:void(0);" id="main-logout-btn">安全退出</a>
					</div>
				</div>
			</div>
	<div style="display:none;">
		<div id="main-backuser-info" title="您的用户信息" class="easyui-panel" style="width:160px;height:140px;padding:10px;background:#fafafa;">
			<table>
				<tr>
					<td class="tr">用户姓名：</td>
					<td class="tl" id="main-backuser-info-userName"></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="tr">角色名称：</td>
					<td class="tl" id="main-backuser-info-roleName"></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="tr">联系方式：</td>
					<td class="tl" id="main-backuser-info-phone"></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td class="tr">电子邮箱：</td>
					<td class="tl" id="main-backuser-info-email"></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="main-body-div" class="easyui-layout">
		<div data-options="region:'west'"
			style="width: 160px; height: 80%; display: block;"
			class="main_centerLeft none" id="main-center-left-div">
			<div class="leftTop tl">
				<span class="leftTopIcon"></span>应用菜单
			</div>
			<div class="main_splitLine"></div>
			<div id="main-submenu-div"></div>
		</div>
		<div data-options="region:'center'" style="height: 80%; width: 100%;">
			<iframe id="mainFrame" name="mainFrame" class="main-container-div"
				src="" style="height: 96%; width: 96%;"></iframe>
		</div>
		<div data-options="region:'south'"
			style="height: 30px; width: 100%; padding-top: 8px; background-color: #F5F5F5">
			<%@ include file="/pages/common/footer.jsp"%>
		</div>
	</div>
	<div id="main-dialog"></div>
</body>
</html>