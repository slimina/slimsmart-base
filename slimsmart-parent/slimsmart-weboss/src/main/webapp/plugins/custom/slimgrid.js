//http://www.jeasyui.net/plugins/183.html
var slimgrid = function(o)
{
	this.version = "1.0.0";
	this.options = {};
	this._init(o);
};
slimgrid.prototype = {
	_init : function(o){
		if (o)
		{
			this.options = o;
			this._loading();
		}else{
			alert("加载数据失败");
		}
	},
	// 加载数据
	_loading : function(){
		var obj = this;
		var options = {
				idField : "id",
				method : "post",
				fitColumns : false,
				toolbar : obj.options.toolbar || [],
				columns : obj.options.columns || [],
				frozenColumns : obj.options.frozenColumns || null,
				rownumbers :  (obj.options.rownumbers === false ? false : (obj.options.rownumbers || true)),
				width :  obj.options.width || 'auto',
				height :  obj.options.height || 'auto',
				nowrap : true,
				url: obj.options.url || null,
				loadMsg: "数据加载中，请稍等...",
				singleSelect : (obj.options.singleSelect === false ? false : (obj.options.singleSelect || true)),
				checkOnSelect: (obj.options.checkOnSelect === false ? false : (obj.options.checkOnSelect || true)),
				selectOnCheck : (obj.options.selectOnCheck === false ? false : (obj.options.selectOnCheck || true)),
				rowStyler : obj.options.rowStyler || null,
				data : obj.options.data || null,
				pagination :  (obj.options.pagination === false ? false : (obj.options.pagination || true)),
				pageNumber : obj.options.pageNumber || 1,
				pageSize : obj.options.pageSize || 20,
				pageList : obj.options.pageList || [10,15,20,25,30,40,50,100],
				loadFilter : obj.options.loadFilter || function(data){
					return data;
				},
				//定义树字段
				treeField : obj.options.treeField || null,
				lines : obj.options.lines || false,
				//加载数据成功
				onLoadSuccess : obj.options.onLoadSuccess || function(){},
				//加载数据失败
				onLoadError : obj.options.onLoadError || function(){
					$.messager.error("数据加载失败，请稍后再试");
				},
				//param
				onBeforeLoad : obj.options.onBeforeLoad || function(){},
				//rowIndex, rowData
				onClickRow : obj.options.onClickRow ||  function(){},
				//rowIndex, rowData
				onDblClickRow :  obj.options.onDblClickRow ||  function(){},
				//rowIndex, field, value
				onClickCell :  obj.options.onClickCell || function(){return false;},
				//rowIndex, field, value
				onDblClickCell:  obj.options.onDblClickCell ||  function(){return false;},
				//rowIndex, rowData
				onSelect:obj.options.onSelect ||  function(){},
				//rowIndex, rowData
				onUnselect:obj.options.onUnselect ||  function(){},
				//rows
				onSelectAll:obj.options.onSelectAll ||  function(){},
				//rows
				onUnselectAll:obj.options.onUnselectAll ||  function(){},
				//rowIndex,rowData
				onCheck : obj.options.onCheck ||  function(){},
				//rowIndex,rowData
				onUncheck : obj.options.onUncheck ||  function(){},
				//rows
				onCheckAll :obj.options.onCheckAll ||  function(){},
				//rows
				onUncheckAll:obj.options.onUncheckAll ||  function(){},
				//row
				onBeforeExpand : obj.options.onBeforeExpand || function(){},
				onExpand : obj.options.onExpand || function(){},
				onBeforeCollapse : obj.options.onBeforeCollapse || function(){},
				onCollapse : obj.options.onCollapse || function(){},
				onContextMenu : obj.options.onContextMenu || function(){}
		};
		if(obj.options.view){
			 $.extend(options,{
				//子表
				view : obj.options.view,
				//function(index,row)
				detailFormatter : obj.options.detailFormatter,
				onExpandRow : obj.options.onExpandRow
			 });
		}
		if(!obj.options.treeField){
			obj.realGrid =  $("#" + obj.options.renderId).datagrid(options);
		}else{
			obj.realGrid =  $("#" + obj.options.renderId).treegrid(options);
		}
		$('#' + obj.options.searchBtnId).bind("click",function(){
			obj.search();
		});
		$('#' + obj.options.searchResetId).bind("click",function(){
			$('#'+obj.options.searchFormId).form("reset");
			if(obj.options.searchFormReset){
				obj.options.searchFormReset();
			}
		});
	},
	search : function(){
		var obj = this;
		var param = {};
		if (obj.options.parms){
			if( typeof obj.options.parms == "function"){
				param = obj.options.parms();
			}else{
				param = obj.options.parms;
			}
		}
		if(obj.options.otherParams){
			$.extend(true,param,obj.options.otherParams);
		}
		var data = $('#' + obj.options.searchFormId).serializeArray();
		for ( var i = 0; i < data.length; i++)
		{
			var datai = data[i];
			if (datai.value&&""!=datai.value)
			{
				var d = $.parseJSON('{"'+datai.name+'":"'+$.trim(datai.value)+'"}');;
				$.extend(true,param,d);
			}
		}
		if(!obj.options.treeField){
			$("#" + obj.options.renderId).datagrid('reload',param); 
		}else{
			$("#" + obj.options.renderId).treegrid('reload',param); 
		}
	},
	/**
	 * 设置其他的参数，可以动态设置
	 * @param data
	 */
	setOtherParams : function(data){
		var obj = this;
		if (!obj.options.otherParams)
		{
			obj.options.otherParams = {};
		}
		for ( var i in data)
		{
			obj.options.otherParams[i] = data[i];
		}
	},
	resize : function(param){
		if(!this.options.treeField){
			this.realGrid.datagrid('resize',param); 
		}else{
			this.realGrid.treegrid('resize',param); 
		}
	},
	/**
	 * 刷新列表数据
	 */
	reloadData:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('load'); 
		}else{
			this.realGrid.treegrid('load'); 
		}
	},
	loadData:function(data){
		if(!this.options.treeField){
			this.realGrid.datagrid('loadData',data); 
		}else{
			this.realGrid.treegrid('loadData',data); 
		}
	},
	getFooterRows:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getFooterRows'); 
		}else{
			return this.realGrid.treegrid('getFooterRows'); 
		}
	},
	/**
	 * 获取选中的行
	 */
	getCheckedRows:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getCheckedRows'); 
		}
		return [];
	},
	getData:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getData'); 
		}else{
			return this.realGrid.treegrid('getData'); 
		}
	},
	getRows:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getRows'); 
		}
		return [];
	},
	getRowIndex:function(row){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getRowIndex',row); 
		}
		return null;
	},
	getChecked:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getChecked'); 
		}
		return [];
	},
	getSelected:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getSelected'); 
		}else{
			return this.realGrid.treegrid('getSelected'); 
		}
	},
	getSelections:function(){
		if(!this.options.treeField){
			return this.realGrid.datagrid('getSelections'); 
		}else{
			return this.realGrid.treegrid('getSelections'); 
		}
	},
	getGrid : function(){
		return this.realGrid;
	},
	find:function(id){
		if(this.options.treeField){
			return this.realGrid.treegrid('find',id); 
		}
		return null;
	},
	clearSelections:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('clearSelections'); 
		}
	},
	clearChecked:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('clearChecked'); 
		}
	},
	selectAll:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('selectAll'); 
		}else{
			this.realGrid.treegrid('selectAll'); 
		}
	},
	unselectAll:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('unselectAll'); 
		}else{
			this.realGrid.treegrid('unselectAll'); 
		}
	},
	selectRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('selectRow',index); 
		}
	},
	selectRecord:function(idValue){
		if(!this.options.treeField){
			this.realGrid.datagrid('selectRecord',idValue); 
		}else{
			this.realGrid.treegrid('select',idValue); 
		}
	},
	unselect:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('unselect',id); 
		}
	},
	unselectRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('unselectRow',index); 
		}
	},
	checkAll:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('checkAll'); 
		}
	},
	uncheckAll:function(){
		if(!this.options.treeField){
			this.realGrid.datagrid('uncheckAll'); 
		}
	},
	checkRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('checkRow',index); 
		}
	},
	uncheckRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('uncheckRow',index); 
		}
	},
	appendRow:function(row){
		if(!this.options.treeField){
			this.realGrid.datagrid('appendRow',row); 
		}
	},
	refreshRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('refreshRow',index); 
		}
	},
	updateRow:function(param){
		if(!this.options.treeField){
			this.realGrid.datagrid('updateRow',param); 
		}
	},
	insertRow:function(param ){
		//index：插入进去的行的索引，如果没有定义，就追加该新行。
		//row：行的数据。
		if(!this.options.treeField){
			this.realGrid.datagrid('insertRow',param); 
		}
	},
	deleteRow:function(index){
		if(!this.options.treeField){
			this.realGrid.datagrid('deleteRow',index); 
		}
	},
	mergeCells:function(options){
		/*把一些单元格合并为一个单元格，options 参数包括下列特性：
		index：列的索引。
		field：字段名。
		rowspan：合并跨越的行数。
		colspan：合并跨越的列数。*/
		if(!this.options.treeField){
			this.realGrid.datagrid('mergeCells',options); 
		}
	},
	showColumn:function(field){
		if(!this.options.treeField){
			this.realGrid.datagrid('showColumn',field); 
		}
	},
	hideColumn:function(field){
		if(!this.options.treeField){
			this.realGrid.datagrid('hideColumn',field); 
		}
	},
	getRoot:function(){
		if(this.options.treeField){
			return this.realGrid.treegrid('getRoot'); 
		}
		return null;
	},
	getRoots:function(){
		if(this.options.treeField){
			return this.realGrid.treegrid('getRoots'); 
		}
		return null;
	},
	getParent:function(){
		if(this.options.treeField){
			return this.realGrid.treegrid('getParent'); 
		}
		return null;
	},
	getChildren:function(){
		if(this.options.treeField){
			return this.realGrid.treegrid('getChildren'); 
		}
		return null;
	},
	getLevel:function(id){
		if(this.options.treeField){
			return this.realGrid.treegrid('getLevel',id); 
		}
		return null;
	},
	collapse:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('collapse',id); 
		}
	},
	expand:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('expand',id); 
		}
	},
	collapseAll:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('collapseAll',id); 
		}
	},
	expandAll:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('expandAll',id); 
		}
	},
	expandTo:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('expandTo',id); 
		}
	},
	toggle:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('toggle',id); 
		}
	},
	append:function(param){
		if(this.options.treeField){
			this.realGrid.treegrid('append',param); 
		}
	},
	insert:function(param){
		if(this.options.treeField){
			this.realGrid.treegrid('insert',param); 
		}
	},
	modify:function(param){
		if(this.options.treeField){
			this.realGrid.treegrid('update',param); 
		}
	},
	remove:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('remove',id); 
		}
	},
	pop:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('pop',id); 
		}
	},
	refresh:function(id){
		if(this.options.treeField){
			this.realGrid.treegrid('refresh',id); 
		}
	},
	add : function(){
		var obj = this;
		var p = null;
		if(obj.options.buttons && obj.options.buttons.length > 0){
			$(obj.options.buttons).each(function(i){ 
				if(this.action =="add"){
					p = this;
				}
			});
		}
		if(p){
			$('#'+obj.options.renderDialogId).dialog({
			    title: p.title || '自定义对话框',
			    width: p.width || 'auto',
			    height:  p.height || 'auto',
			    left: p.left || null,
			    top:  p.top || null,
			    href: p.url || null,
			    cache : false,
			    loadingMessage : '加载中，请稍等',
			    modal:true,
			    collapsible : p.collapsible || false,
			    minimizable : p.minimizable || false,
			    maximizable : p.maximizable || false,
			    resizable : p.resizable || false,
			    style:p.style || {},
			    buttons : [{  
                    text : '保存',  
                    iconCls:'icon-ok',
                    handler : function(){
			    		$('#'+p.formId).form('submit', {
			    			url : p.addUrl,
			    			onSubmit : function(param){
			    				var isValid = $(this).form('validate');
			    				if (!isValid){
			    					return isValid;
			    				}
			    				if(p.beforeSubmit && !p.beforeSubmit(param)){
			    					isValid = false;
			    					return isValid;
								}
			    				return isValid;
			    			},
			    			success : function(data){
			    				if(!data){
			    					if(p.error){
			    						p.error($('#'+obj.options.renderDialogId));
			    					}
			    					return;
			    				}
			    				try{
			    					data = $.parseJSON(data);
			    				}catch(e){
			    					
			    				}
			    				
			    				if(p.success){
									p.success(data,$('#'+obj.options.renderDialogId));
								}else{
									if((typeof data =="string" && data=== "success") || data.code == "0"){
										obj.reloadData();
										$.messager.success("保存成功");
										$('#'+obj.options.renderDialogId).dialog("close");
									}else{
										$.messager.error(data.message || data);
									}
								}
			    			}
			    		});
					}  
                }, {  
                    text : '关闭',  
                    iconCls:'icon-cancel',
                    handler : function(){
                    	$('#'+obj.options.renderDialogId).dialog("close");
                    }  
                }],
			    onLoad : function(){
			    	if(p.onLoad){
			    		try{
			    			p.onLoad();
			    		}catch(e){
			    		}
			    	}
			    },
			    onBeforeOpen : p.onBeforeOpen || function(){},
			    onOpen : p.onOpen || function(){},
			    onBeforeClose : p.onBeforeClose || function(){},
			    onClose : p.onClose || function(){},
			    onBeforeDestroy: p.onBeforeDestroy || function(){},
			    onMaximize : p.onMaximize || function(){},
			    onMinimize : p.onMinimize || function(){},
			    onRestore : p.onRestore || function(){},
			    onDestroy : p.onDestroy || function(){}
			});
		}
	},
	update : function(id){
		var obj = this;
		var p = null;
		if(obj.options.buttons && obj.options.buttons.length > 0){
			$(obj.options.buttons).each(function(i){ 
				if(this.action =="update"){
					p = this;
				}
			});
		}
		if(p){
			$('#'+obj.options.renderDialogId).dialog({
			    title: p.title || '自定义对话框',
			    width: p.width || 'auto',
			    height:  p.height || 'auto',
			    left: p.left || null,
			    top:  p.top || null,
			    href: p.url || null,
			    cache : false,
			    loadingMessage : '加载中，请稍等',
			    modal:true,
			    collapsible : p.collapsible || false,
			    minimizable : p.minimizable || false,
			    maximizable : p.maximizable || false,
			    resizable : p.resizable || false,
			    style:p.style || {},
			    buttons : [{  
                    text : '保存',  
                    iconCls:'icon-ok',
                    handler : function(){
			    		$('#'+p.formId).form('submit', {
			    			url : p.updateUrl,
			    			onSubmit : function(param){
			    				var isValid = $(this).form('validate');
			    				if (!isValid){
			    					return isValid;
			    				}
			    				if(p.beforeSubmit && !p.beforeSubmit(param)){
			    					isValid = false;
			    					return isValid;
								}
			    				return isValid;
			    			},
			    			success : function(data){
			    				if(!data){
			    					if(p.error){
			    						p.error($('#'+obj.options.renderDialogId));
			    					}
			    					return;
			    				}
			    				try{
			    					data = $.parseJSON(data);
			    				}catch(e){
			    					
			    				}
			    				
			    				if(p.success){
									p.success(data,$('#'+obj.options.renderDialogId));
								}else{
									if((typeof data =="string" && data=== "success") || data.code == "0"){
										obj.reloadData();
										$.messager.success("保存成功");
										$('#'+obj.options.renderDialogId).dialog("close");
									}else{
										$.messager.error(data.message || data);
									}
								}
			    			}
			    		});
					}  
                }, {  
                    text : '关闭',  
                    iconCls:'icon-cancel',
                    handler : function(){
                    	$('#'+obj.options.renderDialogId).dialog("close");
                    }  
                }],
			    onLoad : function(){
			    	if(p.onLoad){
			    		try{
			    			p.onLoad(id);
			    		}catch(e){
			    		}
			    	}
			    	$.ajax({
						url : p.detailUrl,
						data:{
							id : id
						},
						type:'post',
						async:false,
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	$('#'+p.formId).form('load',data);
					    	if(p.renderComplete){
					    		p.renderComplete(data);
					    	}
						},    
					    error : function() {}    
					});
			    },
			    onBeforeOpen : p.onBeforeOpen || function(){},
			    onOpen : p.onOpen || function(){},
			    onBeforeClose : p.onBeforeClose || function(){},
			    onClose : p.onClose || function(){},
			    onBeforeDestroy: p.onBeforeDestroy || function(){},
			    onDestroy : p.onDestroy || function(){},
			    onMaximize : p.onMaximize || function(){},
			    onMinimize : p.onMinimize || function(){},
			    onRestore : p.onRestore || function(){}
			});
		}
	},
	detail : function(id){
		var obj = this;
		var p = null;
		if(obj.options.buttons && obj.options.buttons.length > 0){
			$(obj.options.buttons).each(function(i){ 
				if(this.action =="detail"){
					p = this;
				}
			});
		}
		if(p){
			$('#'+obj.options.renderDialogId).dialog({
			    title: p.title || '自定义对话框',
			    width: p.width || 'auto',
			    height:  p.height || 'auto',
			    left: p.left || null,
			    top:  p.top || null,
			    href: p.url || null,
			    cache : false,
			    loadingMessage : '加载中，请稍等',
			    modal:true,
			    collapsible : p.collapsible || false,
			    minimizable : p.minimizable || false,
			    maximizable : p.maximizable || false,
			    resizable : p.resizable || false,
			    style:p.style || {},
			    buttons : [{  
                    text : '关闭',  
                    iconCls:'icon-cancel',
                    handler : function(){
                    	$('#'+obj.options.renderDialogId).dialog("close");
                    }  
                }],
			    onLoad : function(){
			    	if(p.onLoad){
			    		try{
			    			p.onLoad(id);
			    		}catch(e){
			    		}
			    	}
			    	$.ajax({
						url : p.detailUrl,
						data:{
							id : id
						},
						type:'post',
						async:false,
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
					    	$('#'+p.formId).form('load',data);
					    	if(p.renderComplete){
					    		p.renderComplete(data);
					    	}
						},    
					    error : function() {}  
					});
			    },
			    onBeforeOpen : p.onBeforeOpen || function(){},
			    onOpen : p.onOpen || function(){},
			    onBeforeClose : p.onBeforeClose || function(){},
			    onClose : p.onClose || function(){},
			    onBeforeDestroy: p.onBeforeDestroy || function(){},
			    onDestroy : p.onDestroy || function(){},
			    onMaximize : p.onMaximize || function(){},
			    onMinimize : p.onMinimize || function(){},
			    onRestore : p.onRestore || function(){}
			});
		}
	},
	del : function(id,msg){
		var obj = this;
		var p = null;
		if(obj.options.buttons && obj.options.buttons.length > 0){
			$(obj.options.buttons).each(function(i){ 
				if(this.action =="del"){
					p = this;
				}
			});
		}
		if(p){
			$.messager.confirm('提示', msg || '您确认要删除该数据吗？', function(r){
				if (r){
					$.ajax({
						url : p.delUrl,
						data:{
							id : id
						},
						type:'post',    
					    cache:false,    
					    dataType:'json',    
					    success:function(data) {
		    				if(p.success){
								p.success(data);
							}else{
								if((typeof data =="string" && data=== "success") || data.code == "0"){
									obj.reloadData();
									$.messager.success("删除成功");
								}else{
									if(obj.options.treeField){
										obj.unselect(id);
									}else{
										obj.unselectRow(obj.getRowIndex(id));
									}
									$.messager.error(data.message || data);
								}
							}
		    			},    
					     error : function() { 
					    	 if(obj.options.treeField){
									obj.unselect(id);
								}else{
									obj.unselectRow(obj.getRowIndex(id));
								}
					    	 $.messager.error("删除失败");
					     }    
					});
					
				}else{
					if(obj.options.treeField){
						obj.unselect(id);
					}else{
						obj.unselectRow(obj.getRowIndex(id));
					}
				}
			});
		}
	}
};
