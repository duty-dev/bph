function pagination(page ,total ,actionName ,pageCount){
total = total * 1;
var count = 10;
if(pageCount) count = pageCount;
var str = '<a href=\'#this\' onclick=\'javascript:'+actionName+'(1)\'>首页</a> ';
page = page * 1; 
currentlyPage = page ;
   if(total % 11 == 0 && currentlyPage > 0) currentlyPage--;
   if(page > 1){ 
str += '<a href=\'#this\' onclick=\'javascript:'+actionName+'(\"'+ (page - 1) + '\")\'>上一页</a>&nbsp;&nbsp;';
}
   var pagePosi = 0 ;
if(page >= 5) {
   pagePosi = page - 3 ;
} else {
   pagePosi = parseInt(( page + 4) / 5 )- 1;
}
    var pageCount = total % count == 0 ? total / count : parseInt(total / count)+1
     if (page > pageCount) page = pageCount;
for (i = pagePosi + 1; i <= parseInt(pagePosi + 5); i++) {
    if (pageCount < i)
    break;
    if(page != i)
         str += '<a href=\'#this\' onclick=\'javascript:'+actionName+'(\"'+ i + '\")\'>'+i+'</a>&nbsp;&nbsp;';
       else
           str += '<span>'+i+'</span>';
}
var sign = "" ;
if(pageCount > 5)
   sign = "..." ;
    if (pageCount > page +1 ){
   var p = page + 1;
str += sign + '<a href=\'#this\' onclick=\'javascript:'+actionName+'(\"'+ p+ '\")\'>下一页</a>';
}
str += ' <a href=\'#this\' onclick=\'javascript:'+actionName+'(\"'+pageCount+'\")\'>最后一页</a>';
str += ' <font color="#81C9F0">当前页面</font> <font color="#81C9F0">' + page + '</font><font color="#81C9F0"> / 共计 </font> <font color="#81C9F0">' + pageCount + '</font> <font color="#81C9F0">页</font> ';
setH();
return str;
}

function setH(){
	var o = $("#content-wrapper .span12 .span8");
	var o2 = $("#navigationLeft .tree-box");
	var ct=0;
	if(o.length>0){
		ct = o.offset().top;
	}else{
		if(o2.length>0){
			ct = o2.offset().top;
		}else{
			return;
		}
	}
	var wh = $(window).height();
	var v = wh-ct-70;
	$("#navigationLeft .tree-box").css("height",v);
	$(".pow").css("height",v);
	$(".pow .box-content").css("height",v-62);
}

