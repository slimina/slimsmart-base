/**
 * 系统管理
 */
(function() {
	var SystemList = function() {
		//函数构造，调用初始化
		this.init();
	};
	SystemList.prototype = {
		cache : {
			//缓存数据
			grid : null
		},
		init : function() {
			this.render();
			this.loadGrid();
		},
		render : function(){
			//渲染页面样式
		},
		loadGrid : function(){ //表格定义
			var _thisref = this;
			var toolbar = [];
			//检查权限
			if(checkBtnShowAuth("usercenter-system-add")){
				toolbar.push({
					text:"新增",
					iconCls: 'icon-add',
					handler: function(){
						_thisref.cache.grid.add();
					}
				});
			}
			//定义表格属性
			var options = {
					//表格渲染的div id
					renderId : "usercenter-system-grid",
					//查询列表data的url
					url:REQUEST_URL+"/usercenter/system/list.do",
					//查询条件的form id
					searchFormId:"usercenter-system-list-form",
					//查询按钮Id
					searchBtnId : "usercenter-system-list-search-btn",
					//重置按钮Id
					searchResetId : "usercenter-system-list-reset-btn",
					//表格高度
					height : $(window).height()-80,
					//定义列
					columns:[[{
						field:'name',
						title:'名称',
						align:"center",
						width:160
					},{
						field:'code',
						title:'编码',
						align:"center",
						width:160
					},{
						field:'status',
						title:'状态',
						align:"center",
						width:100,
						formatter: function(value,row,index){
							 if(value==0){
								 return "正常";
							 }else if(value==1){
								 return "维护中";
							 }
							 else if(value==2){
								 return "关闭";
							 }
							 return "";
						}
					},{
						field:'url',
						title:'访问地址',
						align:"center",
						width:160,
						formatter: function(value,row,index){
							 if(value){
								 return "<a target='_blank' href='"+value+"'>"+value+"</a>";
							 }
							 return "";
						}
					},{
						field:'remark',
						title:'备注',
						align:"center",
						width:300
					},{
						field:'operation',
						title:'操作',
						align:"center",
						width:160,
						formatter: function(value,row,index){
							var str ="";
							if(checkBtnShowAuth("usercenter-system-update")){
								str +="<a href='javascript:systemList.cache.grid.update(\""+row.id+"\");' >编辑</a> &nbsp;";
							}
							if(checkBtnShowAuth("usercenter-system-delete")){
								str +="<a href='javascript:systemList.cache.grid.del(\""+row.id+"\",\"删除该系统,对应的权限也会删除，您确认要删除吗？\");' >删除</a>&nbsp;";
							}
							str +="<a href='javascript:systemList.cache.grid.detail(\""+row.id+"\");' >详情</a>&nbsp;";
							return str;
						}
					}]],
					renderDialogId : 'usercenter-system-add-dialog',
					buttons : [{
						action : "add",
						title: '新增',
					    width: 600,
					    height: 240,
					    formId : 'usercenter-system-add-form',
						url : REQUEST_URL+'/pages/usercenter/system/system-add.jsp',
						addUrl : REQUEST_URL+"/usercenter/system/save.do"
					},{
						action : "update",
						title: '编辑',
					    width: 600,
					    height: 240,
					    formId : 'usercenter-system-add-form',
						url : REQUEST_URL+'/pages/usercenter/system/system-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/system/detail.do",
						updateUrl : REQUEST_URL+"/usercenter/system/update.do",
						onLoad : function(id){ //页面加载完成后
							$("#usercenter-system-add-form-code","#usercenter-system-add-form").textbox('readonly',true);
						}
					},{
						action : "detail",
						title: '详情',
					    width: 600,
					    height: 240,
					    formId : 'usercenter-system-add-form',
						url : REQUEST_URL+'/pages/usercenter/system/system-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/system/detail.do"
					},{
						action : "del",
						delUrl : REQUEST_URL+"/usercenter/system/delete.do"
					}],
					//指定工具栏
					toolbar :toolbar
			};
			//创建slimgrid对象,放到缓存中
			_thisref.cache.grid = new slimgrid(options);
		}
	};
	$(document).ready(function() {
		//由于整个js类是闭包的方式实例化，外界无法访问，故实例化改对象，将其实体暴露外界，外面可以通过backUserList直接访问。
		 window.systemList = new SystemList();
	});
})();
