package com.tianyi.bph.service.impl.basicdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.basicdata.ExportMapper;
import com.tianyi.bph.dao.basicdata.VehicleMapper;
import com.tianyi.bph.dao.system.OrganDAO;
import com.tianyi.bph.domain.basicdata.Gps;
import com.tianyi.bph.domain.basicdata.IntercomGroup;
import com.tianyi.bph.domain.basicdata.Police;
import com.tianyi.bph.domain.basicdata.Vehicle;
import com.tianyi.bph.domain.basicdata.VehiclePurpose;
import com.tianyi.bph.domain.basicdata.VehicleType;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.basicdata.ExtDbResult;
import com.tianyi.bph.query.basicdata.ExtShiftInfo;
import com.tianyi.bph.query.basicdata.GpsBaseVM;
import com.tianyi.bph.query.basicdata.GpsInfo;
import com.tianyi.bph.query.basicdata.GroupMemberVM;
import com.tianyi.bph.query.basicdata.ItemInfo;
import com.tianyi.bph.query.basicdata.PoliceEInfo;
import com.tianyi.bph.query.basicdata.VehicleExtItem;
import com.tianyi.bph.query.basicdata.VehicleInfo;
import com.tianyi.bph.query.basicdata.VehicleVM;
import com.tianyi.bph.service.basicdata.VehicleService;

/**
 * 车辆接口实现
 * 
 * @author lq
 * 
 */
@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	VehicleMapper vehicleMapper;

	@Autowired
	ExportMapper exportMapper;

	@Autowired
	OrganDAO organMapper;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#findByidCard(Vehicle
	 *      vehicle)
	 */
	public int findCount(Vehicle vehicle) {
		int count = vehicleMapper.countByExample(vehicle);
		return count;
	}

	public Vehicle selectByPrimaryKey(Integer vehicleId) {
		return vehicleMapper.selectByPrimaryKey(vehicleId);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#findPageList(Vehicle
	 *      query,PaginationData page)
	 */
	public List<Vehicle> findPageList(Vehicle query, Pager page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!(query.getNumber() == null || query.getNumber().length() == 0))
			map.put("number", query.getNumber());

		map.put("pageStart", page.getCurrentPage());
		map.put("pageSize", page.getPageSize());
		List<Vehicle> list = vehicleMapper.selectByExample(map);

		return list;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see 
	 *      com.tianyi.drs.basedata.service.VehicleService#loadVMList(Map<String,
	 *      Object> query)
	 */
	public List<VehicleVM> loadVMList(Map<String, Object> query) {

		List<VehicleVM> list = vehicleMapper.loadVMList(query);

		return list;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see 
	 *      com.tianyi.drs.basedata.service.VehicleService#loadVMCount(Map<String
	 *      ,Object> query)
	 */
	public int loadVMCount(Map<String, Object> query) {
		int count = vehicleMapper.loadVMCount(query);
		return count;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#updateByPrimaryKey(Vehicle
	 *      vehicle)
	 */
	@MQDataInterceptor(type = Constants.MQ_TYPE_VEHICLE, operate = Constants.MQ_OPERATE_UPDATE)
	public Vehicle updateByPrimaryKey(Vehicle vehicle) {
		// TODO Auto-generated method stub
		vehicleMapper.updateByPrimaryKey(vehicle);
		return vehicle;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#insert(Vehicle
	 *      vehicle)
	 */

	@MQDataInterceptor(type = Constants.MQ_TYPE_VEHICLE, operate = Constants.MQ_OPERATE_ADD)
	public Vehicle insert(Vehicle vehicle) {
		// TODO Auto-generated method stub
		vehicleMapper.insert(vehicle);
		return vehicle;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#deleteByPrimaryKey(int
	 *      id)
	 */

	@MQDataInterceptor(type = Constants.MQ_TYPE_VEHICLE, operate = Constants.MQ_OPERATE_DELETE)
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		vehicleMapper.deleteByPrimaryKey(id);
		return id;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#selectVehicleType()
	 */
	public List<VehicleType> selectVehicleType() {
		// TODO Auto-generated method stub
		return vehicleMapper.selectVehicleType();
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#selectVehiclePurpose()
	 */
	public List<VehiclePurpose> selectVehiclePurpose() {
		// TODO Auto-generated method stub
		return vehicleMapper.selectVehiclePurpose();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#selectIntercomGroup()
	 */
	public List<IntercomGroup> selectIntercomGroup() {
		// TODO Auto-generated method stub
		return vehicleMapper.selectIntercomGroup();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see 
	 *      com.tianyi.drs.basedata.service.VehicleService#loadVMListWithGroup(Map
	 *      <String, Object> map)
	 */
	public List<VehicleVM> loadVMListWithGroup(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return vehicleMapper.loadVMListWithGroup(map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see 
	 *      com.tianyi.drs.basedata.service.VehicleService#deleteByIds(Map<String
	 *      , Object> map)
	 */
	public void deleteByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		vehicleMapper.deleteByIds(map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#findByNumber(String)
	 */
	public List<VehicleVM> findByNumber(String param) {
		// TODO Auto-generated method stub
		return vehicleMapper.findByNumber(param);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#findByIdAndDtyId(String)
	 */
	public List<Vehicle> findByIdAndDtyId(String param) {
		// TODO Auto-generated method stub
		return vehicleMapper.findByIdAndDtyId(param);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.tianyi.drs.basedata.service.VehicleService#loadListByOrgId(Map)
	 */
	public List<Vehicle> getVehicleInfo(Integer orgId, Integer isSubOrg) {
		// TODO Auto-generated method stub
		Organ org = new Organ();
		String orgPath = "";

		Map<String, Object> map = new HashMap<String, Object>();
		if (isSubOrg == Constants.SEARCH_TYPE_CHILD) {
			org = organMapper.selectByPrimaryKey(orgId);
			orgPath = org.getPath();
			map.put("orgPath", orgPath);
		}
		map.put("orgId", orgId);
		return vehicleMapper.getVehicleInfo(map);
	}

	public List<VehicleExtItem> getVehicleDutyInfo(Integer orgId, Integer ymd,
			Integer isSubOrg) {
		Map<Integer, ItemInfo<?>> cache = new HashMap<Integer, ItemInfo<?>>();// dutyItemId局部缓存，避免大量低效率的循环。
		Map<Integer, Object> cache2 = new HashMap<Integer, Object>();// ItemId
																		// //
		List<VehicleExtItem> vlst = new ArrayList<VehicleExtItem>();
		// 局部缓存，避免大量低效率的循环。Object无意义，都为null
		List<VehicleInfo> vs = new ArrayList<VehicleInfo>();

		Organ org = new Organ();
		String orgPath = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		if (isSubOrg == Constants.SEARCH_TYPE_CHILD) {
			org = organMapper.selectByPrimaryKey(orgId);
			orgPath = org.getPath();
			map.put("orgPath", orgPath);
		}
		if (ymd == null || ymd == 0) {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String date = dateFormat.format(now);
			ymd = Integer.parseInt(date);

		}
		map.put("ymd", ymd);

		List<ExtDbResult> rs = exportMapper.loadDutyItemInfo(map);
String dutyTypeName = "";
		for (ExtDbResult r : rs) {
			if(r.getDutyTypeName()!=null){
				dutyTypeName = r.getDutyTypeName();
			}
			if (r.getItemTypeId() == 1) {

				VehicleInfo v = this.createVehicleInfo(r);
				v.setShiftInfo(this.createShiftInfo(r)); // 只有第一层需要写班次信息
				v.setDutyTypeName(dutyTypeName);
				cache.put(v.getDutyItemId(), v);// 添加到缓存
				cache2.put(v.getData().getId(), null);
				vs.add(v);// 添加到list

			} else {
				if (cache.containsKey(r.getParentId())) {
					if (cache.get(r.getParentId()).getItemTypeId() == 1) {
						VehicleInfo pp = (VehicleInfo) cache.get(r
								.getParentId());

						switch (r.getItemTypeId()) {
						case 2:// police
							if (pp.getPoliceItems() == null) {
								pp.setPoliceItems(new ArrayList<PoliceEInfo>());
							}
							PoliceEInfo p = createPoliceEInfo(r);
							pp.getPoliceItems().add(p);
							cache.put(r.getDutyItemId(), p);
							break;
						case 4: // gps
							if (pp.getGpsItems() == null) {
								pp.setGpsItems(new ArrayList<GpsInfo>());
							}
							GpsInfo g = createGpsInfo(r);
							pp.getGpsItems().add(g);
							cache.put(r.getDutyItemId(), g);
							break;
						}
					}
				}
			}
		}

		List<Vehicle> mps = vehicleMapper.getVehicleInfo(map);

		for (Vehicle mp : mps) {
			if (!cache2.containsKey(mp.getId())) {
				VehicleInfo ep2 = new VehicleInfo();
				ep2.setData(mp);
				vs.add(ep2);
			}
		}
		vlst = CreateVehicleExtItem(vs);
		return vlst;
	}

	private List<VehicleExtItem> CreateVehicleExtItem(List<VehicleInfo> eps) {
		// TODO Auto-generated method stub
		List<VehicleExtItem> pets = new ArrayList<VehicleExtItem>();
		for (VehicleInfo rs : eps) {
			VehicleExtItem pei = new VehicleExtItem();

			pei.setData(rs.getData());
			pei.setDutyItemId(rs.getDutyItemId());
			pei.setDutyTypeName(rs.getDutyTypeName());
			pei.setItemTypeId(rs.getItemTypeId());
			pei.setShiftInfo(rs.getShiftInfo());

			if (rs.getGpsItems() != null) {
				if (rs.getGpsItems().size() > 0) {
					for (int i = 0; i < rs.getGpsItems().size(); i++) {
						GpsInfo ep = rs.getGpsItems().get(i);
						List<Integer> gpsid = new ArrayList<Integer>();
						if (ep.getData() != null) {
							Gps g = (Gps) ep.getData();
							gpsid.add(g.getId());
							if (pei.getGpsItems() == null) {
								pei.setGpsItems(gpsid);
							} else {
								pei.getGpsItems().add(g.getId());
							}
						}

					}
				}
			}else if(rs.getData().getGpsId() != null){
				List<Integer> gpsid = new ArrayList<Integer>();
				gpsid.add(rs.getData().getGpsId());
				if (pei.getGpsItems() == null) {
					pei.setGpsItems(gpsid);
				} else {
					pei.getGpsItems().add(rs.getData().getGpsId());
				}
			}
			if (rs.getPoliceItems() != null) {
				if (rs.getPoliceItems().size() > 0) {
					for (int i = 0; i < rs.getPoliceItems().size(); i++) {
						PoliceEInfo ep = rs.getPoliceItems().get(i);
						List<Integer> pid = new ArrayList<Integer>();
						if (ep.getData() != null) {
							Police g = (Police) ep.getData();
							pid.add(g.getId());
							if (pei.getPoliceItems() == null) {
								pei.setPoliceItems(pid);
							} else {
								pei.getGpsItems().add(g.getId());
							}
						}
					}
				}

			}
			pets.add(pei);
		}
		return pets;
	}

	private VehicleInfo createVehicleInfo(ExtDbResult result) {
		VehicleInfo item = new VehicleInfo();
		Vehicle data = this.createVehicle(result);
		item.setDutyItemId(result.getDutyItemId());
		item.setData(data);
		item.setItemTypeId(result.getItemTypeId());
		// item.setLevel(result.getLevel());

		return item;
	}

	private GpsInfo createGpsInfo(ExtDbResult result) {
		GpsInfo item = new GpsInfo();
		Gps data = this.createGsp(result);
		item.setDutyItemId(result.getDutyItemId());
		item.setData(data);
		item.setItemTypeId(result.getItemTypeId());
		// item.setLevel(result.getLevel());

		return item;
	}

	private ExtShiftInfo createShiftInfo(ExtDbResult result) {
		ExtShiftInfo info = new ExtShiftInfo();
		info.setBeginTime(result.getBeginTime());
		info.setDutyTypeId(result.getDutyTypeId());
		info.setDutyTypeName(result.getDutyTypeName());
		info.setEndTime(result.getEndTime());
		info.setShiftId(result.getDutyItemId());
		info.setShiftName(result.getName());

		return info;
	}

	private Vehicle createVehicle(ExtDbResult result) {
		Vehicle v = new Vehicle();
		v.setBrand(result.getVehicleBrand());
		v.setGpsId(result.getVehicleGpsId());
		v.setGpsName(result.getVehicleGpsName());
		v.setId(result.getItemId());
		v.setIntercomGroup(result.getVehicleIntercomGroup());
		v.setIntercomPerson(result.getVehicleIntercomPerson());
		v.setNumber(result.getVehicleNumber());
		v.setOrgId(result.getVehicleOrgId());
		v.setSiteQty(result.getVehicleSiteQty());
		v.setVehicleTypeId(result.getVehicleTypeId());
		v.setPurposeId(result.getPurposeId());
		return v;

	}

	private Police createPolice(ExtDbResult result) {
		Police p = new Police();
		p.setGpsId(result.getPoliceGpsId());
		p.setGpsName(result.getPoliceGpsName());
		p.setId(result.getPoliceId());
		p.setIdcardno(result.getPoliceIdcardno());
		p.setIntercomGroup(result.getPoliceIntercomGroup());
		p.setIntercomPerson(result.getPoliceIntercomPerson());
		p.setMobile(result.getPoliceMobile());
		p.setMobileShort(result.getPoliceMobileShort());
		p.setName(result.getPoliceName());
		p.setNumber(result.getPoliceNumber());
		p.setOrgId(result.getOrgId());
		p.setTitleId(result.getTitleId());
		return p;
	}

	private PoliceEInfo createPoliceEInfo(ExtDbResult result) {
		PoliceEInfo item = new PoliceEInfo();
		Police data = this.createPolice(result);
		item.setDutyItemId(result.getDutyItemId());
		item.setData(data);
		item.setItemTypeId(result.getItemTypeId());
		// item.setLevel(result.getLevel());

		return item;
	}

	private Gps createGsp(ExtDbResult result) {
		Gps g = new Gps();
		g.setGpsName(result.getGpsName());
		g.setIconUrl(result.getGpsIconUrl());
		g.setId(result.getGpsId());
		g.setNumber(result.getGpsNumber());
		g.setOrgId(result.getGpsOrgId());
		g.setTypeId(result.getGpsTypeId());
		return g;
	}

	@Override
	public List<GpsBaseVM> selectGpsId(int orgId) {
		// TODO Auto-generated method stub
		return vehicleMapper.selectGpsId(orgId);
	}

	@Override
	public List<VehicleVM> findByNumberAndId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return vehicleMapper.findByNumberAndId(map);
	}

	@Override
	public List<VehicleVM> loadVMListWithGroupList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return vehicleMapper.loadVMListWithGroupList(map);
	}


	@Override
	public Integer addMemberToGroup(int vehicleId, Integer groupId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vehicleId", vehicleId);
		map.put("groupId", groupId);
		return vehicleMapper.addMemberToGroup(map);		
	}

	@Override
	public List<GroupMemberVM> findByGroupIDAndVehicleID(int vehicleId,
			Integer groupId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vehicleId", vehicleId);
		map.put("groupId", groupId);
		return vehicleMapper.findByGroupIDAndVehicleID(map);		
	}
}
