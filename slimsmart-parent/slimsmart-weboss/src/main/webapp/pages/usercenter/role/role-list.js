/**
 * 角色管理
 */
(function() {
	var RoleList = function() {
		//函数构造，调用初始化
		this.init();
	};
	RoleList.prototype = {
		cache : {
			//缓存数据
			grid : null,
			systemData : null
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
			if(checkBtnShowAuth("usercenter-role-add")){
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
					renderId : "usercenter-role-grid",
					//查询列表data的url
					url:REQUEST_URL+"/usercenter/role/list.do",
					//查询条件的form id
					searchFormId:"usercenter-role-list-form",
					//查询按钮Id
					searchBtnId : "usercenter-role-list-search-btn",
					//重置按钮Id
					searchResetId : "usercenter-role-list-reset-btn",
					//表格高度
					height : $(window).height()-80,
					//定义列
					columns:[[{
						field:'name',
						title:'名称',
						align:"center",
						width:160
					},{
						field:'remark',
						title:'备注',
						align:"center",
						width:400
					},{
						field:'operation',
						title:'操作',
						align:"center",
						width:260,
						formatter: function(value,row,index){
							var str ="";
							if(checkBtnShowAuth("usercenter-role-update")){
								str +="<a href='javascript:roleList.cache.grid.update(\""+row.id+"\");' >编辑</a> &nbsp;";
							}
							if(checkBtnShowAuth("usercenter-role-delete")){
								str +="<a href='javascript:roleList.cache.grid.del(\""+row.id+"\");' >删除</a>&nbsp;";
							}
							if(checkBtnShowAuth("usercenter-role-perm-bind")){
								str +="<a href='javascript:roleList.bind(\""+row.id+"\");' >绑定权限</a>&nbsp;";
							}
							str +="<a href='javascript:roleList.cache.grid.detail(\""+row.id+"\");' >详情</a>&nbsp;";
							return str;
						}
					}]],
					renderDialogId : 'usercenter-role-add-dialog',
					buttons : [{
						action : "add",
						title: '新增',
					    width: 400,
					    height: 200,
					    formId : 'usercenter-role-add-form',
						url : REQUEST_URL+'/pages/usercenter/role/role-add.jsp',
						addUrl : REQUEST_URL+"/usercenter/role/save.do"
					},{
						action : "update",
						title: '编辑',
					    width: 400,
					    height: 200,
					    formId : 'usercenter-role-add-form',
						url : REQUEST_URL+'/pages/usercenter/role/role-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/role/detail.do",
						updateUrl : REQUEST_URL+"/usercenter/role/update.do"
					},{
						action : "detail",
						title: '详情',
					    width: 400,
					    height: 200,
					    formId : 'usercenter-role-add-form',
						url : REQUEST_URL+'/pages/usercenter/role/role-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/role/detail.do"
					},{
						action : "del",
						delUrl : REQUEST_URL+"/usercenter/role/delete.do"
					}],
					//指定工具栏
					toolbar :toolbar
			};
			//创建slimgrid对象,放到缓存中
			_thisref.cache.grid = new slimgrid(options);
		},
		bind : function(roleId){
			var _thisref = this;
			$('#usercenter-role-add-dialog').dialog({
			    title: '绑定权限',
			    width: 460,
			    height: 400,
			    href: REQUEST_URL+"/pages/usercenter/role/role-perm-bind.jsp",
			    loadingMessage : '加载中，请稍等',
			    modal:true,
			    collapsible : false,
			    minimizable :false,
			    maximizable : false,
			    resizable : false,
			    buttons : [{
			    	text : '保存',  
                    iconCls:'icon-ok',
                    handler : function(){
                    	var permIds = [];
                    	$(_thisref.cache.systemData).each(function(i){
                    		var nodes = $("#"+this.id).tree('getChecked', ['checked','indeterminate']);
                    		$(nodes).each(function(j){
                    			permIds.push(this.id);
                    		});
                    	});
                    	if(permIds.length == 0){
                    		 $.messager.error("请选择权限");
                    		return;
                    	}else{
                    		$("#usercenter-role-bind-perm").hide();
                    		$("#usercenter-role-bind-perm-progressbar-div").show();
                    		$.ajax({
        						url : REQUEST_URL+"/usercenter/role/bind.do",
        						data:{
        							roleId : roleId ,
        							permIds : permIds.join(",")
        						},
        						type:'post',    
        					    cache:false,    
        					    dataType:'json',    
        					    success:function(data) {
        					    	if(data.code == "0"){
        								$.messager.success(data.message);
        								$('#usercenter-role-add-dialog').dialog("close");
        							}else{
        								$.messager.error(data.message);
        								$("#usercenter-role-bind-perm-progressbar-div").hide();
        								$("#usercenter-role-bind-perm").show();
        							}
        		    			},    
        					     error : function() {    
        					    	 $.messager.error("操作失败");
        					    	 $("#usercenter-role-bind-perm-progressbar-div").hide();
        					    	 $("#usercenter-role-bind-perm").show();
        					     }    
        					});
                    	}
                    }
			    },{
                    text : '关闭',  
                    iconCls:'icon-cancel',
                    handler : function(){
                    	$('#usercenter-role-add-dialog').dialog("close");
                    }  
                }],
			    onLoad : function(){
			    	$("#usercenter-role-bind-perm-render").tabs({
			    		border:false,
			    		 onAdd : function(title,index){
			    			 var id = _thisref.cache.systemData[index].id;
			    			 $("#"+id).tree({
			    				 url: REQUEST_URL+"/usercenter/role/findPermTree.do?systemId="+id+"&roleId="+roleId,
			    				 lines : true,
			    				 animate : true,
			    				 checkbox : true,
			    				 onLoadSuccess : function(node,data){
						    			if(data || data.length){
						    				function checkSelected(data){
						    					$(data).each(function(i){
							    					if(!this.checked){
							    						var node = $("#"+id).tree('find', this.id);
							    						$("#"+id).tree("uncheck",node.target);
							    					}
							    					if(this.children && this.children.length){
							    						checkSelected(this.children);
							    					}
							    				});
						    				}
						    				checkSelected(data);
						    			}
						    		}
			    			 });
			    		 }
			    	});
			    	$.ajax({
						url : REQUEST_URL+"/usercenter/system/findList.do",
						type:'post',    
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	if(data && data.length){
					    		_thisref.cache.systemData = data;
					    		$(data).each(function(i){
					    			var o = this;
					    			$("#usercenter-role-bind-perm-render").tabs('add',{
					    				title : o.name,
					    				content : "<div id='"+o.id+"' style='margin: 10px 10px 10px 100px;'></div>",
					    				closable:false
					    			});
					    		});
					    		$("#usercenter-role-bind-perm-render").tabs("select",0);
							}else{
								$.messager.error("操作失败");
								$('#usercenter-role-add-dialog').dialog("close");
							}
		    			},    
					     error : function() {    
					    	 $.messager.error("操作失败");
					    	 $('#usercenter-role-add-dialog').dialog("close");
					     }    
					});
			    }
			});
			
		}
	};
	$(document).ready(function() {
		//由于整个js类是闭包的方式实例化，外界无法访问，故实例化改对象，将其实体暴露外界，外面可以通过backUserList直接访问。
		 window.roleList = new RoleList();
	});
})();
