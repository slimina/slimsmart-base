var Toast = {
	show: function(e, d, c) {
    	$("#Toast_clewBoxDiv").remove();
        if ($("#Toast_clewBoxDiv").length >0) {
            var b = "<span id='mode_clew' style='position:absolute;z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            $("#Toast_clewBoxDiv").html(b);
            $("#Toast_clewBoxDiv").css("display","none");
        } else {
            var a = document.createElement("div");
            a.setAttribute("id", "Toast_clewBoxDiv");
            a.className = "clewBox_layer_wrap";
            var b = "<span id='mode_clew' style='z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            a.innerHTML = b;
            document.body.appendChild(a);
            $("#Toast_clewBoxDiv").css("display","none");
        }
        $("#Toast_clewBoxDiv").fadeIn(1000); 
        setTimeout(function() {
        	 $("#Toast_clewBoxDiv").fadeOut(1000);
        },
        c ? c: 2000);
    },
    success : function(msg,timeout){
    	this.show(msg, "clew_ico_succ",timeout);
    },
    fail : function(msg,timeout){
    	this.show(msg, "clew_ico_fail",timeout);
    },
    warn : function(msg,timeout){
    	this.show(msg, "clew_ico_hits",timeout);
    }
};

if($.messager){
	$.messager.warn=function(msg){
		Toast.warn(msg,3000);
	};
	$.messager.success=function(msg){
		Toast.success(msg,3000);
	};
	$.messager.error=function(msg){
		Toast.fail(msg,3000);
	};
}

//var alertNative = alert || window.alert;

window.alertMsg=function(msg){
	Toast.warn(msg,3000);
};
/*window.alert=function(msg){
	Toast.warn(msg,3000);
};*/