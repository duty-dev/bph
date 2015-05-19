package com.tianyi.bph.test.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TestDom4j2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String xmlString = "<html>" + "<head>" + "<title>dom4j解析一个例子</title>"
	                + "<script>" + "<username>yangrong</username>"
	                + "<password>123456</password>" + "</script>"+"<script>" + "<username>wangxing</username>"
	                + "<password>123456</password>" + "</script>" + "</head>"
	                + "<body>" + "<result>0</result>" + "<form>"
	                + "<banlce>1000</banlce>" + "<subID>36242519880716</subID>"
	                + "</form>"+"<form>"
	                + "<banlce>2000</banlce>" + "<subID>00000000000000</subID>"
	                + "</form>" + "</body>" + "</html>";
		 xmlToMap(xmlString);
	}
	
	public static Map xmlToMap(String xml){
		Map map=new HashMap();
		Document doc=null;
		try {
			//将字符串转为xml
			doc = DocumentHelper.parseText(xml);
			//获取根节点
			Element rootElt=doc.getRootElement();
			
			System.out.println("根节点："+rootElt.getName());//拿到根节点名称
			
			@SuppressWarnings("rawtypes")
			Iterator iter=rootElt.elementIterator("head"); // 获取根节点下的子节点head
			
			//遍历head节点
			while(iter.hasNext()){
				
				Element recordEle=(Element) iter.next();
				String title=recordEle.elementText("title");//拿到head节点下的子节点title值
				System.out.println("title :"+title);
				
				@SuppressWarnings("rawtypes")
				Iterator iters = recordEle.elementIterator("script");
				
				//遍历head下面的script节点
				while(iters.hasNext()){
					Element itemEle=(Element) iters.next();
					String userName=itemEle.elementText("username");
					System.out.println("userName:"+userName);
					
					String password=itemEle.elementText("password");
					System.out.println("password:"+password);
				}
			}
			 @SuppressWarnings("rawtypes")
			Iterator iterss=rootElt.elementIterator("body");
			
			 //遍历body节点
			 while(iterss.hasNext()){
				 Element recordEless=(Element) iterss.next();
				 String result=recordEless.elementText("result");//获取body下面子节点的result值
				 System.out.println("result:"+result);
				 
				 //获取body下的子节点form的值
				 @SuppressWarnings("rawtypes")
				Iterator itersElIterator =recordEless.elementIterator("form");
				 while(itersElIterator.hasNext()){
					 Element itemEle =(Element) itersElIterator.next();
					 String banlce =itemEle.elementTextTrim("banlce");
					 String subID=itemEle.elementTextTrim("subID");
					 
					 System.out.println("banlce:" + banlce);
	                 System.out.println("subID:" + subID);
				 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

}
