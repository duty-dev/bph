package com.tianyi.bph.rest.action.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.PoliceType;
import com.tianyi.bph.domain.system.DataType;
import com.tianyi.bph.service.basicdata.PoliceService;

@Controller
@RequestMapping("/type")
public class DataTypeAction {
	
	@Autowired PoliceService policeService;
	
	/**
	 * 资源类型
	 * @param request
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	@RequestMapping(value = "/getDataTypeInfo.do")
	@ResponseBody
	public ReturnResult getDataTypeInfo() {
		DataType dataType = new DataType();
		List<PoliceType> policeTypeList = policeService.selectPoliceType();
		dataType.setPoliceTypeList(policeTypeList);
		return ReturnResult.SUCCESS("资源类型", policeTypeList);
	}
	

}
