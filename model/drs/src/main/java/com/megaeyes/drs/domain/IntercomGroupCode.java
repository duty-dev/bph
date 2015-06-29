package com.megaeyes.drs.domain;

import java.io.Serializable;

/**
 * 对讲机组呼号
 * @author gm
 * @time 2013-7-25 下午03:05:53
 */
public class IntercomGroupCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 753998825951869338L;
	
	
	private int id;
	private String code;  //组呼号编号
	private String name;  //未使用
	private String usedCode;    //使用的组呼号
	private int type;       //组呼号所属CITY类型
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsedCode() {
		return usedCode;
	}
	public void setUsedCode(String usedCode) {
		this.usedCode = usedCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
