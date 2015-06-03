package com.tianyi.bph.web.controller.basicdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据库访问配置文件各参数的获取
 * @author lq
 *
 */
public class DbConfig {
    //数据库及server配置文件路径
    private static final String ConfigPath = "system.properties";  
    private static DbConfig instance=null;
    
    private String db_user=null;
    private String db_password=null; 
    private String db_url=null; 
    
    private DbConfig(){}
     
    public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_url() {
		return db_url;
	}

	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}

	public String getDb_password() {
        return db_password;
    } 
    public static DbConfig getInstance(){
        if(instance==null){
            instance= new DbConfig().getNewDbConfig();
        }
        return instance;
    }
    
    private DbConfig getNewDbConfig(){
        
        DbConfig dc=new DbConfig();
        Properties prop = new Properties();  
        String path=null;
        FileInputStream fis=null;
        
        try {
            path = DbConfig.class.getClassLoader().getResource("").toURI().getPath();
            fis = new FileInputStream(new File(path + ConfigPath));
              
        	//ApplicationContext ctx=new FileSystemXmlApplicationContext(path+"/spring/applicationContext.xml");
        	
        	
        	//Object s =  ctx.getBean("dataSource");
            
            prop.load(fis);
            dc.db_user=prop.getProperty("db_user"); 
            dc.db_password=prop.getProperty("db_password");  
            dc.db_url=prop.getProperty("db_url");  
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        return dc;
    }
}