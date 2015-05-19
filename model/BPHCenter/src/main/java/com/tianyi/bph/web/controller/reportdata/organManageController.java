package com.tianyi.bph.web.controller.reportdata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/organManageWeb")
public class organManageController {
	@Autowired
	private OrganService organService;

	@RequestMapping({ "/getOrgAndSubOrgList.do", "/getOrgAndSubOrgList.action" })
	@ResponseBody
	public ReturnResult getOrgAndSubOrgList(
			@RequestParam(value = "organId", required = false) Integer organId,
			HttpServletRequest request) {
		try {
			ModelAndView mv = new ModelAndView(
					"/reportdata/alarmmap/alarmMap.jsp");
			User user = (User) request.getAttribute("User");
			if (organId == null) {
				organId = user.getOrgId();
			}
			List<Organ> organList = new ArrayList<Organ>();
			OrganQuery query = new OrganQuery();
			query.setId(organId);
			organList = organService.findOrganById(query);
			String orgPath = "";
			for (int i = 0; i < organList.size(); i++) {
				String namehtml = "<a onclick='parentNodeClick("
						+ organList.get(i).getId() + ")'>"
						+ organList.get(i).getShortName() + "</a>";
				orgPath += namehtml + ">>";
			}
			if(orgPath.length()>2){
				orgPath = orgPath.substring(0, orgPath.length()-2);
			}
			List<Organ> subOrgList = new ArrayList<Organ>();
			OrganQuery organQuery = new OrganQuery();
			organQuery.setParentId(organId);
			subOrgList = organService.getOrganListByParentId(organQuery);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, orgPath,
					subOrgList.size(), subOrgList);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
}
