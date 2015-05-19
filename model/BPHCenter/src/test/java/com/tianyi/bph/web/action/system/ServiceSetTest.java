package com.tianyi.bph.web.action.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.web.controller.system.ServiceSetController;

public class ServiceSetTest extends BaseTest{

	@Autowired
	private ServiceSetController setController;
	
	//@Test
	public void testInsert(){
		setController.insert("2222", 1, "222", 12, "11", "123456", "222", "1.0");
	}
	
	//@Test
	public void testUpdate(){
		setController.update(6,"2233", 1, "222", 12, "11", "123456", "222", "1.0");
	}
	
	//@Test
	public void testDelete(){
		setController.delete(6);
	}
	@Test
	public void testGetlist(){
		setController.gotoServiceSetList(null, 10, 1);
	}
}
