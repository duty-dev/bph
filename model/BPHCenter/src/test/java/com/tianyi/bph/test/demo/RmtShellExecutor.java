package com.tianyi.bph.test.demo;

import java.nio.charset.Charset;

public class RmtShellExecutor{
	
	/** *//**  */ 
    //private Connection conn; 
    /** *//** 远程机器IP */ 
    private String     ip; 
    /** *//** 用户名 */ 
    private String     usr; 
    /** *//** 密码 */ 
    private String     psword; 
    /** *//** shell命令 */ 
    private String 	   command;
    
    private String     charset = Charset.defaultCharset().toString(); 

    private static final int TIME_OUT = 1000 * 5 * 60; 
    
    private final static String startTomcatShell ="JAVA_HOME=/usr/local/jdk1.6.0_06 ;export JAVA_HOME ;PATH=$PATH:.:$JAVA_HOME/bin ;export PATH ;CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar ;export CLASSPATH ;cd /usr/local/apache-tomcat-7.0.47/bin ;sh startup.sh ";
    private final static String startJbossShell = "JAVA_HOME=/usr/local/jdk1.6.0_06 ;export JAVA_HOME ;PATH=$PATH:.:$JAVA_HOME/bin ;export PATH ;CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar ;export CLASSPATH ;cd /usr/local/jboss-3.2.7/bin/ ; sh run.sh -c all ";
//    /** *//** 
//     * 构造函数   /usr/local/jboss-3.2.7/bin/run.sh -c all
//     * @param param 传入参数Bean 一些属性的getter setter 实现略 
//     */ 
//    public RmtShellExecutor(ShellParam param) { 
//        this.ip = param.getIp(); 
//        this.usr = param.getUsername(); 
//        this.psword = param.getPassword(); 
//    } 
    
    /** *//** 
     * 构造函数 
     * @param ip 
     * @param usr 
     * @param ps 
     */ 
    public RmtShellExecutor(String ip, String usr, String ps,String command) { 
        this.ip = ip; 
        this.usr = usr; 
        this.psword = ps; 
        this.command = command;
    } 
    
    /** *//** 
     * 登录 
     * 
     * @return 
     * @throws IOException 
     */ 
    /*private boolean login() throws IOException { 
        conn = new Connection(ip); 
        conn.connect(); 
        return conn.authenticateWithPassword(usr, psword); 
    } */

    /** *//** 
     * 执行脚本 
     * 
     * @param cmds 
     * @return 
     * @throws Exception 
     */ 
    /*public int exec() throws Exception { 
        InputStream stdOut = null; 
        InputStream stdErr = null; 
        String outStr = ""; 
        String outErr = ""; 
        int ret = -1; 
        try { 
            if (login()) { 
                // Open a new {@link Session} on this connection 
                Session session = conn.openSession(); 
                // Execute a command on the remote machine. 
                session.execCommand(command); 
                
                stdOut = new StreamGobbler(session.getStdout()); 
                outStr = processStream(stdOut, charset); 
                
                stdErr = new StreamGobbler(session.getStderr()); 
                outErr = processStream(stdErr, charset); 
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT); 
                
                ret = session.getExitStatus(); 
            } else { 
                throw new Exception("登录远程机器失败,IP:" + ip); // 自定义异常类 实现略 
            } 
        } finally { 
            if (conn != null) { 
                conn.close(); 
            } 
            if(stdOut != null){
            	stdOut.close();
            }
            if(stdErr != null){
            	stdErr.close();
            }
        } 
        return ret; 
    } 

    *//** *//** 
     * @param in 
     * @param charset 
     * @return 
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     *//* 
    private String processStream(InputStream in, String charset) throws Exception { 
        byte[] buf = new byte[1024]; 
        StringBuilder sb = new StringBuilder(); 
        while (in.read(buf) != -1) { 
            sb.append(new String(buf, charset)); 
        } 
        return sb.toString(); 
    } 
*/
   /* public static void main(String args[]) throws Exception { 
        RmtShellExecutor exe = new RmtShellExecutor("25.30.9.44", "root", "ceshizu",ShellCommandConstant.STARTJBOOS); 
        // 执行myTest.sh 参数为java Know dummy 
        exe.exec(); 
    } */
}
