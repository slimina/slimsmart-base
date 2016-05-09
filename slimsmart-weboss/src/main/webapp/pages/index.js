var indexBoss = {
		version : "1.0.0",
		name : "Boss管理后台",
		cache : {
			menuList : []
		},
		resizeTime : 0,
		intervalSpan : 5000
};

$.extend(true, indexBoss,
{
	render : function(){
		this.resize();
		this.loadMenus();
		this.logout();
		this.modifyPassword();
		$("#main-backuser-tip").text(indexBoss.cache.backUser.userName);
		$("#main-backuser-info-userName").text(indexBoss.cache.backUser.userName);
		$("#main-backuser-info-roleName").text(indexBoss.cache.backUser.roleName);
		$("#main-backuser-info-phone").text(indexBoss.cache.backUser.phone);
		$("#main-backuser-info-email").text(indexBoss.cache.backUser.email);
	},
	resize : function(){
		$('#main-body-div').layout('resize', {
			width : $(window).width(),
			height : $(window).height()-80
		});
	},
	loadMenus : function(){
		$.ajax({
			url : REQUEST_URL+"/menuList.do",
			data:{},
			type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {
		    	if(!data  || !data.length){
		    		$("#mainFrame").attr("src",REQUEST_URL+"/pages/welcome.jsp");
		    		$.messager.error("抱歉，您没有任何权限，请联系管理员");
		    		return;
		    	}
		    	indexBoss.cache.menuList = data;
		    	indexBoss.renderMenus(data);
			},    
		     error : function() {   
		    	 $.messager.error("系统出现异常，请稍后再试");
		     }    
		});
		$.ajax({
			url : REQUEST_URL+"/funcList.do",
			data:{},
			type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {
		    	if(!data  || !data.length){
		    		return;
		    	}
		    	indexBoss.cache.funcList = data;
			},    
		     error : function() {   
		    	 $.messager.error("系统出现异常，请稍后再试");
		     }    
		});
	},
	renderMenus : function(menus){
		var mainMenu = $("#main-nav-ul");
		var leftMenu = $("#main-submenu-div");
		mainMenu.empty();
		$(menus).each(function(i){
			var liItem=$('<li><div><a>'+this.name+'</a></div></li> ');
			liItem.css({"margin-left":"34px"});
			var iconXY = this.iconUrl.split(",");
			liItem.find("div").css({"background-position":(parseInt(iconXY[0]))+"px "+(parseInt(iconXY[1]))+"px"});
			$(liItem).attr('id',this.id);
			if(this.code){
				$(liItem).attr('code',this.code);
			}
			//已经选择菜单样式
			var _selectedCss='main_NavSelected_bg';
			//鼠标移过样式
			var _hoverCss='main_NavHover_bg';
			liItem.hover(function(){
				if(!liItem.hasClass(_selectedCss)){
					liItem.addClass(_hoverCss);
				}
			},function(){
				if(!liItem.hasClass(_selectedCss)){
					liItem.removeClass(_hoverCss);
				}
			});
			mainMenu.append(liItem);
			var _thisref=this;
			//一级菜单点击事件
			liItem.bind('click',function(e,subItem){
				if(liItem.hasClass(_selectedCss)){
					return false;
				}
				//重置已选项 设置父级菜单当前为已选项
				var sel=mainMenu.find('.'+_selectedCss);
				sel.removeClass(_selectedCss);
				liItem.addClass(_selectedCss);
				liItem.removeClass(_hoverCss);
				
				mainMenu.show();
				leftMenu.children("div").hide();
				 $("div[name='"+this.id+"left']").show();
				 $("div[name='"+this.id+"left']").eq(0).trigger("click");
				if(_thisref.subMenuClick){
					_thisref.subMenuClick.call();
				}
				leftMenu.empty();
				if(_thisref.children){
					$(_thisref.children).each(function(j){
						var _this=this;
						var iconXY = this.iconUrl.split(",");
						//二级菜单生成
						var sumItem=$('<div class="main_left_sumitem"><div></div><a class="fl" style="margin-left:8px;">'+this.name+'</a></div><div class="main_splitLine"></div>');
						sumItem.find("div:eq(0)").css({
							"background-position":(parseInt(iconXY[0])-20)+"px "+(parseInt(iconXY[1])+1)+"px"
						}).attr("class","main_left_sumitem_NavIcon");
						
						$(sumItem).attr('id',this.id);
						$(sumItem).attr('name',_thisref.id+"left");
						leftMenu.append(sumItem);
						
						var _subSelectedCss='main_SubNavSelected_bg';
						var _hoverCss='main_SubNavHover_bg';
						sumItem.hover(function(){
							if(sumItem.hasClass(_subSelectedCss)){
								return false;
							 }
							sumItem.addClass(_hoverCss);
							sumItem.find("div:eq(0)").css({
								"background-position":(parseInt(iconXY[0])-100)+"px "+(parseInt(iconXY[1])+1)+"px"
							});
						},function(){
							if(!sumItem.hasClass(_subSelectedCss)){
								sumItem.removeClass(_hoverCss);
								sumItem.find("div:eq(0)").css({
									"background-position":(parseInt(iconXY[0])-20)+"px "+(parseInt(iconXY[1])+1)+"px"
								});
							}
						});
						sumItem.bind('click',function(){
							//重置已选项
							var tmpCurrentSelectedItem=leftMenu.find('.'+_subSelectedCss);
							tmpCurrentSelectedItem.removeClass(_subSelectedCss);
							var xy=tmpCurrentSelectedItem.find("div").css("background-position");
							if(xy){
								tmpCurrentSelectedItem.trigger('mouseover');
								tmpCurrentSelectedItem.trigger('mouseout');
							}else{
								tmpCurrentSelectedItem.find("div:eq(0)").css({
									"background-position":(parseInt(iconXY[0])-20)+"px "+(parseInt(iconXY[1])+1)+"px"
								});
							}
							//设置当前为已选项
							sumItem.addClass(_subSelectedCss);
							sumItem.removeClass(_hoverCss);
							sumItem.find("div:eq(0)").css({
								"background-position":(parseInt(iconXY[0])-100)+"px "+(parseInt(iconXY[1])+1)+"px"
							});
							if(_this.subMenuClick){
								_this.subMenuClick.call();
							}
							// 打开ur
							$("#mainFrame").attr("src",REQUEST_URL+_this.pageUrl);
						});
						if(j==0){
							sumItem.click();
						}
					});
				}
			});
			if(i==0){
				liItem.click();
			}
		});
	},
	modifyPassword : function(){
		$("#main-modifyPassword-btn").bind("click",function(){
			$('#main-dialog').dialog({
			    title: '修改密码',
			    width:300,
			    height:200,
			    cache : false,
			    loadingMessage : '加载中，请稍等',
			    modal:true,
			    href : REQUEST_URL+"/pages/usercenter/backuser/backuser-modify-password.jsp",
			    buttons : [{  
                    text : '提交',  
                    iconCls:'icon-ok',
                    handler : function(){
			    		$('#usercenter-backuser-modify-password-form').form('submit', {
			    			url : REQUEST_URL+"/usercenter/backUser/modifyPassword.do",
			    			onSubmit : function(param){
			    				return $(this).form('validate');
			    			},
			    			success : function(data){
			    				try{
			    					data = $.parseJSON(data);
			    				}catch(e){
			    				}
						    	if(data.code==0){
						    		$('#main-dialog').dialog("close");
									$.messager.success(data.message);
								}else{
									$.messager.error(data.message);
								}
			    			}
			    		});
			    	}  
                }, {  
                    text : '关闭',  
                    iconCls:'icon-cancel',
                    handler : function(){
                    	$('#main-dialog').dialog("close");
                    }  
                }],
			    onLoad : function(){
			    	$('#usercenter-backuser-modify-password-form').form('load',{id : indexBoss.cache.backUser.userId});
			    }
			});
		});
	},
	logout : function(){
		$("#main-logout-btn").bind("click",function(){
			$.messager.confirm('提示', '您确认要退出系统吗？', function(r){
				if (r){
					window.location.href = REQUEST_URL+"/logout";
				}
			});
		});
	}
  }
);
$(document).ready(function() {
	indexBoss.render();
	$('#main-backuser-tip').tooltip({
		hideEvent: 'none',
		content: function(){
			return $("#main-backuser-info");
		},
	    onShow: function(){
	    	var t = $(this);
			t.tooltip('tip').focus().unbind().bind('blur',function(){
				t.tooltip('hide');
			});
	    }
	});
	$(window).resize(function(){
		var d=new Date();
		var cTime=Date.UTC(d.getFullYear(),d.getMonth(),d.getDay(),d.getHours(),d.getMinutes(),d.getSeconds(),d.getMilliseconds());
		if(0==indexBoss.resizeTime){
			indexBoss.resizeTime=cTime;
			indexBoss.resize();
		}
		//防止短时间多次执行
		else if(cTime-indexBoss.resizeTime>1000){
			indexBoss.resize();
		}
	});
});
