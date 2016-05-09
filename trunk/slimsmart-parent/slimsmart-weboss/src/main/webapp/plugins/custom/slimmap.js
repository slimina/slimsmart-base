var bmap = {
    isPanelShow : false,
    map: '',
    point: null,
    coordinatesdiv : "",
    overlays: [],
    overlaysCache: [],
    myPolygon: '',
    myOverlay: [],
    drawingManager: '',
    enableEditingPolygon : true,
    styleOptions: {
        strokeColor:"red",      //边线颜色。
        fillColor:"red",        //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,        //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,     //边线透明度，取值范围0 - 1。
        fillOpacity: 0.3,       //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid'    //边线的样式，solid或dashed。
    },
    /**
     * 实例化
     */
    init: function(rendId,isOpen,points,coordinatesdivId,enableEditingPolygon){
        $("#"+rendId).empty();
        this.isPanelShow = false;
        this.point = null;
        this.coordinatesdiv= !coordinatesdivId ? null: $("#"+coordinatesdivId);
        this.overlays= [];
        this.overlaysCache= [];
        this.myPolygon= '';
        this.myOverlay= [];
        this.enableEditingPolygon = (enableEditingPolygon ==undefined ? true : enableEditingPolygon);
        if(points && points.length){
        	$(points).each(function(i){
        		bmap.myOverlay.push(new BMap.Point(this[0],this[1]));
        	});
        	this.point = new BMap.Point(points[0][0],points[0][1]);
        }
        if(!this.point){
        	this.point = new BMap.Point(116.307852,40.057031);
        }
        
        this.map = new BMap.Map(rendId);
        var map = this.map;
        
        var styleOptions = this.styleOptions;
        map.centerAndZoom(this.point, 12);
        map.enableScrollWheelZoom();
        //实例化鼠标绘制工具
        this.drawingManager = new BMapLib.DrawingManager(map, {
            isOpen: isOpen || false, //是否开启绘制模式
            drawingType : BMAP_DRAWING_POLYGON,
            enableDrawingTool: false, //是否显示工具栏
            drawingToolOptions: {
                anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                offset: new BMap.Size(5, 5), //偏离值
                scale: 0.8 //工具栏缩放比例
            },
            circleOptions: styleOptions, //圆的样式
            polylineOptions: styleOptions, //线的样式
            polygonOptions: styleOptions, //多边形的样式
            rectangleOptions: styleOptions //矩形的样式
        });
        this.drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
        //添加鼠标绘制工具监听事件，用于获取绘制结果
        this.drawingManager.addEventListener('overlaycomplete', bmap.overlaycomplete);
        /*加载一个已有的多边形*/
        if (this.myOverlay && this.myOverlay.length) {
            this.loadMyOverlay();
        };
        map.addEventListener("rightclick",function(e){
             console.log(e);
        });
    },
    loadMyOverlay: function(){
    	bmap.clearAll();
    	bmap.map.centerAndZoom(bmap.point, 12);
    	bmap.map.setViewport(bmap.myOverlay);
    	bmap.myPolygon = new BMap.Polygon(bmap.myOverlay, bmap.styleOptions);
    	if(bmap.enableEditingPolygon){
    		try{bmap.myPolygon.enableEditing();}catch(e){};
    	}
        bmap.myPolygon.addEventListener("lineupdate",function(e){
            bmap.showLatLon(e.currentTarget.W);
        });
        bmap.map.addOverlay(bmap.myPolygon);
    },
    showLatLon: function(a){
        var len = a.length;
        var s = '';
        var arr = [];
        for(var i =0 ; i < len-1; i++){
            arr.push([a[i].lng, a[i].lat]);
            s += '<li>'+ a[i].lng +','+ a[i].lat +'<a class="icon-delete" title="点击删除" onclick="bmap.delPoint('+i+')">&nbsp;</a></li>';
        }
        this.overlaysCache = arr;
        !this.coordinatesdiv || this.coordinatesdiv.html('<ul>'+ s +'</ul>');
    },
    delPoint: function(i){
        if(this.overlaysCache.length <=3 ){
            alert('不能再删除, 请保留3个以上的点.');
            return;
        }
        this.overlaysCache.splice(i,1);
        var a = this.overlaysCache;
        var newOverlay = [];
        for(var i in a ){
            newOverlay.push( new BMap.Point(a[i][0],a[i][1]) );
        }
        this.myOverlay = newOverlay;
        this.loadMyOverlay();
    },
    /**
     *回调获得覆盖物信息
     */
    overlaycomplete: function(e){
        bmap.overlays.push(e.overlay);
        e.overlay.enableEditing();
        e.overlay.addEventListener("lineupdate",function(e){
            bmap.showLatLon(e.currentTarget.W);
        });
    },
    /**
     * 清除覆盖物
     */
    clearAll: function() {
        var map = this.map;
        var overlays = this.overlays;
        for(var i = 0; i < overlays.length; i++){
            map.removeOverlay(overlays[i]);
        }
        this.overlays.length = 0;
        map.removeOverlay(this.myPolygon);
        this.myPolygon = '';
    },
    /**
     *取覆盖物的经纬度
     */
    getOverLay: function(){
        var box = this.myPolygon ? this.myPolygon : this.overlays[this.overlays.length - 1];
        return box.W;
    },
    getCount: function(){
        var n = 0;
        if (this.myPolygon) {
            n++;
        };
        if (this.overlays) {
            n = n + this.overlays.length;
        };
       return n;
    }
};