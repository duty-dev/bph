package com.tianyi.bph.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemConfig {
	private static final Logger log=LoggerFactory.getLogger(SystemConfig.class);
	/** 初始化状态 */
    private static boolean initFlag = false;
    private static final Properties PROP = new Properties();
	private String serverPath;
	
	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	/** 设备类型
	 * @author heshengchao@qq.com 
	 * @since  2013-7-29 下午6:12:38
	 */
	public static  interface DevType{
		Integer ALL=null;
		Integer IPC=1;
		Integer DVR=2;
		Integer NVR=3;
	}
	/**
	 * 主题的选择
	 * @author Administrator
	 *
	 */
	public static  interface Theme{
		String SICHUAN="sichuan";
		String SHANGHAI="shanghai";
		String SHANXI="shanxi";
		String JIAJIA="jiajia";
	}
	/**
	 * @author he
	 * 设备分享类型
	 */
	public static interface DevShare{
		Integer OWNER = 0;
		Integer SHARE = 1;
	}
	/**
	 * @author he
	 * 手机客户端类型
	 */
	public static interface CliectType{
		int ANDROID = 1;
		int IOS = 2;
	}
	
	
	/**
	 * @author he
	 *用户类型
	 */
	public static interface UserType{
		Integer OWN = 1;
		Integer SUB = 2;
		Integer NULL=null;
	}
	public static interface Flag{
		String ACTIVATE = "0";
		String ALL = null;
	}
	/**用户状态 0为销户 1为可用*/
	public final static int STATUS_VALID=1;
	public final static int STATUS_INVALID=0;
	
	
	/**
	 * 系统启用的主题（VIEW），为提高性能，单独提取出来。
	 */
	public final static String SYSTEM_THEME=getInstance().getProperty("THEME");
	public final static String ALARM_FLAG=getInstance().getProperty("ALARM_FLAG");
	public final static String DATABASE=getInstance().getProperty("dataBase");
	
	/**
	 * 一级菜单配置
	 */
	public final static String BASE_MANAGER=getInstance().getProperty("baseManager");//基础数据
	public final static String SYSTEM_MANAGER=getInstance().getProperty("systemManager");//基础数据
	public final static String DUTY_MANAGER=getInstance().getProperty("dutyManager");//勤务报备
	public final static String POLICE_MANAGER=getInstance().getProperty("policeManager");//接处警
	public final static String REPORT_GROUP=getInstance().getProperty("reportGroup");//报备分组
	
	public final static String AccessServerPort="accessServerPort";
	public final static String accessServerPortBack="accessServerPort_back";
	
	public final static String turnServerIP="turnServerIP";
	public final static String turnServerPort="turnServerPort";

	public final static String ftpWanIp="ftpWanIp";
	public final static String ftpLanIp="ftpLanIp";
	public final static String ftpPort="ftpPort";
	public final static String ftpUsername="ftpUsername";
	public final static String ftpPassword="ftpPassword";
	public final static String ftpDirectory="ftpDirectory";
	
	public final static String ECloudUrl="eCloudUrl";
	public final static String ERecordBucket="eRecordBucket";
	public final static String EPictureBucket="ePictureBucket";
	/**WEB服务器地址*/
	public final static String website="website";
//	public final static String WEB_SERVER="WEB_SERVER";
	
	
	/**本机外网ＩＰ*/
	public final static String wanIP="wanIP";
	/**本机内网ＩＰ*/
	public final static String lanIP="lanIP";
	/**短信平台ＩＰ*/
	public final static String SMS_PLAT="SMS_PLAT";
	public final static String SMS_SERVER="SMS_SERVER";
	/**省平台信息*/
	public final static String provinceNo = "provinceNo"; 
	public final static String cmsNo = "cmsNo"; 
	public final static String cmsPassword  = "cmsPassword"; 
	public final static String cmsVer = "cmsVer"; 
	public final static String cmsOpen = "cmsOpen";
	public final static String cmsOpenFlag = "true";
	public final static String devUrl = "devUrl";
	public final static String mcuUrl = "mcuUrl";
	private static SystemConfig instance=null;
	private Properties pp;
	
	private SystemConfig(){
		InputStream in=getClass().getClassLoader().getResourceAsStream("system.properties");
		pp=new Properties();
		try {
			pp.load(in);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public static SystemConfig getInstance(){
		if(instance==null){
			instance=new SystemConfig();
		}
		return instance;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return pp.getProperty(key);
	}
	
	
	static final char[] VALIDATE_CODE_SEQ = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',  
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w','x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',  
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
	public static String getRandCode(int len){
		// 创建一个随机数生成器类  
        Random random = new Random(); 
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。  
        StringBuffer randomCode = new StringBuffer();  
        // 随机产生codeCount数字的验证码。  
        for (int i = 0; i < len; i++) {  
            // 得到随机产生的验证码数字。  
            String strRand = String.valueOf(VALIDATE_CODE_SEQ[random.nextInt(62)]);  
            // 将产生的四个随机数组合在一起。  
            randomCode.append(strRand);  
        }
        return randomCode.toString();
	}
	
	 /**
     * 重新装配
     * @param flag
     */
    public static void reload(boolean flag) {
        if (!initFlag || flag) {
            try {
                PROP.clear();
                InputStream inStream = SystemConfig.class.getClassLoader().getResourceAsStream("system.properties");
                PROP.load(inStream);
                initFlag = true;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
	
}
