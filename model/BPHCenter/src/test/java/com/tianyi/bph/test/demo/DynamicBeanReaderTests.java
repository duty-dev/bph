package com.tianyi.bph.test.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 *junit测试类
 */
@ContextConfiguration(locations={"classpath:spring/aapplicationContext-test.xml","classpath:spring/applicationContext-ehcache.xml","classpath:spring/applicationContext-rabbitmq.xml"})
//@ContextConfiguration(locations={"classpath:spring/aapplicationContext-test.xml"})
//@Profile(value="development")
@ActiveProfiles("test")
public class DynamicBeanReaderTests extends AbstractJUnit4SpringContextTests{
	@Autowired
	private DynamicBeanReader dynamicBeanReader;
  
    @Test
    public void testLoadBean(){
    	DataSourceDynamicBean dataSourceDynamicBean = new DataSourceDynamicBean("dataSource");
    	dataSourceDynamicBean.setDriverClassName("net.sf.log4jdbc.DriverSpy");
    	dataSourceDynamicBean.setUrl("jdbc:log4jdbc:mysql://25.30.9.182:3306/bph?allowMultiQueries=true&amp;autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8");
    	dataSourceDynamicBean.setUsername("root");
    	dataSourceDynamicBean.setPassword("123456");
    	
    	dynamicBeanReader.loadBean(dataSourceDynamicBean);//
    	
    	DataSource s = (DataSource)applicationContext.getBean("dataSource");
    	
    	System.out.println(s);
    	try {
			PreparedStatement ps = s.getConnection().prepareStatement("select name from t_organ where organ_id=1");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			//TODO 
		}
    }
}
