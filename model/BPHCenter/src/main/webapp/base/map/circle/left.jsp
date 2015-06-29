<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%
Cookie cname=new Cookie("c_name","55555");//设置cookie的键键c_name
cname.setMaxAge(0);//设置cookie的有效期.
//response.setCharacterEncoding("utf-8");
response.addCookie(cname);//设置cookie,将cookie存放到respones里面
%>
<%
//读取cookie
Cookie cookie[]=request.getCookies();
if(cookie!=null){
	for(int i=0;i<cookie.length;i++){
		  Cookie c=cookie[i];	
		  //out.println(c.getName());
		  String name=c.getName();
		  out.println(name);
	}
} 
%> --%>
<div id="navigationLeft">
        <div class="line1-mid box">
          <div class="t1-start"></div>
          <div class="end"></div>
        </div>
        <div class="tree-box clear"><!----机构树---->
        <div id="box">
            <div id="treeview"></div>
		</div>
        </div><!----机构树结束---->
        <div class="line8 box"></div>
        <div class="line2"></div>
</div>        
		<script>
			 $(function() {
					loadCircleLayerTreeList("init");
				});
			 function loadCircleLayerTreeList(type){
				 $.ajax({
						url:"<%=basePath%>"+MapResourceUrl.getCricleLayers,
						type:"post", 
						dataType:"json",
						success:function(req){
								if(req != ""){ 
									var json_data = JSON.stringify(req);
									$("#treeview").empty();
									$("#treeview").kendoTreeView({ 
										 template: "#= item.text #<img id='#= item.id #' onclick='drawCardPoint(this, \"#= item.type #\", #= item.isDraw #)' style='width: 11px; height: 14px; margin-left: 5px;' src='<%=basePath%>supermap/theme/images/others/#= (item.isDraw == true ? \"CaseMarkPressed.png\" : \"CaseMarkNormal.png\") # '>",
									    checkboxes: false, 
									    width:200,
									    dataTextField: "text",  
									    select: onSelect,//点击触发事件
									    dataSource: eval(json_data)
									}).data("treeview"); 
									
									circleDatas = eval(json_data);
									if(type == "init"){
										createCricleLayers(circleDatas, vector);
									}
									
									setTimeout(function(){
										doCardPointChart();
									},200);
								}
						}
					});
			}			 
		</script>

