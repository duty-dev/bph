package com.tianyi.bph.test.demo;

/**
 * @author Allen
 * @descrption 常用 shell脚本
 * @create 2015-2-5
 */
public class ShellCommandConstant {
	/** 停止JBOSS服务 */
	public static final String STOPJBOOS = "JAVA_HOME=/usr/local/jdk1.6.0_06 ;export JAVA_HOME ;PATH=$PATH:.:$JAVA_HOME/bin ;export PATH ;CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar ;export CLASSPATH ;cd /usr/local/jboss-3.2.7/bin/ ;  sh shutdown.sh -S ";
	/** 启动JBOSS服务 */
	public final static String STARTJBOOS = "JAVA_HOME=/usr/local/jdk1.6.0_06 ;export JAVA_HOME ;PATH=$PATH:.:$JAVA_HOME/bin ;export PATH ;CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar ;export CLASSPATH ;cd /usr/local/jboss-3.2.7/bin/ ; sh run.sh -c all ";
	
	public final static String startTomcatShell = "JAVA_HOME=/usr/local/jdk1.6.0_06 ;export JAVA_HOME ;PATH=$PATH:.:$JAVA_HOME/bin ;export PATH ;CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar ;export CLASSPATH ;cd /usr/local/apache-tomcat-7.0.47/bin ;sh startup.sh ";

	
}

