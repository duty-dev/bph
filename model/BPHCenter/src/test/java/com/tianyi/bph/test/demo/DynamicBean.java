package com.tianyi.bph.test.demo;

/**
 * 动态bean描述对象
 */
public abstract class DynamicBean {
	protected String beanName;

	public DynamicBean(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * 获取bean 的xml描述
	 * 
	 * @return
	 */
	protected abstract String getBeanXml();

	/**
	 * 生成完整的xml字符串
	 * 
	 * @return
	 */
	public String getXml(){
		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(" ")
		.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"").append(" ")
		.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\"")
		.append(" ").append("xmlns:aop=\"http://www.springframework.org/schema/aop\" xmlns:tx=\"http://www.springframework.org/schema/tx\"")
		.append(" ").append("xmlns:jdbc=\"http://www.springframework.org/schema/jdbc\" xmlns:task=\"http://www.springframework.org/schema/task\"")
		.append(" ").append("xmlns:rabbit=\"http://www.springframework.org/schema/rabbit\"")
		.append(" ").append("xsi:schemaLocation=\"http://www.springframework.org/schema/beans")
		.append(" ").append("http://www.springframework.org/schema/beans/spring-beans-3.2.xsd")
	   .append(" ").append("http://www.springframework.org/schema/aop")
		.append(" ").append("http://www.springframework.org/schema/aop/spring-aop-3.2.xsd")
		.append(" ").append("http://www.springframework.org/schema/context")
	   .append(" ").append("http://www.springframework.org/schema/context/spring-context-3.2.xsd")
	   .append(" ").append("http://www.springframework.org/schema/tx")  
	   .append(" ").append("http://www.springframework.org/schema/tx/spring-tx-3.2.xsd")
	   .append(" ").append("http://www.springframework.org/schema/jdbc") 
	   .append(" ").append("http://www.springframework.org/schema/jdbc/spring-jdbc-3.2.xsd")
	   .append(" ").append("http://www.springframework.org/schema/task") 
	   .append(" ").append("http://www.springframework.org/schema/task/spring-task-3.2.xsd")
	   .append(" ").append("http://www.springframework.org/schema/rabbit")
	   .append(" ").append("http://www.springframework.org/schema/rabbit/spring-rabbit-1.1.xsd\">")
		.append(getBeanXml())
		.append("</beans>");
		return buf.toString();
	}
}
