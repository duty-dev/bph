package com.tianyi.bph.domain.system;

public class MqConfig {
	/**
	 * mq 服务配置
	 */
	  private String exchange; //交换机名称
	  private String exchangeType; //交换机类型
	  private String messeageSvrIP; //消息服务IP
	  private String messeagePort; //消息服务端口
	  private String messeageUser; //消息服务用户
	  private String messeagePassword;//消息服务密码
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getMesseageSvrIP() {
		return messeageSvrIP;
	}
	public void setMesseageSvrIP(String messeageSvrIP) {
		this.messeageSvrIP = messeageSvrIP;
	}
	public String getMesseagePort() {
		return messeagePort;
	}
	public void setMesseagePort(String messeagePort) {
		this.messeagePort = messeagePort;
	}
	public String getMesseageUser() {
		return messeageUser;
	}
	public void setMesseageUser(String messeageUser) {
		this.messeageUser = messeageUser;
	}
	public String getMesseagePassword() {
		return messeagePassword;
	}
	public void setMesseagePassword(String messeagePassword) {
		this.messeagePassword = messeagePassword;
	}
}
