package com.tianyi.bph.service.alarm;

import java.util.List;

import com.tianyi.bph.domain.alarm.JJDBState;


public interface JJDBStateService {
	
	/**
	 * 获取警情状态分类
	 * 
	 * @param 
	 * @return
	 */
	public List<JJDBState> getJJDBState();
	/**
	 * 警情状态更新
	 * 
	 * @param JJDBState
	 * @return
	 */
	public void updateJJDBState(JJDBState jjdbState);

}
