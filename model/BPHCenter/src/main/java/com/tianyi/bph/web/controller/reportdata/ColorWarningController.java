package com.tianyi.bph.web.controller.reportdata;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.ReturnResult; 
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;  
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/colorWarningWeb")
public class ColorWarningController {

	@Autowired
	WarningCfgService warningService;
	@Autowired
	OrganService organService;

	/*
	 * 获取警员列表信息
	 * 
	 * police_Query：查询条件包 sort：排序列 order：排序方式 page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "/getColorWarningList.do")
	@ResponseBody
	public PageReturn getColorWarningList(
			@RequestParam(value = "organId", required = false) Integer organId, 
			HttpServletRequest request) throws Exception {
		try {
			User user = (User) request.getAttribute("User");
			Map<String, Object> map = new HashMap<String, Object>();
			if (organId == null) {
				organId = user.getOrgId();
			}
			map.put("orgId", organId);
			Organ organ = organService.getOrganByPrimaryKey(organId);
			if (organ != null) {
				if (organ.getParentId() > 0) {
					map.put("orgParentId", organ.getParentId());
				}
			} 
			// map.put("pageSize", pageSize); 
			int total = warningService.loadWarningCfgVMCountByOrgId(map);
			List<WarningCfgVM> list = warningService
					.loadWarningCfgVMByOrgId(map);
			return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	/*
	 * 获取警员列表信息
	 * 
	 * police_Query：查询条件包 sort：排序列 order：排序方式 page：当前页 rows：每页条数
	 */
	@RequestMapping(value = "/getCaseWarningInfo.do")
	@ResponseBody
	public PageReturn getCaseWarningInfo(
			@RequestParam(value = "caseId", required = false) Integer caseId, 
			HttpServletRequest request) throws Exception {
		try {  
			WarningCfgVM list = warningService.loadWarningCfgVMInfoById(caseId);
			if(list!=null){
				return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, list);
			}else{ 
				return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, list);
			}
		} catch (Exception ex) {
			return PageReturn.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveWarningConfig.do")
	@ResponseBody
	public ReturnResult saveWarningConfig(
			@RequestParam(value = "colorWarning", required = false) String wcg,
			HttpServletRequest request)
			throws Exception {
		try {
			JSONObject jobj = JSONObject.fromObject(wcg);
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			
			classMap.put("colors", WarningColor.class);
			classMap.put("caseTypes", WarningCaseType.class);
			WarningCfgVM warningCfg =  (WarningCfgVM) JSONObject.toBean(jobj, WarningCfgVM.class, classMap);
			int messageCode = 0;
			if (warningCfg != null) {
				warningService.saveWarningCfg(warningCfg);
				messageCode = MessageCode.STATUS_SUCESS;
			} else {
				messageCode = MessageCode.STATUS_FAIL;
			}
			return ReturnResult.MESSAGE(messageCode,
					MessageCode.SELECT_SUCCESS, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "deleteCaseById.do")
	@ResponseBody
	public ReturnResult deleteCaseById(
			@RequestParam(value = "caseId", required = true) Integer caseId)
			throws Exception {
		try {
			if(caseId>0){
				warningService.deleteById(caseId);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null); 
			}else{ 
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					"传入后台id为空", 0, null); 
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL, "删除预警信息出错", 0,
					null);
		}
	}
}
