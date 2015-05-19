package com.tianyi.bph.query.basicdata;

import java.util.List;

import com.tianyi.bph.domain.basicdata.Police;
 

public class PoliceInfo  extends ItemInfo<PoliceJJVM> {
	
	private  List<GpsInfo> gpsItems;
	
	private  List<WeaponInfo> weaponItems;
	
	private String dutyTypeName;

	public String getDutyTypeName() {
		return dutyTypeName;
	}

	public void setDutyTypeName(String dutyTypeName) {
		this.dutyTypeName = dutyTypeName;
	}

	public List<GpsInfo> getGpsItems() {
		return gpsItems;
	}

	public void setGpsItems(List<GpsInfo> gpsItems) {
		this.gpsItems = gpsItems;
	}

	public List<WeaponInfo> getWeaponItems() {
		return weaponItems;
	}

	public void setWeaponItems(List<WeaponInfo> weaponItems) {
		this.weaponItems = weaponItems;
	}
	 
 
}
