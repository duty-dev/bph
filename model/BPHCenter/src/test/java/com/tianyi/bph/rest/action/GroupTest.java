package com.tianyi.bph.rest.action;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.rest.action.system.GroupManagerAction;

public class GroupTest extends BaseTest {
	
	@Autowired GroupManagerAction groupAction;
	
	//@Test
	public void testSave(){
		//groupAction.saveGroupInfo("222", 1, 1, 1, 2,"[{\"id\":\"1\",\"groupType\":\"2\"}]",1,"[{\"id\":\"1\",\"groupType\":\"2\"}]");
	}
	
	@Test
	public void testGetInfo(){
		ReturnResult result=groupAction.getGroupInfo(1);
		System.out.println(result.getData());
	}
	
	// ==========================================
	/*public static void main(String[] args) {
		test1();
	}
	
	
	public static void test1(){
		String aa="[{\"id\":\"1\",\"groupType\":\"2\"}]";
		JSONArray array=JSONArray.fromObject(aa);
	}*/
}
