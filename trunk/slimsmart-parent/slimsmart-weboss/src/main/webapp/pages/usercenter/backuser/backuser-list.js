/**
 * 后台用户管理
 */
(function() {
	var BackUserList = function() {
		//函数构造，调用初始化
		this.init();
	};
	BackUserList.prototype = {
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
			if(checkBtnShowAuth("usercenter-backUser-add")){
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
					renderId : "usercenter-backuser-grid",
					//查询列表data的url
					url:REQUEST_URL+"/usercenter/backUser/list.do",
					//查询条件的form id
					searchFormId:"usercenter-backuser-list-form",
					//查询按钮Id
					searchBtnId : "usercenter-backuser-list-search-btn",
					//重置按钮Id
					searchResetId : "usercenter-backuser-list-reset-btn",
					//表格高度
					height : $(window).height()-100,
					//定义列
					columns:[[{
						field:'loginName',
						title:'登录名',
						align:"center",
						width:140
					},{
						field:'name',
						title:'姓名',
						align:"center",
						width:120
					},{
						field:'roleName',
						title:'角色名称',
						align:"center",
						width:120
					},{
						field:'status',
						title:'状态',
						align:"center",
						width:100,
						formatter: function(value,row,index){
							 if(value==0){
								 return "正常";
							 }else if(value==1){
								 return "锁定";
							 }
							 else if(value==2){
								 return "注销";
							 }
							 return "";
						}
					},{
						field:'phone',
						title:'联系方式',
						align:"center",
						width:120
					},{
						field:'email',
						title:'电子邮箱',
						align:"center",
						width:180
					},{
						field:'remark',
						title:'备注',
						align:"center",
						width:260
					},{
						field:'operation',
						title:'操作',
						align:"center",
						width:260,
						formatter: function(value,row,index){
							var str ="";
							if(row.id!=getindexBoss().cache.backUser.userId){
								if(checkBtnShowAuth("usercenter-backUser-update")){
									str +="<a href='javascript:backUserList.cache.grid.update(\""+row.id+"\");' >编辑</a> &nbsp;";
								}
								if(checkBtnShowAuth("usercenter-backUser-delete")){
									str +="<a href='javascript:backUserList.cache.grid.del(\""+row.id+"\");' >删除</a>&nbsp;";
								}
								if(checkBtnShowAuth("usercenter-backUser-resetPwd")){
									str +="<a href='javascript:backUserList.resetPassword(\""+row.id+"\");' >重置密码</a>&nbsp;";
								}
								if(checkBtnShowAuth("usercenter-backUser-status")){
									if(row.status == 0){
										str +="<a href='javascript:backUserList.changeStatus(\""+row.id+"\",1);' >锁定</a>&nbsp;";
									}else if(row.status == 1){
										str +="<a href='javascript:backUserList.changeStatus(\""+row.id+"\",0);' >解锁</a>&nbsp;";
									}
									if(row.status != 2){
										str +="<a href='javascript:backUserList.changeStatus(\""+row.id+"\",2);' >注销</a>&nbsp;";
									}
								}
							}
							str +="<a href='javascript:backUserList.cache.grid.detail(\""+row.id+"\");' >详情</a>&nbsp;";
							return str;
						}
					}]],
					renderDialogId : 'usercenter-backuser-add-dialog',
					buttons : [{
						action : "add",
						title: '新增',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-backuser-add-form',
						url : REQUEST_URL+'/pages/usercenter/backuser/backuser-add.jsp',
						addUrl : REQUEST_URL+"/usercenter/backUser/save.do",
						//数据提交前处理事件
						beforeSubmit: function(param){
							//提交之前校验
							var password = $("input[name='password']","#usercenter-backuser-add-form").val();
							var password2 = $("input[name='password2']","#usercenter-backuser-add-form").val();
							if(password!=password2){
								$.messager.error("密码和确认密码不一致");
								return false;
							}
							return true;
						}/*,
						//对话框打开之前
						onBeforeOpen : function(){
						},
						//对话框关闭之前
						onBeforeClose : function(){
						},
						//提交失败处理
						error : function(){
						},
						//提交成功处理
						success : function(data,dialog){
						}*/
						
					},{
						action : "update",
						title: '编辑',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-backuser-add-form',
						url : REQUEST_URL+'/pages/usercenter/backuser/backuser-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/backUser/detail.do",
						updateUrl : REQUEST_URL+"/usercenter/backUser/update.do",
						onLoad : function(id){ //页面加载完成后
							$("tr[name='usercenter-backuser-password-tr']","#usercenter-backuser-add-form").each(function(j){
								 $(this).remove();
							 });
							 $("#loginName","#usercenter-backuser-add-form").textbox('readonly',true);
						}
						/*提交之前
						,
						beforeSubmit : function(param){
						}，
						//对话框打开之前
						onBeforeOpen : function(){
						},
						//数据渲染完毕
						renderComplete : function(){
						},
						//对话框关闭之前
						onBeforeClose : function(){
						},
						//提交失败处理
						error : function(){
						},
						//提交成功处理
						success : function(data,dialog){
						}*/
					},{
						action : "detail",
						title: '详情',
					    width: 600,
					    height: 340,
					    formId : 'usercenter-backuser-add-form',
						url : REQUEST_URL+'/pages/usercenter/backuser/backuser-add.jsp',
						detailUrl : REQUEST_URL+"/usercenter/backUser/detail.do",
						onLoad : function(id){ //页面加载完成后
							$("tr[name='usercenter-backuser-password-tr']","#usercenter-backuser-add-form").each(function(j){
								 $(this).remove();
							 });
							 $("#loginName","#usercenter-backuser-add-form").textbox('readonly',true);
						}
						/*,
						//对话框打开之前
						onBeforeOpen : function(){
						},
						//数据渲染完毕
						renderComplete : function(){
						},
						//对话框关闭之前
						onBeforeClose : function(){
						}*/
					},{
						action : "del",
						delUrl : REQUEST_URL+"/usercenter/backUser/delete.do"
						/*
						 //删除成功
						 success : function(data){
						 },
						 */
					}],
					//指定工具栏
					toolbar :toolbar
			};
			//创建slimgrid对象,放到缓存中
			_thisref.cache.grid = new slimgrid(options);
		},
		resetPassword : function(id){
			$.messager.confirm('提示', '您确认要重置该用户密码吗？', function(r){
				if (r){
					$.ajax({
						url : REQUEST_URL+"/usercenter/backUser/resetPwd.do",
						data:{
							id : id 
						},
						type:'post',    
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	if((typeof data =="string" && data=== "success") || data.code == "0"){
					    		backUserList.cache.grid.reloadData();
								$.messager.success("重置成功，该用户的重置密码为："+data.data);
							}else{
								$.messager.error(data.message || data);
								backUserList.cache.grid.unselectRow(backUserList.cache.grid.getRowIndex(id));
							}
		    			},    
					     error : function() {    
					    	 $.messager.error("操作失败");
					     }    
					});
				}else{
					backUserList.cache.grid.unselectRow(backUserList.cache.grid.getRowIndex(id));
				}
			});
		},
		changeStatus : function(id,status){
			var msg = "";
			if(status == 0){
				msg ="解锁";
			}else if(status ==1){
				msg ="锁定";
			}else if(status ==2){
				msg ="注销";
			}else{
				$.messager.error("您操作有误");
				return ;
			}
			$.messager.confirm('提示', '您确认要'+msg+'该用户吗？', function(r){
				if (r){
					$.ajax({
						url : REQUEST_URL+"/usercenter/backUser/update.do",
						data:{
							id : id ,
							status : status
						},
						type:'post',    
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	if((typeof data =="string" && data=== "success") || data.code == "0"){
					    		backUserList.cache.grid.reloadData();
								$.messager.success(msg+"成功");
							}else{
								$.messager.error(data.message || data);
								backUserList.cache.grid.unselectRow(backUserList.cache.grid.getRowIndex(id));
							}
		    			},    
					     error : function() {    
					    	 $.messager.error("操作失败");
					     }    
					});
				}else{
					backUserList.cache.grid.unselectRow(backUserList.cache.grid.getRowIndex(id));
				}
			});
		}
	};
	$(document).ready(function() {
		//由于整个js类是闭包的方式实例化，外界无法访问，故实例化改对象，将其实体暴露外界，外面可以通过backUserList直接访问。
		 window.backUserList = new BackUserList();
	});
})();
