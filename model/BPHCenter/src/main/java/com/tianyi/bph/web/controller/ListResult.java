package com.tianyi.bph.web.controller;

import java.util.List;

import net.sf.json.JSONObject;

public class ListResult<T> {
	private boolean isSuccess=true;
	private String msg;
	private int total;
	private List<T> rows;
	
	
	public ListResult()
	{
		
	}
	
	public ListResult(int total,List<T> rows){
		this.total=total;
		this.rows=rows;
		this.isSuccess=true;
	}
	
	public ListResult(int total,List<T> rows,boolean isSuccess){
		this.total=total;
		this.rows=rows;
		this.isSuccess=isSuccess;
	}
	
	public String toJson(){
		JSONObject rs=JSONObject.fromObject(this);
		return rs.toString();
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
