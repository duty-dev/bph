package com.tianyi.bph.test.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jFileXml {
	
	static String filepath="D:\\test.xml";
	
	public static void main(String[] args) {
		Student stu=new Student();
		stu.setName("wx");
		stu.setPass("123456");
		stu.setSex("男");
		stu.setPhone("18215555555");
		stu.setAge("123");
		stu.setAddress("成都");
		stu.setGrade("博士");
		write(stu);
	}
	
	public static boolean write(Student stu){
		File file=new File(filepath);
		if(file.isFile()){
			try {
				//file.createNewFile(); 
				OutputFormat format=OutputFormat.createPrettyPrint();
				format.setEncoding("utf-8");
				XMLWriter write = new XMLWriter(new FileWriter(file), format); 
				Document doc=DocumentHelper.createDocument();
				Element students=doc.addElement("students");
				Element student =students.addElement("student");
				student.addAttribute("id","001");
				Element name=student.addElement("name");
				name.setText(stu.getName());
				Element age =student.addElement("age");
				age.setText(String.valueOf(stu.getAge()));
				Element pass=student.addElement("pass");
				pass.setText(stu.getPass());
				Element sex =student.addElement("sex");
				sex.setText(stu.getSex());
				Element grade =student.addElement("grade");
				grade.setText(stu.getGrade());
				Element phone =student.addElement("phone");
				phone.setText(stu.getPhone());
				Element address =student.addElement("address");
				address.setText(stu.getAddress());
				try {
					write.write(doc);
					write.flush();
					write.close();
				} catch (IOException e) {
					return false;
				}
					return true;
			} catch (IOException e) {
				System.out.println("write xmlfile error");
				return false;
			}
		}


		/*SAXReader reader=new SAXReader();
		try {
			OutputFormat format=OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter write = new XMLWriter(new FileWriter(new File(filepath)), format);
	
	
			//
			Document doc = reader.read(new File(filepath));
	//			Element students=DocumentHelper.createElement("students");
			Element root=doc.getRootElement();
			Element student =root.addElement("student");
			Element name=student.addElement("name");
			name.setText(stu.getName());
			Element age =student.addElement("age");
			age.setText(String.valueOf(stu.getAge()));
			Element pass=student.addElement("pass");
			pass.setText(stu.getPass());
			Element sex =student.addElement("sex");
			sex.setText(stu.getSex());
	
	
			Element grade =student.addElement("grade");
	
	
			grade.setText(stu.getGrade());
			Element phone =student.addElement("phone");
			phone.setText(stu.getPhone());
			Element address =student.addElement("address");
			address.setText(stu.getAddress());
	
			write.write(doc);
			write.close();
			
			} catch (DocumentException e1) {
				return false;
			} catch(Exception e){
				return false;
			}*/
			return true;
		}


}