package com.tianyi.bph.web.controller.dutydata;

import java.util.ArrayList;
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
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.basicdata.GpsType;
import com.tianyi.bph.domain.basicdata.PoliceType;
import com.tianyi.bph.domain.basicdata.VehicleType;
import com.tianyi.bph.domain.basicdata.WeaponType;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.basicdata.GpsVM;
import com.tianyi.bph.query.basicdata.PoliceVM;
import com.tianyi.bph.query.basicdata.VehicleVM;
import com.tianyi.bph.query.basicdata.WeaponVM;
import com.tianyi.bph.query.duty.GpsGroupVM;
import com.tianyi.bph.query.duty.PoliceGroupVM;
import com.tianyi.bph.query.duty.VehicleGroupVM;
import com.tianyi.bph.query.duty.WeaponGroupVM;
import com.tianyi.bph.service.basicdata.GpsService;
import com.tianyi.bph.service.basicdata.PoliceService;
import com.tianyi.bph.service.basicdata.VehicleService;
import com.tianyi.bph.service.basicdata.WeaponService;
import com.tianyi.bph.service.duty.GpsGroupService;
import com.tianyi.bph.service.duty.PoliceGroupService;
import com.tianyi.bph.service.duty.VehicleGroupService;
import com.tianyi.bph.service.duty.WeaponGroupService;
import com.tianyi.bph.service.system.OrganService;

@Controller
@RequestMapping("/dutyResourceWeb")
public class DutyResourceController {

	@Autowired
	PoliceService policeService;

	@Autowired
	private OrganService organService;
	
	
	@Autowired
	PoliceGroupService policeGroupService;
  
	@Autowired
	VehicleService vehicleService;
	

	@Autowired
	VehicleGroupService vehicleGroupService;
	
	@Autowired
	GpsService gpsService;
	

	@Autowired
	GpsGroupService gpsGroupService;
	
	
	@Autowired
	WeaponService weaponService;

	
	@Autowired
	WeaponGroupService weaponGroupService;
	
	/*
	 * 获取警员资源列表
	 * 
	 * 判断是否有分组id传入，如果有分组，则从分组里面选择警员 若传入分组id为空，则从polie表里面抽取数据
	 */
	@RequestMapping(value = "/getPoliceSource.do")
	public @ResponseBody
	ReturnResult getPoliceSource(
			@RequestParam(value = "police_Query", required = false) String police_Query, 
			HttpServletRequest request)throws Exception {
		try {

			List<PoliceVM> list = new ArrayList<PoliceVM>();
			Map<String, Object> map = new HashMap<String, Object>(); 
			JSONObject joQuery = JSONObject.fromObject(police_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			String name =  joQuery.getString("name"); 
			String typeId = joQuery.getString("typeId");
			String groupId = joQuery.getString("groupId");
			Organ organ = new Organ();
			if (orgId >0) {
				organ = organService.getOrganByPrimaryKey(orgId);
			}
			map.put("orgId", orgId);
			map.put("orgPath", organ.getPath());
			map.put("orgCode", organ.getCode());
			map.put("name", name);

			if (typeId != null && !typeId.equals("")) {
				String[] s = {};
				s = typeId.split(",");
				int[] ids = new int[s.length];
				for (int i = 0; i < s.length; i++) {
					ids[i] = Integer.parseInt(String.valueOf(s[i]));
				}
				map.put("ids", ids);
			}

			if (groupId != null && !groupId.equals("")) {
				String[] gs = {};
				gs = groupId.split(",");
				int[] gids = new int[gs.length];
				for (int i = 0; i < gs.length; i++) {
					gids[i] = Integer.parseInt(String.valueOf(gs[i]));
				}
				map.put("gids", gids);
				list = policeService.loadVMListWithGroupList(map);
			} else {
				list = policeService.loadVMListWithGroup(map);
			} 
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
	
	/**
	 * 获取警员分组，用于报备资源分组查询
	 * 
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPoliceGrouplist.do")
	public @ResponseBody
	ReturnResult getPoliceGrouplist(
			@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);

			List<PoliceGroupVM> pgvms = policeGroupService
					.loadVMListByOrgIdShared(map);
			int total = pgvms.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, pgvms);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}

	}

	/*
	 * 获取警员类型列表，一数据列表的形式展现；
	 */
	@RequestMapping(value = "/getPoliceTypeList.do")
	public @ResponseBody
	ReturnResult getPoliceTypeList() throws Exception {
		try {
			List<PoliceType> list = policeService.selectPoliceType();
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_ORGAN_FAIL, 0, null);
		}
	}


	/*
	 * 获取武器资源列表
	 * 
	 * 判断是否有分组id传入，如果有分组，则从分组里面选择武器 若传入分组id为空，则从vehicle表里面抽取数据
	 */
	@RequestMapping(value = "/getVehicleSource.do")
	public @ResponseBody
	ReturnResult getVehicleSource(
			@RequestParam(value = "vehicle_Query", required = false) String vehicle_Query, 
			HttpServletRequest request)throws Exception {
		try {

			List<VehicleVM> list = new ArrayList<VehicleVM>();
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject joQuery = JSONObject.fromObject(vehicle_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			String number =  joQuery.getString("number");	
			Organ organ = new Organ();
			if (orgId >0) {
				organ = organService.getOrganByPrimaryKey(orgId);
			}
			map.put("orgId", orgId);
			map.put("orgPath", organ.getPath());
			map.put("orgCode", organ.getCode());
			String typeId = joQuery.getString("typeId");
			String groupId = joQuery.getString("groupId");

			map.put("number", number);
			if (typeId != null && !typeId.equals("")) {
				String[] s = {};
				s = typeId.split(",");
				int[] ids = new int[s.length];
				for (int i = 0; i < s.length; i++) {
					ids[i] = Integer.parseInt(String.valueOf(s[i]));
				}
				map.put("ids", ids);
			} 

			if (groupId != null && !groupId.equals("")) {
				String[] gs = {};
				gs = groupId.split(",");
				int[] gids = new int[gs.length];
				for (int i = 0; i < gs.length; i++) {
					gids[i] = Integer.parseInt(String.valueOf(gs[i]));
				}
				map.put("gids", gids);
				list = vehicleService.loadVMListWithGroupList(map);
			} else {
				list = vehicleService.loadVMListWithGroup(map);
			} 
			//list = vehicleService.loadVMListWithGroup(map);
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}

	/**
	 * 获取车辆分组列表，用于报备资源分组条件筛选
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getVehicleGrouplist.do")
	public @ResponseBody
	ReturnResult getVehicleGrouplist(@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);

			List<VehicleGroupVM> pgvms = vehicleGroupService.loadVMListByOrgIdShared(map);
			int total = pgvms.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, pgvms);
		}catch(Exception ex){
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
		
	}
	/*
	 * 
	 * 获取车辆类型列表数据，以数据列表的形式展现
	 * 
	 * 用于报备中车辆资源的过滤条件筛选；
	 */
	@RequestMapping(value = "/getvehicleTypelist.do")
	public @ResponseBody
	ReturnResult getvehicleTypelist() throws Exception {
		try {
			List<VehicleType> list = vehicleService.selectVehicleType();
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
	/*
	 * 获取武器资源列表
	 * 
	 * 判断是否有分组id传入，如果有分组，则从分组里面选择武器 若传入分组id为空，则从vehicle表里面抽取数据
	 */
	@RequestMapping(value = "/getGpsSource.do")
	public @ResponseBody
	ReturnResult getGpsSource(
			@RequestParam(value = "gps_Query", required = false) String gps_Query, 
			HttpServletRequest request)throws Exception {
		try {

			List<GpsVM> list = new ArrayList<GpsVM>();
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject joQuery = JSONObject.fromObject(gps_Query);
			int orgId = Integer.parseInt(joQuery.getString("orgId"));
			String gpsname =  joQuery.getString("gpsname"); 
			String typeId = joQuery.getString("typeId");
			String groupId = joQuery.getString("groupId");

			Organ organ = new Organ();
			if (orgId >0) {
				organ = organService.getOrganByPrimaryKey(orgId);
			}
			map.put("orgId", orgId);
			map.put("orgPath", organ.getPath());
			map.put("orgCode", organ.getCode());
			if (groupId != null && !groupId.equals("")) {
				String[] gs = {};
				gs = groupId.split(",");
				int[] gids = new int[gs.length];
				for (int i = 0; i < gs.length; i++) {
					gids[i] = Integer.parseInt(String.valueOf(gs[i]));
				}
				map.put("gids", gids);
			}
			if (typeId != null && !typeId.equals("")) {
				String[] s = {};
				s = typeId.split(",");
				int[] ids = new int[s.length];
				for (int i = 0; i < s.length; i++) {
					ids[i] = Integer.parseInt(String.valueOf(s[i]));
				}
				map.put("ids", ids);
			} 
			map.put("gpsname", gpsname);
			list = gpsService.loadVMListWithGroup(map);
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}

	/**
	 * 获取定位设备分组，用于GPS资源分组查询
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getGpsGrouplist.do")
	public @ResponseBody
	ReturnResult getGpsGrouplist(@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);

			List<GpsGroupVM> pgvms = gpsGroupService.loadVMListByOrgIdShared(map);
			int total = pgvms.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, pgvms);
		}catch(Exception ex){
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
		
	}
	/*
	 * 获取定位设备类型，列表形式 参数： id:前台选择的数据id集
	 */
	@RequestMapping(value = "/getGpsTypelist.do")
	public @ResponseBody
	ReturnResult getGpsTypelist() throws Exception {
		try {
			List<GpsType> list = gpsService.selectGpsType();
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
	/*
	 * 获取武器资源列表
	 * 
	 * 判断是否有分组id传入，如果有分组，则从分组里面选择武器 若传入分组id为空，则从vehicle表里面抽取数据
	 */
	@RequestMapping(value = "/getWeaponSource.do")
	public @ResponseBody
	ReturnResult getWeaponSource(
			@RequestParam(value = "weapon_Query", required = false) String weapon_Query,
			HttpServletRequest request)throws Exception {
		try { 
			List<WeaponVM> list = new ArrayList<WeaponVM>();
			Map<String, Object> map = new HashMap<String, Object>();

			JSONObject joQuery = JSONObject.fromObject(weapon_Query);
			String number =  joQuery.getString("number"); 
			String typeId = joQuery.getString("typeId");
			String groupId = joQuery.getString("groupId");

			int orgId = Integer.parseInt(joQuery.getString("orgId"));

			Organ organ = new Organ();
			if (orgId >0) {
				organ = organService.getOrganByPrimaryKey(orgId);
			}
			map.put("orgId", orgId);
			map.put("orgPath", organ.getPath());
			map.put("orgCode", organ.getCode());
			map.put("number", number); 
			if (groupId != null && !groupId.equals("")) {
				String[] gs = {};
				gs = groupId.split(",");
				int[] gids = new int[gs.length];
				for (int i = 0; i < gs.length; i++) {
					gids[i] = Integer.parseInt(String.valueOf(gs[i]));
				}
				map.put("gids", gids);
			}
			if (typeId != null && !typeId.equals("")) {
				String[] s = {};
				s = typeId.split(",");
				int[] ids = new int[s.length];
				for (int i = 0; i < s.length; i++) {
					ids[i] = Integer.parseInt(String.valueOf(s[i]));
				}
				map.put("ids", ids);
			} 
			list = weaponService.loadVMListWithGroup(map);
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);

		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}

	/**
	 * 获取武器分组列表，用于报备资源费祖筛选查询
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWeaponGrouplist.do")
	public @ResponseBody
	ReturnResult getWeaponGrouplist(@RequestParam(value = "orgId", required = false) Integer orgId,
			HttpServletRequest request) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);

			List<WeaponGroupVM> pgvms = weaponGroupService.loadVMListByOrgIdShared(map);
			int total = pgvms.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, pgvms);
		}catch(Exception ex){
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
		
	}

	/*
	 * 获取武器类型列表数据，
	 */
	@RequestMapping(value = "/getWeaponTypelist.do")
	public @ResponseBody
	ReturnResult getWeaponTypelist() throws Exception {
		try {
			List<WeaponType> list = weaponService.selectWeaponType();
			int total = list.size();
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,
					MessageCode.SELECT_SUCCESS, total, list);
		} catch (Exception ex) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,
					MessageCode.SELECT_SUCCESS, 0, null);
		}
	}
}
