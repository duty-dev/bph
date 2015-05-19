package com.tianyi.bph.web.action.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.web.controller.system.LogController;

public class LogTest extends BaseTest {
	@Autowired
	private LogController logCon;
	
	@Test
	public void testQuery(){
		logCon.gotoLogList(null, 10, 1);
	}
}
