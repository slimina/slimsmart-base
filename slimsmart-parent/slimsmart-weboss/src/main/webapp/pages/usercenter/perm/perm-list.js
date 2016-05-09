/**
 * 资源权限管理
 */
(function() {
	var PermList = function() {
		//函数构造，调用初始化
		this.init();
	};
	PermList.prototype = {
		cache : {
			//缓存数据
			grid : null,
			defaultSystemId : null
		},
		init : function() {
			this.render();
			this.loadGrid();
		},
		render : function(){
			var _thisref = this;
			$("input[name='systemId']","#usercenter-perm-list-form").val(_thisref.cache.defaultSystemId);
			//渲染页面样式
			$('#usercenter-system-tree').tree({
			    url: REQUEST_URL+"/usercenter/system/findList.do",
			    lines : true,
			    animate : true,
			    loadFilter: function(data){
			    	var systemArr = [];
			    	if(data){
			    		$(data).each(function(i){
			    			if(i==0){
			    				_thisref.cache.defaultSystemId = this.id;
			    			}
			    			systemArr.push({text:this.name,id : this.id});
			    		});
			    	}
			         var treedata = [];
			         treedata.push({text:"所属系统",id : "",children : systemArr});
			    	return treedata;
			    },
			    onLoadSuccess: function(){
			    	var node = $('#usercenter-system-tree').tree('find', _thisref.cache.defaultSystemId);
					$('#usercenter-system-tree').tree('select', node.target);
			    },
			    onSelect : function(node){
			    	$("input[name='systemId']","#usercenter-perm-list-form").val(node.id);
			    	_thisref.cache.grid.search();
			    }
			});
			
		},
		loadGrid : function(){ //表格定义
			var _thisref = this;
			var toolbar = [];
			//检查权限
			if(checkBtnShowAuth("usercenter-perm-add")){
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
					renderId : "usercenter-perm-grid",
					//查询列表data的url
					url:REQUEST_URL+"/usercenter/perm/findPermTree.do",
					//查询条件的form id
					searchFormId:"usercenter-perm-list-form",
					//查询按钮Id
					searchBtnId : "usercenter-perm-list-search-btn",
					//重置按钮Id
					searchResetId : "usercenter-perm-list-reset-btn",
					//表格高度
					height : $(window).height()-80,
					//定义列
					columns:[[{
						field:'name',
						title:'名称',
						align:"center",
						width:260
					},{
						field:'code',
						title:'编码',
						align:"center",
						width:120
					},{
						field:'type',
						title:'类型',
						align:"center",
						width:120,
						formatter: function(value,row,index){
							 if(value==0){
								 return "菜单";
							 }else if(value==1){
								 return "按钮";
							 }
							 return "";
						}
					},{
						field:'status',
						title:'状态',
						align:"center",
						width:100,
						formatter: function(value,row,index){
							 if(value==0){
								 return "启用";
							 }else if(value==1){
								 return "禁用";
							 }
							 return "";
						}
					},{
						field:'orderNum',
						title:'排序序列',
						align:"center",
						width:120
					},{
						field:'operation',
						title:'操作',
						align:"center",
						width:260,
						formatter: function(value,row,index){
							var str ="";
							if(checkBtnShowAuth("usercenter-perm-update")){
								str +="<a href='javascript:permList.cache.grid.update(\""+row.id+"\");' >编辑</a> &nbsp;";
							}
							if(checkBtnShowAuth("usercenter-perm-delete")){
								str +="<a href='javascript:permList.cache.grid.del(\""+row.id+"\");' >删除</a>&nbsp;";
							}
							if(checkBtnShowAuth("usercenter-perm-status")){
								if(row.status == 0){
									str +="<a href='javascript:permList.changeStatus(\""+row.id+"\",1);' >禁用</a>&nbsp;";
								}else if(row.status == 1){
									str +="<a href='javascript:permList.changeStatus(\""+row.id+"\",0);' >启用</a>&nbsp;";
								}
							}
							str +="<a href='javascript:permList.cache.grid.detail(\""+row.id+"\");' >详情</a>&nbsp;";
							return str;
						}
					}]],
					renderDialogId : 'usercenter-perm-add-dialog',
					treeField : "name",
					pagination : false,
					buttons : [{
						action : "add",
						title: '新增',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-perm-add-form',
						url : REQUEST_URL+'/pages/usercenter/perm/perm-add.jsp',
						addUrl : REQUEST_URL+"/usercenter/perm/save.do",
						//数据提交前处理事件
						beforeSubmit: function(param){
							return true;
						},
						onLoad : function(){
							var systemId = $("input[name='systemId']","#usercenter-perm-list-form").val();
							$("#usercenter-perm-add-pid-tree").combotree({
								url:REQUEST_URL+'/usercenter/perm/menuList.do?systemId='+systemId,
								required:false,
								editable:false,
								onChange : function(newValue, oldValue){
									if(newValue!=oldValue){
										$("#usercenter-perm-add-form").find("input[name='parentId']").val(newValue);
									}
								},
							});
						}
					},{
						action : "update",
						title: '编辑',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-perm-add-form',
						url : REQUEST_URL+'/pages/usercenter/perm/perm-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/perm/detail.do",
						updateUrl : REQUEST_URL+"/usercenter/perm/update.do",
						renderComplete : function(data){
							$("#usercenter-perm-add-form").find("input[name='parentId']").val(data.parentId);
							$("#usercenter-perm-add-pid-tree").combotree({
								url:REQUEST_URL+'/usercenter/perm/menuList.do?systemId='+data.systemId,
								required:false,
								editable:false,
								onChange : function(newValue, oldValue){
									if(newValue!=oldValue){
										$("#usercenter-perm-add-form").find("input[name='parentId']").val(newValue);
									}
								},
								onLoadSuccess:function(){
									if(data.parentId && data.parentId!=''){
										$("#usercenter-perm-add-pid-tree").combotree('setValue',data.parentId);
									} else {
										$("#usercenter-perm-add-pid-tree").combotree('setValue','');
									}
								}
							});
							$('#usercenter-perm-add-form-systemId','#usercenter-perm-add-form').combobox('readonly', true);
						}
					},{
						action : "detail",
						title: '详情',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-perm-add-form',
						url : REQUEST_URL+'/pages/usercenter/perm/perm-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/perm/detail.do",
						renderComplete : function(data){
							$("#usercenter-perm-add-form").find("input[name='parentId']").val(data.parentId);
							$("#usercenter-perm-add-pid-tree").combotree({
								url:REQUEST_URL+'/usercenter/perm/menuList.do?systemId='+data.systemId,
								required:false,
								editable:false,
								onChange : function(newValue, oldValue){
									if(newValue!=oldValue){
										$("#usercenter-perm-add-form").find("input[name='parentId']").val(newValue);
									}
								},
								onLoadSuccess:function(){
									if(data.parentId && data.parentId!=''){
										$("#usercenter-perm-add-pid-tree").combotree('setValue',data.parentId);
									} else {
										$("#usercenter-perm-add-pid-tree").combotree('setValue','');
									}
								}
							});
						}
					},{
						action : "del",
						delUrl : REQUEST_URL+"/usercenter/perm/delete.do"
					}],
					//指定工具栏
					toolbar :toolbar
			};
			//创建slimgrid对象,放到缓存中
			_thisref.cache.grid = new slimgrid(options);
		},
		changeStatus : function(id,status){
			var msg = "";
			if(status == 0){
				msg ="启用";
			}else if(status ==1){
				msg ="禁用";
			}else{
				$.messager.error("您操作有误");
				return ;
			}
			$.messager.confirm('提示', '您确认要'+msg+'该权限吗？', function(r){
				if (r){
					$.ajax({
						url : REQUEST_URL+"/usercenter/perm/update.do",
						data:{
							id : id ,
							status : status
						},
						type:'post',    
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	if((typeof data =="string" && data=== "success") || data.code == "0"){
					    		permList.cache.grid.reloadData();
								$.messager.success(msg+"成功");
							}else{
								$.messager.error(data.message || data);
								permList.cache.grid.unselectRow(permList.cache.grid.getRowIndex(id));
							}
		    			},    
					     error : function() {    
					    	 $.messager.error("操作失败");
					     }    
					});
				}else{
					permList.cache.grid.unselectRow(permList.cache.grid.getRowIndex(id));
				}
			});
		}
	};
	$(document).ready(function() {
		//由于整个js类是闭包的方式实例化，外界无法访问，故实例化改对象，将其实体暴露外界，外面可以通过backUserList直接访问。
		 window.permList = new PermList();
	});
})();
