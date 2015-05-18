$(function(){
	$("#password").keydown(function(event) {  
        if (event.keyCode == 13) {  
        	submitBtn($("#subBtn").get(0),1); 
        }  
    });
});
//登录及特效
function submitBtn(obj,f){
	var u = $("#username").val();
	var p = $("#password").val();
	if(u != "" && p != ""){
		if(f == 1){
			$(obj).hide();
			var s ='<div class="login-feedback" id="feedback"><p class="col-blue">.服务器连接中</p></div>';
			$("#alarmdialog").html(s);
			var h = '<div class="progressbar-box" id="pgbox"><div class="temp"><div class="progressbar-area"><div class="progressbar" id="progressbar"></div></div></div><div class="progressbar-bottom"></div></div>';
			$(obj).after(h);
			progressbar();
			
			$.ajax({
				type:'POST',
				url:rootPath+'/admin/login.action',
				data:{'username':u,'password':p},
				dataType:'json',
				success:function(result){
					var cd = result.code;
					if(cd == "200"){
						window.location.href=rootPath+"/admin/gotoUserList.action?organId="+result.data.id;
					}else if(cd == "300"){
						$("body").popjs({"title":"提示","content":result.data.msg});
 						submitBtn($("#subBtn").get(0),0);
					}else{
						$("#feedback").append('<p class="col-white">服务器连接失败，错误代码'+result.code+'<span class="col-red" onclick="submitBtn($(\'#subBtn\').get(0),0);">[再次认证]</span></p>');
					}
				}
			});
		}else{
			$("#pgbox").remove();
			$("#alarmdialog").html("");
			$("#subBtn").show();
		}
	}else{
		$("body").popjs({"title":"提示","content":"用户名或密码不能为空！"});
	}
}
//进度条滚动效果
function progressbar(){
	$("#progressbar").css("left","-9%").animate({"left":"100%"},3000,function(){
		progressbar();
	});
}
//关闭窗口
function closeWin(){
	var browserName = navigator.appName;
	if (browserName=="Netscape"){
		window.open('','_self','');
		window.close();
	}else if (browserName == "Microsoft Internet Explorer"){
		window.opener = "whocares";
		window.close();
	} 
}