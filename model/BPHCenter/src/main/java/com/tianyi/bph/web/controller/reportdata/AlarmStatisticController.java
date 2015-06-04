package com.tianyi.bph.web.controller.reportdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.query.report.AlarmTypeTreeVM;
import com.tianyi.bph.query.report.AlarmTypeVM;
import com.tianyi.bph.service.alarm.AlarmDispatchService;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.report.WarningCfgService;

@Controller
@RequestMapping("/alarmStatisticWeb")
public class AlarmStatisticController {
	// getAlarmTypeList

	@Autowired
	AlarmDispatchService alarmDispatchService;
	@Autowired
	WarningCfgService warningService;

	@RequestMapping(value = "/getAlarmTypeList.do")
	public @ResponseBody
	ReturnResult getAlarmTypeList(
			@RequestParam(value = "parentCode", required = false) String parentCode,
			@RequestParam(value = "warningId", required = false) Integer warningId,
			HttpServletRequest request) {
		try {
			List<AlarmType> list = new ArrayList<AlarmType>();
			list = alarmDispatchService.getAlarmTypeList(parentCode);

			AlarmTypeTreeVM vmList = new AlarmTypeTreeVM();
			vmList = createtreeVMList(list, warningId);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, vmList);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	private AlarmTypeTreeVM createtreeVMList(List<AlarmType> list,
			Integer warningId) {
		// TODO Auto-generated method stub
		AlarmTypeTreeVM treevm = new AlarmTypeTreeVM();
		treevm.setChecked(false);
		treevm.setExpanded(true);
		treevm.setTypeName("警情分类");
		treevm.setTypeCode("caseType110000000");
		treevm.setTypeLevel(0);
		treevm.setId(1);
		treevm.setText("警情分类");
		treevm.setParentTypeCode(null); 
		treevm.setConstrast(null);
		treevm.setNote("caseType110");
		List<AlarmTypeTreeVM> lst = new ArrayList<AlarmTypeTreeVM>();
		lst = getChildren(list, warningId);
		treevm.setItems(lst);
		return treevm;
	}

	private List<AlarmTypeTreeVM> getChildren(List<AlarmType> list,
			Integer warningId) {
		// TODO Auto-generated method stub
		List<AlarmTypeTreeVM> treelist = new ArrayList<AlarmTypeTreeVM>();
		for (AlarmType at : list) {
			if (at.getParentTypeCode().equals("caseType110")) {
				AlarmTypeTreeVM treevm = new AlarmTypeTreeVM();
				if (warningId > 0) {
					boolean ischecked = getNodechecked(at.getTypeCode(),
							warningId);
					treevm.setChecked(ischecked);
					treevm.setSelected(ischecked);
				} else {
					treevm.setChecked(false);
					treevm.setSelected(false);
				}
				String id = at.getTypeCode().substring(12, at.getTypeCode().length()-2);
				treevm.setId(Integer.parseInt(id));
				treevm.setExpanded(false);
				treevm.setTypeName(at.getTypeName());
				treevm.setTypeCode(at.getTypeCode());
				treevm.setTypeLevel(1);
				treevm.setText(at.getTypeName());
				treevm.setParentTypeCode("caseType110000000");
				treevm.setParentId(1);
				treevm.setConstrast(at.getConstrast());
				treevm.setNote(at.getNote());
				List<AlarmTypeTreeVM> lst = new ArrayList<AlarmTypeTreeVM>();
				lst = getSubChildren(list, warningId, at.getTypeCode(),id);
				treevm.setItems(lst);
				treelist.add(treevm);
			}
		}
		return treelist;
	}

	private boolean getNodechecked(String typeCode, Integer warningId) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("warningId", warningId);
		map.put("caseTypeCode", typeCode);
		WarningCaseType wct =  warningService.getNodechecked(map);
		if(wct!=null){
			return true;
		}else{
			return false;
		} 
	}

	private List<AlarmTypeTreeVM> getSubChildren(List<AlarmType> list,
			Integer warningId, String typeCode,String pid) {
		// TODO Auto-generated method stub
		List<AlarmTypeTreeVM> treelist = new ArrayList<AlarmTypeTreeVM>();
		for(AlarmType at :list){
			if(at.getParentTypeCode().equals(typeCode)){
				AlarmTypeTreeVM treevm = new AlarmTypeTreeVM();
				if (warningId > 0) {
					boolean ischecked = getNodechecked(at.getTypeCode(),
							warningId);
					treevm.setChecked(ischecked);
					treevm.setSelected(ischecked);
				} else {
					treevm.setChecked(false);
					treevm.setSelected(false);
				}
				String id = at.getTypeCode().substring(12, at.getTypeCode().length()-2);
				treevm.setId(Integer.parseInt(id));
				treevm.setExpanded(false);
				treevm.setTypeName(at.getTypeName());
				treevm.setTypeCode(at.getTypeCode());
				treevm.setParentId(Integer.parseInt(pid));
				treevm.setTypeLevel(1);
				treevm.setText(at.getTypeName());
				treevm.setParentTypeCode(at.getParentTypeCode());
				treevm.setConstrast(at.getConstrast());
				treevm.setNote(at.getNote()); 
				treevm.setItems(null);
				treelist.add(treevm);
			}
		}
		return treelist;
	}
}
