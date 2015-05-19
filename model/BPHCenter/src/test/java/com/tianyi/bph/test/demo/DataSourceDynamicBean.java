package com.tianyi.bph.test.demo;

/**
 * 数据源动态bean描述对象
 */
public class DataSourceDynamicBean extends DynamicBean {
	private String driverClassName;
	
	private String url;
	
	private String username;
	
	private String password;
	
	public DataSourceDynamicBean(String beanName) {
		super(beanName);
	}
	/* (non-Javadoc)
	 * @see org.youi.common.bean.DynamicBean#getBeanXml()
	 */
	@Override
	protected String getBeanXml() {
		StringBuffer xmlBuf = new StringBuffer();
		xmlBuf.append("<beans profile=\"test\">")
		.append("<bean id=\""+beanName+"\" class=\"com.alibaba.druid.pool.DruidDataSource\" init-method=\"init\" destroy-method=\"close\">")
			.append("<property name=\"driverClassName\" value=\""+driverClassName+"\"/>")
			.append("<property name=\"url\" value=\""+url+"\"/>")
			.append("<property name=\"username\" value=\""+username+"\"/>")
			.append("<property name=\"password\" value=\""+password+"\"/>")
			.append("</bean></beans>");
		return xmlBuf.toString();
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
