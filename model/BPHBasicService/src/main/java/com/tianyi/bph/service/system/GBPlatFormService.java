package com.tianyi.bph.service.system;

import java.util.List;

import com.tianyi.bph.domain.system.GBDevice;
import com.tianyi.bph.domain.system.GBOrgan;
import com.tianyi.bph.domain.system.OrganGBOrganKey;
import com.tianyi.bph.domain.system.OrganGbOrgan;

/**
 * 国标平台业务层
 * 
 * @author Administrator
 * 
 */
public interface GBPlatFormService {

	/**
	 * 根据 平台机构获取 国标机构数
	 * 
	 * @param organId
	 * @return
	 */
	public GBOrgan getOrganTree(int organId);

	public void addGBPermission(Integer organId, List<Integer> list);

	public List<GBDevice> getGBDeviceListByOrganId(Integer organId);

	public OrganGbOrgan queryOrganGBOrganKey(Integer organId);

	public List<GBDevice> queryAllGbDevice();

	public GBDevice getGBDeviceById(Integer gbDeviceId);

	List<GBOrgan> loadGbOrgan(Integer organId,Integer parentId);
	
	public List<GBDevice> getGBDeviceListByGBOrganId(Integer gbOrganId);

}
