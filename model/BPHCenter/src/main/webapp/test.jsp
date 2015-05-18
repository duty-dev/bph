<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<html>
 <head>
  <meta name="generator" content="HTML Tidy, see www.w3.org">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Untitled Document</title>

 </head>
 <body>
  <%
  //设置cookie
   //要存放在cookie里面的内容  
   String name="145678我来了次,不用登记了";
   boolean flag=false;//用来判断cookie值是否存在
  
   int time=60*60*24*365;
     //通过构造Cookie
     Cookie cname=new Cookie("c_name",name);//设置cookie的键键c_name
     cname.setMaxAge(time);//设置cookie的有效期.
     //response.setCharacterEncoding("utf-8");
     response.addCookie(cname);//设置cookie,将cookie存放到respones里面

%>
  <% 
  //读取cookie
   Cookie cookie[]=request.getCookies();
   System.out.println(cookie.length+"-----------------'");
   if(cookie!=null){
    for(int i=0;i<cookie.length;i++){
     Cookie c=cookie[i];
     out.println(c.getName());
     if(c.getName().equals("c_name")){//查找cookie里面的是否存在cookie键位c_name的cookie
      //如果存在该键 取该键对应的值==>相当于Map取值
      out.println("已经设置了cookie,cookie的值为:"+c.getValue()+".cookie的时效为:"+time);
      flag=true;//cookie值存在
     }
    }
   }
   
  %>
  <%
  //删除cookie
  Cookie cookies[]=request.getCookies();
   cookies[0].setMaxAge(0);       //删除第1个cookie 
         response.addCookie(cookies[0]);     
  if(cookies!=null){
    for(int i=0;i<cookies.length;i++){
     Cookie c=cookies[i];
     out.println(c.getName());
     if(c.getName().equals("c_name")){//查找cookie里面的是否存在cookie键位c_name的cookie
      cookies[i].setMaxAge(0);       //删除第1个cookie 
             response.addCookie(cookies[i]);  
     }
    }
   }
   %>
  
 </body>
</html>