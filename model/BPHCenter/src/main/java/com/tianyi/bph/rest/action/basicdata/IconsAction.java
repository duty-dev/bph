package com.tianyi.bph.rest.action.basicdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.Icons;
import com.tianyi.bph.service.admin.IconsService;

@Controller
@RequestMapping("/icons")
public class IconsAction {
	
	@Autowired IconsService iconsService;
	
	/**
	 * 获取图标信息
	 * @param request
	 * @param userId 用户Id
	 * @param organId 机构Id
	 * @return
	 */
	@RequestMapping(value = "/getIconsInfo.do")
	@ResponseBody
	public ReturnResult getIconsInfo() {
		List<Icons> iconsList = iconsService.getIconsInfo();
		return ReturnResult.SUCCESS("图标信息", iconsList);
	}

}
