package com.tianyi.bph.query.duty;

import java.util.HashMap;
import java.util.Map;

public class DutyItemTypeUtil {
	private static Map<Integer,String> typeNames;
	
	static {
		typeNames=new HashMap<Integer,String>();
		
		
		typeNames.put(1, "车辆");
		typeNames.put(2, "警员");
		typeNames.put(3, "武器");
		typeNames.put(4, "定位设备");
		
		typeNames.put(100, "备勤类型");
		typeNames.put(101, "班次");
		typeNames.put(200, "自定义编组");
		
	}
	
	public static String getNameById(Integer id){
		return typeNames.get(id);
	}
}
