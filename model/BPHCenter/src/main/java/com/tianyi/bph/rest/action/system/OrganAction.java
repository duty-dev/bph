package com.tianyi.bph.rest.action.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.CacheManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.system.OrgPcCgyService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.service.system.OrganTypeService;
import com.tianyi.bph.web.cache.UserCache;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.common.ehcache.CacheUtils;

@Controller
@RequestMapping("/organ")
public class OrganAction {

	static final Logger log = LoggerFactory.getLogger(OrganAction.class);

	@Autowired OrganService organService;
	@Autowired OrganTypeService organTypeService;
	@Autowired OrgPcCgyService orgPcCgyService;
	private CacheManager manager;

	@Autowired
	public void setManager(@Qualifier("ehCacheManager") CacheManager manager) {
		this.manager = manager;
	}

	/**
	 * 添加机构
	 * @param organ @
	 * @
	 * @return
	 */
	@RequestMapping(value = "/addOrgan.do")
	@ResponseBody
	public ReturnResult addOrgan(Organ organ) {
		
		//验证机构名和编码是否重复
		OrganQuery organQuery = new OrganQuery();
		organQuery.setCode(organ.getCode());
		organQuery.setName(organ.getName());
		/*boolean isUnique = organService.isUnique(organQuery);
		if (!isUnique) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "机构名或机构代码重复");
		}*/
		//int id = organService.addOrgan(organ);

		return ReturnResult.SUCCESS("成功");
	}
	
	
	/**
	 * 加载机构数
	 * @param name
	 * @param code
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/tree.do","/tree.action"})
	@ResponseBody
	public ReturnResult getOrganTree(
			@RequestParam(value="organId",required=false)String organId,
			HttpServletRequest request){
		try{
			User user=(User) request.getAttribute("User");
			
			Organ o=(Organ) CacheUtils.getObjectValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"获取成功",o);
		}catch(Exception e){
			log.debug(e.getMessage());
			return ReturnResult.FAILUER(MessageCode.GET_TREE_FAIL);
		}
	}
	
	
	/**
	 * 删除机构
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteOrgan.do")
	@ResponseBody
	public ReturnResult deleteOrgan(
			@RequestParam(value = "id", required =true) Integer id) {
		
		/*boolean hasChild = organService.hasChild(id);
		if (hasChild) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_HASCHILD, "该机构有子节点不能删除");
		}*/
		organService.deleteOrgan(id);

		return ReturnResult.SUCCESS();
	}
	
	/**
	 * 修改机构信息
	 * @param organ
	 * @return
	 */
	@RequestMapping(value = "/modifyOrgan.do")
	@ResponseBody
	public ReturnResult modifyOrgan(Organ organ) {
		
		//验证机构名和编码是否重复
		OrganQuery organQuery = new OrganQuery();
		organQuery.setCode(organ.getCode());
		organQuery.setName(organ.getName());
		organQuery.setId(organ.getId());
		/*boolean isUnique = organService.isUnique(organQuery);
		if (!isUnique) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "机构名或机构代码重复");
		}*/
				
		organService.updateOrgan(organ);

		return ReturnResult.SUCCESS();
	}
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryOrganDetail.do")
	@ResponseBody
	public ReturnResult queryOrganDetail(
			@RequestParam(value = "id", required =true) Integer id) {
		
		Organ organ = organService.getOrganById(id);
		
		return ReturnResult.SUCCESS(organ);
	}
	/**
	 * 分页查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryOrganPageList.do")
	@ResponseBody
	public ReturnResult queryOrganPageList(OrganQuery query) {
		
		Pager<Organ> page = organService.getPageList(query);
		return ReturnResult.SUCCESS(page);
	}
	
	/**
	 * 条件查询
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/queryOrganList.do")
	@ResponseBody
	public ReturnResult queryOrganList(OrganQuery query) {
		
		List<Organ> organs = organService.getQueryList(query);
		return ReturnResult.SUCCESS(organs);
	}
	
	/**
	 * 修改状态
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/modifyOrganState.do")
	@ResponseBody
	public ReturnResult modifyOrganState(@RequestParam(value = "organId", required =true)Integer organId,
			@RequestParam(value = "state", required =true)Integer state) {
		
		Organ organ = new Organ();
		organ.setId(organId);
		organ.setState(state);
		organService.updateOrganByMySelect(organ);
		return ReturnResult.SUCCESS();
	}

}
