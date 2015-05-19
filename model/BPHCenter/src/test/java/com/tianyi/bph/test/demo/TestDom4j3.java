package com.tianyi.bph.test.demo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class TestDom4j3 {

	static String filepath="D:\\applicationContext.xml";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlString = "<html>" + "<head>" + "<title>dom4j解析一个例子</title>"
                + "<script value='1'>" + "<username>yangrong</username>"
                + "<password>123456</password>" + "</script>"+"<script value='2'>" + "<username>yangrong</username>"
                        + "<password>123456</password>" + "</script>" + "</head>"
                + "</html>";
		//xmltostring(xmlString);
		//doXmlForNode(xmlString,"username","wx");
		applitostrinig();
		
	}
	
	@SuppressWarnings("deprecation")
	public static String applitostrinig(){
	
		 SAXReader saxReader = new SAXReader();  
         Document document;
		try {
			document = saxReader.read(new File(filepath));
			
			Element root=document.getRootElement();
			Iterator beansIter=root.elementIterator("beans");
			while(beansIter.hasNext()){
				Element ele=(Element) beansIter.next();
				if(ele.attributeValue("profile").equals("development")){
					Iterator beaniter=ele.elementIterator("bean");
					while(beaniter.hasNext()){
						Element el=(Element) beaniter.next();
						Iterator proiter=el.elementIterator("property");
						while(proiter.hasNext()){
							Element elel=(Element) proiter.next();
							String name=elel.attributeValue("name");
							String value=elel.attributeValue("value");
							if(name.equals("password")){
								elel.setAttributeValue("value", "123456");
							}
						}
					}
				}
			}
			 try {  
	                /** 将document中的内容写入文件中 */  
	                XMLWriter writer = new XMLWriter(new FileWriter(new File(  
	                		filepath)));  
	                writer.write(document);  
	                writer.close();  
	                System.out.println("执行成功");
	                RmtShellExecutor exe = new RmtShellExecutor("25.30.9.44", "root", "123456",ShellCommandConstant.STARTJBOOS);
	                /** 执行成功,需返回1 */  
	            } catch (Exception ex) {  
	                ex.printStackTrace();  
	            } 
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           
		return null;
	}
	
	
	public static void xmltostring(String xml){
		
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(xml);
			Element root=doc.getRootElement();
			@SuppressWarnings("rawtypes")
			Iterator iter=root.elementIterator("head");
			while(iter.hasNext()){
				Element ele=(Element) iter.next();
				String title=ele.elementTextTrim("title");
				System.out.println("title:"+title);
				
				@SuppressWarnings("rawtypes")
				Iterator iters=ele.elementIterator("script");
				while(iters.hasNext()){
					Element eles=(Element) iters.next();
					String value=eles.attributeValue("value");
					String username=eles.elementTextTrim("username");
					String password=eles.elementTextTrim("password");
					
					System.out.println("username:"+username);
					System.out.println("password:"+password);
					System.out.println("value:"+value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	  * 修改信息xml字符串中指定节点的信息
	  * @param userInfo 用户信息xml
	  * @param nodeName 节点名称
	  * @param nodeValue 节点的新值
	  * @return String 返回修改后的xml字符串
	  * */
	 public static String doXmlForNode(String info,String nodeName ,String nodeValue){
	  if(info == null || "".equals(info)){
	   return "";
	  }
	  Document document = null;
	  String newStr = "";
	  try {
	   document = DocumentHelper.parseText(info);
	   Element rootEl = document.getRootElement();
	   Element personNode;
	  
	   @SuppressWarnings("rawtypes")
	Iterator kk=rootEl.elementIterator("head");
	   while(kk.hasNext()){
		   Element el=(Element) kk.next();
		    for(@SuppressWarnings("rawtypes")
			Iterator k = el.elementIterator("script"); k.hasNext();) {
		    personNode = (Element)k.next();
		    personNode.element(nodeName).setText(nodeValue);
		   }
	   }
	   newStr = document.asXML();
	  }catch(Exception ex){
	   ex.printStackTrace();
	  }
	  return newStr;
	 }

	 public static void properties(){
		 //ClassLoader load = getClass().getClassLoader();
			// 配置中心服务
			Properties pro = new Properties();
			try {
				//URL url = load.getResource("sysConfig.properties");
				File file = new File("");
				pro.load(new FileReader(file));
				pro.setProperty("routeServerIP", "123");
				pro.setProperty("routeServerPort", "1231");
				pro.store(new FileWriter(file), "系统配置信息");
				
				// 重置启动参数
				//SystemConfig.reload(true);
				//req.getServletContext().log("系统配置文件【" + file.getAbsolutePath() + "】修改成功！");
				
			} catch (IOException e) {
				//LOG.error(e.getMessage(), e);
				//res.getWriter().write("{\"success\":false,\"errMsg\":\"" + e.getMessage().replace("\n", "") + "\"}");
				return;
			}
			//req.getServletContext().log("系统配置成功！");
	 }
}
