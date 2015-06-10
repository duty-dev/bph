package com.tianyi.bph.rest.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.domain.system.GBDevice;
import com.tianyi.bph.domain.system.OrganGBOrganBean;
import com.tianyi.bph.domain.system.OrganGbOrgan;
import com.tianyi.bph.service.basicdata.GpsService;
import com.tianyi.bph.service.system.GBPlatFormService;

/**
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/client/GBPlatForm")
public class GBPlatFormAction {

	@Resource
	private GBPlatFormService platFormService;

	@Resource
	private GpsService gps;

	/**
	 * 获取机构授权的国标设备
	 * 
	 * @param organId
	 * @return
	 */
	@RequestMapping(value = "/getGBDeviceListByOrganId.do")
	@ResponseBody
	public ReturnResult getGBDeviceListByOrganId(
			@RequestParam(value = "organId", required = false) Integer organId) {
		try {
			List<GBDevice> list = null;
			if (organId != null) {
				list = platFormService.getGBDeviceListByOrganId(organId);
			} else {
				list = platFormService.queryAllGbDevice();
			}
			return ReturnResult.SUCCESS(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}

	/**
	 * 获取机构授权的国标设备
	 * 
	 * @param organId
	 * @return
	 */
	@RequestMapping(value = "/getOrganRelationGBOrgan.do")
	@ResponseBody
	public ReturnResult getOrganRelationGBOrgan(
			@RequestParam(value = "organId", required = true) Integer organId) {
		try {
			OrganGbOrgan organGbOrgan = null;
			List<OrganGBOrganBean> result = null;
			if (organId != null) {
				organGbOrgan = platFormService.queryOrganGBOrganKey(organId);
			}
			if (organGbOrgan != null) {
				result = new ArrayList<OrganGBOrganBean>();
				process(result, organGbOrgan);

			}
			return ReturnResult.SUCCESS(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}


	private void process(List<OrganGBOrganBean> result,
			OrganGbOrgan organGbOrgan) {
		if (organGbOrgan != null) {
			OrganGBOrganBean bean = new OrganGBOrganBean();
			bean.setOrganId(organGbOrgan.getOrganId());
			bean.setGbOrganIds(new ArrayList<Integer>(organGbOrgan
					.getGbOrganIds()));
			result.add(bean);
			if (organGbOrgan.getItems() != null) {
				for (OrganGbOrgan child : organGbOrgan.getItems()) {
					process(result, child);
				}
			}
		}

	}
}
