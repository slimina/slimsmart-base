/**
 * 系统参数管理
 */
(function() {
	var ParamsList = function() {
		//函数构造，调用初始化
		this.init();
	};
	ParamsList.prototype = {
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
			if(checkBtnShowAuth("usercenter-params-add")){
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
					renderId : "usercenter-params-grid",
					//查询列表data的url
					url:REQUEST_URL+"/usercenter/params/list.do",
					//查询条件的form id
					searchFormId:"usercenter-params-list-form",
					//查询按钮Id
					searchBtnId : "usercenter-params-list-search-btn",
					//重置按钮Id
					searchResetId : "usercenter-params-list-reset-btn",
					//表格高度
					height : $(window).height()-60,
					//定义列
					columns:[[{
						field:'key',
						title:'参数编码',
						align:"center",
						width:160
					},{
						field:'value',
						title:'参数值',
						align:"center",
						width:240
					},{
						field:'desc',
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
							if(checkBtnShowAuth("usercenter-params-update")){
								str +="<a href='javascript:paramsList.cache.grid.update(\""+row.id+"\");' >编辑</a> &nbsp;";
							}
							if(checkBtnShowAuth("usercenter-params-delete")){
								str +="<a href='javascript:paramsList.cache.grid.del(\""+row.id+"\");' >删除</a>&nbsp;";
							}
							str +="<a href='javascript:paramsList.cache.grid.detail(\""+row.id+"\");' >详情</a>&nbsp;";
							return str;
						}
					}]],
					renderDialogId : 'usercenter-params-add-dialog',
					buttons : [{
						action : "add",
						title: '新增',
					    width: 480,
					    height: 280,
					    formId : 'usercenter-params-add-form',
						url : REQUEST_URL+'/pages/usercenter/params/params-add.jsp',
						addUrl : REQUEST_URL+"/usercenter/params/save.do"
					},{
						action : "update",
						title: '编辑',
						width: 480,
						height: 280,
					    formId : 'usercenter-params-add-form',
						url : REQUEST_URL+'/pages/usercenter/params/params-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/params/detail.do",
						updateUrl : REQUEST_URL+"/usercenter/params/update.do"
					},{
						action : "detail",
						title: '详情',
					    width: 480,
					    height: 280,
					    formId : 'usercenter-params-add-form',
						url : REQUEST_URL+'/pages/usercenter/params/params-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/params/detail.do"
					},{
						action : "del",
						delUrl : REQUEST_URL+"/usercenter/params/delete.do"
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
		 window.paramsList = new ParamsList();
	});
})();
