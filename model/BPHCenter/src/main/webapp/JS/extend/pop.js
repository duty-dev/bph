/*
 * popjs 1.0 
 * http://hi.baidu.com/lpf801/home
 *
 * Copyright (c) 2015 lai Pengfei (laipengfeide@163.com)
 *
 * Date: 2015-3-18
 * please prepare jQuery.js file
 *
 */
(function($) {
	$.fn.popjs = function (opt){
		var title,content,callback;
		if(opt!=null){
			title= opt.title;
			content = opt.content;
			callback = opt.callback;
		}else{
			title = '提示';
			content = '操作成功！';
		}
		
		var str='<div class="pop" id="pop">'+
					'<div class="pop-main">'+
						'<div class="pop-title">'+
							'<p>'+title+'</p><em></em>'+
						'</div>'+
						'<div class="pop-txt">'+content+'</div>'+
						'<div class="pop-btn">'+
							'<input type="button" class="pop-ok" id="popOk" value="确 认" />'+
						'</div>'+
					'</div>'+
				'</div>';
		if($("#pop").length == 0){
			var obj = $("body").get(0);
			$(obj).append(str);
			
			showpop();
		}
		
		function showpop(){
			showBg();
			var winW=$(window).width();
			var winH=$(window).height();
			var sclL=$(window).scrollLeft();
			var sclT=$(window).scrollTop();
			var layerW=$("#pop").width();
			var layerH=$("#pop").height();
			var left=sclL+(winW-layerW)/2;
			var top=sclT+(winH-layerH)/2;
			
			$("#pop").css({"display":"block","position":"absolute","top":top,"left":left});
			$("#popOk").focus().keydown(function(event) {  
		        if (event.keyCode == 32) {  
		        	closePop(); 
		        }
		        if(typeof(callback)=="function"){
					callback();
				}
		    }).click(function(){
				if(typeof(callback)=="function"){
					callback();
				}
				closePop();
			});
		}
		//显示灰色JS遮罩层
		function showBg(){
			$(window).scroll(function(){resetBg();});
			$(window).resize(function(){resetBg();});
		}
		
		function resetBg(){
			if($("#pop").length != 0){
				var winW=$(window).width();
				var winH=$(window).height();
				var sclL=$(window).scrollLeft();
				var sclT=$(window).scrollTop();
				var layerW=$("#pop").width();
				var layerH=$("#pop").height();
				var left=sclL+(winW-layerW)/2;
				var top=sclT+(winH-layerH)/2;
				$("#pop").css({"top":top,"left":left});
			}
		}
	}
})(jQuery);

function closePop(){
	$("#pop").remove();
}