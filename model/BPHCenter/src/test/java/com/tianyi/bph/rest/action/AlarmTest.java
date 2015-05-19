package com.tianyi.bph.rest.action;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.alarm.JJDBState;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.query.alarm.JJDBView;
import com.tianyi.bph.rest.action.alarm.AlarmAction;

public class AlarmTest extends BaseTest{
	@Autowired AlarmAction alarmAction;
	
	//@Test
	public void testAlarmList() throws ParseException{
		
//		ReturnResult result = new ReturnResult();
//		result = alarmAction.getAlarmList(null, null, null, null, null, null, null, null, null, null, null, null, null);
//		System.out.println("************code="+result.getCode());
//		List<JJDBView> alarmList = (List)result.getData();
//		for(JJDBView jjdbView : alarmList){
//			System.out.println("************jjdbh="+jjdbView.getJjdbh());
//		}
	}
	//@Test
	public void testAlarmTypeList(){
		ReturnResult result = new ReturnResult();
		result = alarmAction.getAlarmTypeList(null, null);
		System.out.println("************code="+result.getCode());
		List<AlarmType> alarmTypeList = (List)result.getData();
		for(AlarmType alarmType : alarmTypeList){
			System.out.println("************jjdbh="+alarmType.getTypeName()+"*****等级="+alarmType.getTypeLevel());
		}
	}
	@Test
	public void testAlarmInfo(){
		ReturnResult result = new ReturnResult();
		result = alarmAction.getAlarmInfo(null, "10000000000000000000");
		System.out.println("************code="+result.getCode());
		Jjdb110 jjdb110 = (Jjdb110) result.getData();
		System.out.println("************接警内容="+jjdb110.getBjnr());
	}
	//@Test
	public void testAlarmUpdate() throws ParseException{
		ReturnResult result = new ReturnResult();
		result = alarmAction.updateAlarmInfo(null, "10000000000000000000", true, 1, "20150421161616", "x:121,y:123");
		System.out.println("************code="+result.getCode());
	}
	
	//@Test
	public void testAlarmState(){
		
		ReturnResult result = new ReturnResult();
		result = alarmAction.getAlarmState();
		System.out.println("************code="+result.getCode());
		List<JJDBState> alarmStateList = (List)result.getData();
		for(JJDBState jjdbState : alarmStateList){
			System.out.println("************jjdbState="+jjdbState.getName());
		}
	}

}