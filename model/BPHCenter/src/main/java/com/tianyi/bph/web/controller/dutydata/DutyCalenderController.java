package com.tianyi.bph.web.controller.dutydata;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.basicUtil.ObjResult;
import com.tianyi.bph.domain.duty.Duty;
import com.tianyi.bph.query.duty.DutyItemCountVM;
import com.tianyi.bph.query.duty.DutyItemVM;
import com.tianyi.bph.query.duty.DutyVM;
import com.tianyi.bph.service.duty.DutyService;
import com.tianyi.bph.service.duty.DutyTypeService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/dutyCalendarWeb")
public class DutyCalenderController {

	@Autowired
	private OrganService organService;

	@Autowired
	private DutyService dutyService;

	@Autowired
	private DutyTypeService dutyTypeService;

	/**
	 * 根据月份，获取日历，并加载备勤汇总数据信息
	 * 
	 * @param date
	 *            日期：2014-12-01
	 * @param orgId
	 *            组织机构id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCalender.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getCalender(
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date);
		Calendar c = Calendar.getInstance();

		c.setTime(d);
		int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String result = "[";
		for (int i = 1; i <= totalDays; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
			Date dates = c.getTime();
			Calendar cld = Calendar.getInstance();
			cld.setTime(dates);
			int year = cld.get(Calendar.YEAR);
			int month = cld.get(Calendar.MONTH) + 1;
			String week = getWeekOfDate(dates);
			String dt = "";
			String dtMonth = "";
			if (month < 10) {
				dtMonth = "-0" + month;
			} else {
				dtMonth = "-" + month;
			}
			if (i < 10) {
				dt = year + dtMonth + "-0" + i;
			} else {
				dt = year + dtMonth + "-" + i;
			}
			result += "{\"y\":\"" + year + "\",\"m\":\"" + month
					+ "\",\"d\":\"" + i + "\",\"week\":\"" + week
					+ "\",\"totalpolice\":\"" + getTotalPolice(dt, orgId)
					+ "\"},";
			// + "\",\"dutyList\":\"" + getDutyList(dt, orgId) + "\"},";
		}
		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1) + "]";
		}
		return result;
	}

	/**
	 * 根据日期、组织，获取报备类型列表
	 * 
	 * @param date
	 * @param orgId
	 * @return
	 */
	private String getTotalPolice(String date, Integer orgId) {
		try {
			int dt = 0;
			String dates = date.replace("-", "");
			dt = Integer.parseInt(dates);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ymd", dt);
			map.put("orgId", orgId);
			List<DutyItemCountVM> list = new ArrayList<DutyItemCountVM>();
			list = dutyTypeService.loadDutyItemCount(map);
			String result = "";
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					result += "<li>";
					if (list.get(i).getItemTypeName().equals("警员")) {
						result += list.get(i).getorgName() + "人";
					} else if (list.get(i).getItemTypeName().equals("车辆")) {
						result += list.get(i).getorgName() + "车";
					} else if (list.get(i).getItemTypeName().equals("武器")) {
						result += list.get(i).getorgName() + "武器";
					}
					result += "</li>";
				}
			} else {
				result = "<li class='nobaobei' style='display: list-item;'>无报备</li>";
				// +
				// "<li class='baoBeiBtn'><div class='pasteBtnBox' onclick='selectPasteBox(this,'"+date+"')' style='display: none;'><a href='javascript:void(0);'>粘贴</a></div>"
				// + "<a href='javascript:void(0);'>粘贴</a>"
				// + "</div></li>";
				// result = "无报备";
			}
			return result;
		} catch (Exception ex) {
			return " 获取报备警力发生错误 ";
		}
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysCode[intWeek];
	}

	/**
	 * 报备日历复制报备信息到其他日期
	 * 
	 * @param orgId
	 *            组织机构id
	 * @param ymd
	 *            复制内容的日期
	 * @param targetYmd
	 *            粘贴内容的日期
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/copyDutyByOrgIdAndYMD.do")
	public @ResponseBody
	ReturnResult copyDutyByOrgIdAndYMD(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			@RequestParam(value = "ymd", required = false) Integer ymd,
			@RequestParam(value = "targetYmd", required = false) Integer targetYmd,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		try {
			Duty duty = new Duty();
			duty = dutyService.loadVMByOrgIdAndYmd(orgId, targetYmd);
			if (duty != null) {
				int dutyId = duty.getId();
				dutyService.deleteByDutyId(dutyId);
			}
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("ymd", targetYmd);
			map.put("organId",orgId);
			dutyService.deleteByYMD(map);
			DutyVM lduty = dutyService.loadVMByOrgIdAndYmd(orgId, ymd);
			ObjResult<DutyVM> rs = new ObjResult<DutyVM>(true, null,
					lduty == null ? 0 : lduty.getId(), lduty);
			DutyVM dmv = rs.getObj();
			if (dmv != null) {
				dmv.setId(0);
				dmv.setIsTemplate(false);
				dmv.setYmd(targetYmd);
				dmv.setOrgId(orgId);
				if (dmv.getItems() != null) {
					for (int m = 0; m < dmv.getItems().size(); m++) {
						DutyItemVM divm = new DutyItemVM();
						divm = clearItemId(dmv.getItems().get(m));
						dmv.getItems().set(m, divm);
					}
				}
				dutyService.save(dmv);
				return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
						MessageCode.SELECT_SUCCESS, 0, null);
			} else {
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
						MessageCode.SELECT_ORGAN_FAIL, 0, null);
			}
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}

	private DutyItemVM clearItemId(DutyItemVM dutyItemVM) {
		// TODO Auto-generated method stub
		dutyItemVM.setId(0);
		if (dutyItemVM.getTargets() != null) {
			for (int i = 0; i < dutyItemVM.getTargets().size(); i++) {
				dutyItemVM.getTargets().get(i).setId(0);
			}
		}
		if (dutyItemVM.getItems() != null) {
			for (int j = 0; j < dutyItemVM.getItems().size(); j++) {
				clearItemId(dutyItemVM.getItems().get(j));
			}
		}
		return dutyItemVM;
	}

	/**
	 * 删除选择日期的数据信息
	 * 
	 * @param orgId
	 *            组织机构id
	 * @param ymd
	 *            日期
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteDutyByYMD.do")
	public @ResponseBody
	ReturnResult deleteDutyByYMD(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			@RequestParam(value = "ymd", required = false) Integer ymd,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		try {
			Duty duty = new Duty();
			duty = dutyService.loadVMByOrgIdAndYmd(orgId, ymd);

			if (duty != null) {
				int dutyId = duty.getId();
				dutyService.deleteByDutyId(dutyId);
				dutyService.deleteByPrimaryKey(dutyId);
			}
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, 0, null);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}
	/**
	 * 清除当月所有报备数据信息
	 * 
	 * @param orgId
	 *            组织机构id
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteAllDutyData.do")
	public @ResponseBody
	ReturnResult deleteAllDutyData(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		List<Duty> dutylist = new ArrayList<Duty>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		int firstYMD = Integer.parseInt(year.toString()
				+ (month < 10 ? "0" + month.toString() : month.toString())
				+ "00");
		int lastYMD = Integer.parseInt(year.toString()
				+ (month < 10 ? "0" + month.toString() : month.toString())
				+ "32");

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("orgId", orgId);
		maps.put("firstYMD", firstYMD);
		maps.put("lastYMD", lastYMD);
		dutylist = dutyService.loadVMListByOrgAndYmd(maps);

		if (dutylist.size() > 0) {
			int[] ids = new int[dutylist.size()];
			for (int i = 0; i < dutylist.size(); i++) {
				int id = dutylist.get(i).getId();
				ids[i] = id;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			if (ids.length > 0) {
				map.put("ids", ids);
				dutyService.deleteByDutyIdlist(map);
				for (int j = 0; j < ids.length; j++) {
					int dId = ids[j];
					dutyService.deleteByPrimaryKey(dId);
				}
			}
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
				MessageCode.SELECT_SUCCESS, 0, null);
	}
}
