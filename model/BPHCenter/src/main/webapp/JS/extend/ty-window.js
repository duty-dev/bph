/*
 * ty-window 1.0 
 * http://hi.baidu.com/lpf801/home
 *
 * Copyright (c) 2015 lai Pengfei (laipengfeide@163.com)
 *
 * Date: 2015-3-18
 * please prepare jQuery.js file
 *
 * 参数对照
 * title：标题1；title2：标题2；no：是否显示取消按钮；noValue：取消按钮文字；ok：是否显示确定按钮;okValue：确定按钮文字；content：内容；position：重新定位（默认屏幕中间显示）；icon：图标（目前支持参数icon）；iframe：是否显示网页；okCallback：确定按钮回调函数；noCallback：取消按钮回调函数；closeCallback：关闭按钮回调函数；
 */
(function($) {
    $.fn.tyWindow = function(opt) {
        var title, title2, no = false,
        noValue, ok = false,
        okValue, content, width, height, position = '',
        icon = '',
        center = false,
        iframe = false,
        okCallback, noCallback, closeCallback;
        if (opt != null) {
            title = opt.title;
            title2 = opt.title2;
            content = opt.content;
            width = opt.width;
            height = opt.height;
            no = opt.no;
            noValue = opt.noValue;
            ok = opt.ok;
            okValue = opt.okValue;
            position = opt.position;
            icon = opt.icon;
            center = opt.center;
            iframe = opt.iframe;
            okCallback = opt.okCallback;
            noCallback = opt.noCallback;
            closeCallback = opt.closeCallback;
            if (title == undefined) title = '提示';
            if (title2 == undefined) title2 = '';
            if (width == undefined) width = '400px';
            if (height == undefined) height = '250px';
            if (content == undefined) content = '';
            if (noValue == undefined) noValue = '取 消';
            if (okValue == undefined) okValue = '确 定';
        } else {
            width = '400px';
            height = '250px';
            title = '提示';
            title2 = '';
            no = false;
            ok = false;
            content = '操作成功！';
            okValue = '确 定';
            noValue = '取 消';
        }
        var str = '<div class="ty-tcc" id="tyTcc" style="width:' + width + ';height:' + height + ';"><table class="ty-tcc-table"><tr class="ty-tcc-tit"><td width="30"><i class="ty-tcc-bg1"></i><i class="ty-tcc-bg4 opa9"></i></td><td><i class="ty-tcc-bg2"></i><div class="ty-tcc-top opa9"></div></td><td width="30"><i class="ty-tcc-bg3"></i><i class="ty-tcc-bg5 opa9"></i><div class="temp"><i class="ty-tcc-close" id="tccClose"></i></div></td></tr><tr><td></td><td><div class="ty-tcc-main opa9" id="tyTccMain"><div class="ty-tcc-mh"><table class="ty-tcc-table"><tr><td><div id="tyTccTitle" class="fl mr10"><i class="ty-tcc-mh-icon"></i><span class="ty-tcc-title1"><p>' + title + '</p><em></em></span><span class="ty-tcc-title2">' + title2 + '</span></div></td><td><span class="ty-tcc-title3"><div class="temp"><em></em></div></span></td></tr></table></div><div class="ty-tcc-info"><table id="tyTccTable" class="ty-tcc-table"><tr>';
        if (icon != undefined) {
            str += '<td width="110px"><i class="ty-tcc-' + icon + '"></i></td>';
        }
        str += '<td ' + ((center) ? ('align=center') : '') + '>';
        if (iframe) {
            str += '<iframe frameborder="0" src="' + content + '" class="ty-tcc-frame opa9" id="tyIframeContent" title="' + title + '"></iframe>';
        } else {
            str += content;
        }
        str += '</td></tr></table></div>';
        if (ok || no) {
            str += '<div class="ty-tcc-btn">';
            if (ok) {
                str += '<input type="button" class="ty-tcc-btn-ok" value="' + okValue + '" id="tyTccOk" />';
            }
            if (no) {
                str += '<input type="button" class="ty-tcc-btn-no" value="' + noValue + '" id="tyTccNo" />';
            }
            str += '</div>';
        }
        str += '</div></td><td></td></tr><tr><td><i class="ty-tcc-bg6 opa9"></i></td><td><div class="ty-tcc-bottom opa9"></div></td><td><i class="ty-tcc-bg7 opa9"></i></td></tr><tr><td><i class="ty-tcc-bg8"></i></td><td><i class="ty-tcc-bg9"></i></td><td><i class="ty-tcc-bg10"></i></td></tr></table></div>';
        if ($("#tyTcc").length == 0) {
            var obj = $("body").get(0);
            $(obj).append(str);
            openTcc();
        } else {
            return;
        }
        function openTcc() {
            showBg();
            var winW=$(window).width();
			var winH=$(window).height();
			var sclL=$(window).scrollLeft();
			var sclT=$(window).scrollTop();
			var layerW=$("#tyTcc").width();
			var layerH=$("#tyTcc").height();
			var left=sclL+(winW-layerW)/2;
			var top=sclT+(winH-layerH)/2;
            if (position != undefined) {
                if (position.top != undefined) top = position.top;
                if (position.left != undefined) left = position.left;
            }
            $("#tyTcc").css({
                "display": "block",
                "position": "absolute",
                "top": top,
                "left": left
            });
            drag();
            var w = $("#tyTccTitle").width();
            $("#tyTccTitle").parent().css("width", w + 10);
            var hei = height.substring(0, height.indexOf("p"));
            //$("#tyTccbg").css({"width":width,"height":hei-32});
            $("#tyTccTable .ty-tcc-frame").css("height", hei - 172);
            $("#tyTccNo").bind("click",
            function() {
                if (typeof(noCallback) == "function") {
                    noCallback();
                }
                closeTcc();
            });
            $("#tccClose").bind("click",
            function() {
                if (typeof(closeCallback) == "function") {
                    closeCallback();
                }
                closeTcc();
            });
            $("#tyTccOk").bind("click",
            function() {
                if (typeof(okCallback) == "function") {
                    okCallback();
                }
                closeTcc();
            });
        }
        function showBg() {
            $(window).scroll(function() {
                resetBg();
            });
            $(window).resize(function() {
                resetBg();
            });
        }
        function resetBg() {
            if ($("#tyTcc").length != 0) {
            	var winW=$(window).width();
    			var winH=$(window).height();
    			var sclL=$(window).scrollLeft();
    			var sclT=$(window).scrollTop();
    			var layerW=$("#tyTcc").width();
    			var layerH=$("#tyTcc").height();
    			var left=sclL+(winW-layerW)/2;
    			var top=sclT+(winH-layerH)/2;
                if (position != undefined) {
                    if (position.top != undefined) top = position.top;
                    if (position.left != undefined) left = position.left;
                }
                $("#tyTcc").css({
                    top: top,
                    left: left
                });
            }
        }
        function drag(){
        	var oDrag = $("#tyTcc").get(0);
        	var oTitle = $("#tyTcc .ty-tcc-tit").get(0);
        	var posX=posY=0;
        	oTitle.onmousedown=function(event){
        		oTitle.style.cursor = "move";
        		var event = event || window.event;
        		var disX=event.clientX-oDrag.offsetLeft;
        		var disY=event.clientY-oDrag.offsetTop;
        		//鼠标移动，窗口随之移动 
        		document.onmousemove=function(event){
        			var event = event || window.event;
        			var maxW=document.documentElement.clientWidth-oDrag.offsetWidth;
        			var maxH=document.documentElement.clientHeight-oDrag.offsetHeight;
        			posX=event.clientX-disX;
        			posY=event.clientY-disY;
        			if(posX<0){
        				posX=0;
        			}else if(posX>maxW){
        				posX=maxW;
        			}
        			if(posY<0){
        				posY=0;
        			}else if(posY>maxH){
        				posY=maxH;
        			}
        			oDrag.style.left=posX+'px';
        			oDrag.style.top=posY+'px';
        		}
        		//鼠标松开，窗口将不再移动
        		document.onmouseup=function(){
        			document.onmousemove=null;
        			document.onmouseup=null;
        		}
        	}
        }
    }
    var f = $.fn.tyWindow;
    f.open = function(o) {
        f(o);
    }
    f.close = function() {
        closeTcc();
    }
})(jQuery);
function closeTcc() {
	$("#tyIframeContent").remove();
    $("#tyTcc").remove();
}