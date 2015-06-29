package com.megaeyes.drs.webService.server;

import java.io.FileNotFoundException;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import com.megaeyes.drs.bean.CjdbBean;
import com.megaeyes.drs.bean.CjryBean;
import com.megaeyes.drs.bean.Jjdb110Bean;
import com.megaeyes.drs.bean.XlzBean;
import com.megaeyes.drs.domain.PatrolGroup;
import com.megaeyes.drs.domain.PoliceUser;

/**
 * 三台合一对接服务
 * 
 * @author gm
 * 
 */
@WebService
public interface ExternalManageService {

	/**
	 * 推送接警信息
	 * 
	 * @param jjdb110Bean
	 * @return
	 * @author gm
	 */
	public boolean pushCurJjdb110(
			@WebParam(name = "jjdb110Bean") Jjdb110Bean jjdb110Bean);

	/**
	 * 推送出警信息
	 * 
	 * @param cjdbBean
	 * @return
	 * @author gm
	 */
	public boolean pushCurCjdb(@WebParam(name = "cjdbBean") CjdbBean cjdbBean);

	/**
	 * 推送警情状态
	 * 
	 * @param jjdbh
	 * @param cjdbh
	 * @param zt
	 * @return
	 * @author gm
	 */
	@Deprecated
	public boolean pushCurState(@WebParam(name = "jjdbh") String jjdbh,
			@WebParam(name = "cjdbh") String cjdbh,
			@WebParam(name = "zt") Integer zt);

	/**
	 * 推送出警人员
	 * 
	 * @param cjrybean
	 * @return
	 */
	public boolean pushCurCjry(@WebParam(name = "cjrybean") CjryBean cjrybean);

	/**
	 * 推送巡逻组
	 * 
	 * @param xlzbean
	 * @return
	 */
	public boolean pushCurXlz(@WebParam(name = "xlzbean") XlzBean xlzbean);

	/**
	 * 删除处警信息
	 * 
	 * @param cjdbBean
	 * @return
	 */
	public boolean delCjdb(@WebParam(name = "cjdbBean") CjdbBean cjdbBean);

	/**
	 * 通过区域id获取巡逻组信息
	 * 
	 * @param platformId
	 * @return
	 * @author gm
	 */
	@Deprecated
	public PatrolGroup getPatrolGroupByRegionId(
			@WebParam(name = "regionId") String regionId);

	/**
	 * 根据机构编号获取当前警员列表
	 * 
	 * @param platformId
	 * @param organCodep
	 * @return
	 * @author gm
	 */
	public List<PoliceUser> findUsersByOrganCode(
			@WebParam(name = "platformId") String platformId,
			@WebParam(name = "organCode") String organCode);

	@Deprecated
	public List<PatrolGroup> findPatrolGroupByOrganCode(
			@WebParam(name = "platformId") String platformId,
			@WebParam(name = "organCode") String organCode);

	@WebMethod
	public void upload(
			@WebParam(name = "dataHandler") @XmlMimeType("*/*") DataHandler dataHandler);

	@WebResult
	@XmlMimeType("*/*")
	public DataHandler downLoad(@WebParam(name = "fileName") String fileName)
			throws FileNotFoundException;

}
