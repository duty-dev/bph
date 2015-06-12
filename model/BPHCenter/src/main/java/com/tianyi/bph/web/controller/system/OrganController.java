package com.tianyi.bph.web.controller.system;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.CacheManager;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianyi.bph.BaseLogController;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.PageReturn;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.common.ehcache.CacheUtils;
import com.tianyi.bph.domain.system.GBOrgan;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.system.LogService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.service.system.UserOtherOrganService;
import com.tianyi.bph.web.cache.UserCache;

/**
 * web 机构管理
 */
@Controller
@RequestMapping("/web/organx")
public class OrganController extends BaseLogController{
	private static final Logger logger =LoggerFactory.getLogger(OrganController.class);
	
	@Autowired private OrganService organService;
	@Autowired UserOtherOrganService userOtherService;
	@Autowired LogService logService;
	private CacheManager manager;

	@Autowired
	public void setManager(@Qualifier("ehCacheManager") CacheManager manager) {
		this.manager = manager;
	}
	
	/**
	 *  web 机构列表展示
	 * @param name
	 * @param code
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/gotoOrganList.do","/gotoOrganList.action"})
	@ResponseBody
	public ModelAndView gotoOrganList(
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="code",required=false)String code,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request
			){
		ModelAndView  mv=new ModelAndView("/base/organ/organList.jsp");
		/*User user=(User) request.getAttribute("User");
		OrganQuery organQuery=new OrganQuery();
		if(!StringUtils.isEmpty(name)){organQuery.setName(name);}
		organQuery.setPageNo(pageNo);
		organQuery.setPageSize(pageSize);
		organQuery.setPath(user.getOrganPath());
		Pager<Organ> organList=organService.getPageList(organQuery);
		
		mv.addObject("pager", organList);*/
		mv.addObject("num",SystemConfig.SYSTEM_MANAGER);//系统管理模块100
		return mv;
	}
	
	/**
	 * 通过ajax 获取机构信息
	 * @param name
	 * @param code
	 * @param params
	 * @param pageSize
	 * @param pageNo
	 * @param request
	 * @return
	 */
	@RequestMapping({"/getOrganList.do","/getOrganList.action"})
	@ResponseBody
	public PageReturn getOrganList(
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="organId",required=false)Integer organId,
			@RequestParam(value="organPath",required=false)String organPath,
			@RequestParam(value="expandeds",required=false)String expandeds,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="organLevel",required=false)String organLevel,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request
			){
		OrganQuery organQuery=new OrganQuery();
		User user=(User) request.getAttribute("User");
		if(!StringUtils.isEmpty(name)){organQuery.setName(name);}
		organQuery.setOrganLevel(organLevel);
		if(organId != null){
			organQuery.setId(organId);
		}else{
			organQuery.setId(user.getOrgId());
		}
		if(!StringUtils.isEmpty(organPath)){
			organQuery.setPath(organPath);
		}
		else{
			organQuery.setPath(user.getOrganPath());
		}
		int totalRows = 0;
		organQuery.setPageNo(pageNo);
		organQuery.setPageSize(pageSize);
		Pager<Organ> organList =organService.getPageList(organQuery);
		totalRows=organList.getTotalRows();
		return PageReturn.MESSAGE(MessageCode.STATUS_SUCESS,MessageCode.SELECT_ORGAN_SUCCESS,totalRows,organList);
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
	public ReturnResult tree(
			@RequestParam(value="searchName",required=false)String searchName,
			@RequestParam(value="organId",required=false)String organId,
			@RequestParam(value="expandeds",required=false)String expandeds,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request){
		try{
			
			User user=(User) request.getAttribute("User");
			OrganQuery organQuery=new OrganQuery();
			if(!StringUtils.isEmpty(searchName)){organQuery.setName(searchName);}
			organQuery.setUserId(user.getUserId());
			organQuery.setPath(user.getOrganPath());
			if(!StringUtils.isEmpty(organId)){
				organQuery.setId(Integer.parseInt(organId));
			}else{
				organQuery.setId(user.getOrgId());
			}
			organQuery.setPageNo(pageNo);
			organQuery.setPageSize(pageSize);
			
			if(!StringUtils.isEmpty(expandeds)){
				organQuery.setExpandeds(expandeds);
			}
			Organ o=organService.getOrganTree(organQuery,SystemConfig.DATABASE);
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"获取成功",o);
		}catch(Exception e){
			e.getStackTrace();
			logger.debug(e.getMessage());
			return ReturnResult.FAILUER(MessageCode.GET_TREE_FAIL);
		}
	}
	
	@RequestMapping("/searchOrganListByName.do")
	@ResponseBody
	public ReturnResult searchOrganListByName(HttpServletRequest request) {
		try {
			String searchOrganName=(String) request.getAttribute("searchOrganName");
			String organId=(String) request.getAttribute("organId");
			User user=(User) request.getAttribute("User");
			OrganQuery query=new OrganQuery();
			if(!StringUtils.isEmpty(organId)){
				query.setId(Integer.parseInt(organId));
			}
			query.setName(searchOrganName);
			Organ organ = (Organ) CacheUtils.getObjectValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			return ReturnResult.SUCCESS(organ);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnResult.FAILUER(e.getMessage());
		}
	}
	

	/*private void process(final List<Organ> list, String name) {
		Iterator<Organ> it = list.listIterator();
		while (it.hasNext()) {
			Organ organ = it.next();
			if (organ.getItems() != null) {
				process(organ.getItems(), name);
			}
			if (!organ.getName().contains(name)
					&& (organ.getItems() == null || organ.getItems().size() == 0)) {
				it.remove();
			}
		}
	}*/
	
	/**
	 * 分级展示机构树
	 * @param organId
	 * @param hybrid_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lazyOrganList.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String lazyOrganList(
			@RequestParam(value = "id", required = false) Integer hybrid_id,
			HttpServletRequest request) {
		List<Organ> list = null;
		try {
			User user=(User) request.getAttribute("User");
			String expandeds=(String) request.getAttribute("expandeds");
			String organId=(String) request.getAttribute("organId");
			OrganQuery query=new OrganQuery();
			query.setId(user.getOrgId());
			query.setParentId(hybrid_id);
			query.setExpandeds(expandeds);
			if(!StringUtils.isEmpty(organId)){
				query.setCurrentOrganId(Integer.parseInt(organId));
			}else{
				query.setCurrentOrganId(user.getOrgId());
			}
			list=organService.getOrganListByParentId(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String s =  JSONArray.fromObject(list).toString();
		return s;
	}
	
	@RequestMapping(value = "/lazyOrganList1.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String lazyOrganList1(
			@RequestParam(value = "id", required = false) Integer hybrid_id,
			HttpServletRequest request) {
		List<Organ> list = null;
		try {
			User user=(User) request.getAttribute("User");
			String expandeds=(String) request.getAttribute("expandeds");
			String organId=(String) request.getAttribute("organId");
			OrganQuery query=new OrganQuery();
			query.setId(user.getOrgId());
			query.setParentId(hybrid_id);
			query.setExpandeds(expandeds);
			if(!StringUtils.isEmpty(organId)){
				query.setCurrentOrganId(Integer.parseInt(organId));
			}else{
				query.setCurrentOrganId(user.getOrgId());
			}
			list=organService.getOrganListByParentId1(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String s =  JSONArray.fromObject(list).toString();
		return s;
	}
	
	/**
	 * 跨机构树选择
	 * @param name
	 * @param code
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@RequestMapping({"/jumpTree.do","/jumpTree.action"})
	@ResponseBody
	public ReturnResult jumpTree(
			@RequestParam(value="userId",required=true)String userId,
			@RequestParam(value="organId",required=true)String organId,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request){
		try{
			OrganQuery organQuery=new OrganQuery();
			if(!StringUtils.isEmpty(organId)){
				organQuery.setUserId(new Long(userId));
			}
			organQuery.setId(Integer.parseInt(organId));
			organQuery.setPageNo(pageNo);
			organQuery.setPageSize(pageSize);
			
			Organ o=organService.getJumpTree(organQuery,SystemConfig.DATABASE);
			
			return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"获取成功",o);
		}catch(Exception e){
			logger.debug(e.getMessage());
			return ReturnResult.FAILUER(MessageCode.GET_TREE_FAIL);
		}
	} 
	
	/**
	 * @ goto添加机构页面
	 * @return
	 */
	@RequestMapping({"/gotoAddOrgan.do","/gotoAddOrgan.action"})
	@ResponseBody
	public ModelAndView gotoAddOrgan(
			@RequestParam(value="organId",required=false) String organId,
			@RequestParam(value="sessionId",required=false) String sessionId,
			HttpServletRequest request
			){
		ModelAndView  mv=new ModelAndView("/base/organ/organAdd.jsp");
		User user=null;
		if(!StringUtils.isEmpty(sessionId)){
			user=UserCache.instance.getUser(sessionId);
		}else{
			 user=UserCache.instance.getUser(request.getSession());
		}
		Organ organ=null;
		if(StringUtils.isEmpty(organId)){
			 organ = organService.getOrganByPrimaryKey(user.getOrgId());
		}else{
			organ = organService.getOrganByPrimaryKey(Integer.parseInt(organId));
		}
		mv.addObject("organ", organ);
		return mv;
	}
	/**
	 * 添加机构
	 * @param name
	 * @param shortName
	 * @param code
	 * @param parentId
	 * @param orgPcCgyCode
	 * @param orgTypeCode
	 * @return
	 */
	@RequestMapping({"/addOrgan.do","/addOrgan.action"})
	@ResponseBody
	public ReturnResult addOrgan(
			@RequestParam(value="name",required=true)String name,
			@RequestParam(value="code",required=true)String code,
			@RequestParam(value="shortName",required=true)String shortName,
			@RequestParam(value="parentId",required=true)String parentId,
			@RequestParam(value="orgTypeCode",required=true)String orgTypeCode,
			@RequestParam(value="note",required=false)String note,
			HttpServletRequest request
			){
		try{
			Organ organ=new Organ();
			organ.setName(name);
			organ.setCode(code);
			organ.setParentId(Integer.parseInt(parentId));
			organ.setOrgTypeCode(orgTypeCode);
			organ.setShortName(shortName);
			organ.setCreateTime(new Date());
			organ.setUpdateTime(new Date());
			organ.setNote(note);
			organ=organService.addOrgan(organ);
			if(organ==null){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,MessageCode.ADD_ORGAN_FAIL_BYCODENAME);
			}
			addLog(request, "添加机构成功",2);
			
			User user=(User) request.getAttribute("User");
			OrganQuery query=new OrganQuery();
			query.setPath(user.getOrganPath());
			query.setId(user.getOrgId());
			CacheUtils.invalidateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			CacheUtils.updateValue(manager, CacheUtils.ORGAN_DATASOURCE,
					user.getUserId()+"", organService.getOrganTree(query, SystemConfig.DATABASE));
		}catch(Exception e){
			logger.debug(e.getMessage());
			return ReturnResult.FAILUER(MessageCode.ADD_ORGAN_FAIL);
		}
		return ReturnResult.SUCCESS(MessageCode.ADD_ORGAN_SUCCESS);
	}
	
	/**
	 *  根据ID查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping({"/queryOrganDetail.do","/queryOrganDetail.action"})
	@ResponseBody
	public ModelAndView queryOrganDetail(
			@RequestParam(value = "organId", required =true) String organId) {
		ModelAndView  mv=new ModelAndView("/base/organ/organEdit.jsp");
		Organ organ=null;
		if(organId.equals("1")){
			organ=organService.getOrganByPrimaryKey(Integer.parseInt(organId));
		}else{
			organ = organService.getOrganById(Integer.parseInt(organId));
		}
		mv.addObject("organ",organ);
		return mv;
	}
	
	/**
	 * 编辑机构
	 * @param organId
	 * @param name
	 * @param shortName
	 * @param code
	 * @param parentId
	 * @param orgTypeCode
	 * @param orgPcCgyCode
	 * @return
	 */
	@RequestMapping({"/modifyOrgan.do","/modifyOrgan.action"})
	@ResponseBody
	public ReturnResult modifyOrgan(
			@RequestParam(value="organId",required=true)Integer organId,
			@RequestParam(value="code",required=true)String code,
			@RequestParam(value="name",required=true)String name,
			@RequestParam(value="shortName",required=true)String shortName,
			@RequestParam(value="parentId",required=true)Integer parentId,
			@RequestParam(value="orgTypeCode",required=true)String orgTypeCode,
			@RequestParam(value="note",required=false)String note,
			HttpServletRequest request
			) {
		try{
			Organ organ=new Organ();
			organ.setName(name);
			organ.setCode(code);
			organ.setParentId(parentId);
			organ.setShortName(shortName);
			organ.setUpdateTime(new Date());
			organ.setOrgTypeCode(orgTypeCode);
			organ.setId(organId);
			organ.setNote(note);
			organ=organService.updateOrgan(organ);
			if(organ==null){
				return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,"修改失败，机构名已存在");
			}
			addLog(request, "修改机构成功",2);
			
			User user=(User) request.getAttribute("User");
			OrganQuery query=new OrganQuery();
			query.setId(user.getOrgId());
			query.setPath(user.getOrganPath());
			CacheUtils.invalidateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			CacheUtils.updateValue(manager, CacheUtils.ORGAN_DATASOURCE,
					user.getUserId()+"", organService.getOrganTree(query, SystemConfig.DATABASE));
			
		}catch(Exception e){
			logger.debug(e.getMessage());
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,MessageCode.UPDATE_ORGAN_FAIL);
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS, MessageCode.UPDATE_ORGAN_SUCCESS);
	
	}
	
	/**
	 * 根据id删除机构
	 * @param organId
	 * @return
	 */
	@RequestMapping({"/deleteOrgan.do","/deleteOrgan.action"})
	@ResponseBody
	public ReturnResult deleteOrgan(
			@RequestParam(value = "organId", required =true) Integer organId,
			HttpServletRequest request){
		try{
			int i=organService.deleteOrgan(organId);
			if(i==0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_HASCHILD,MessageCode.DELETE_ORGAN_FAIL);
			}
			addLog(request, "删除机构成功",2);
			
			User user=(User) request.getAttribute("User");
			OrganQuery query=new OrganQuery();
			query.setPath(user.getOrganPath());
			query.setId(user.getOrgId());
			CacheUtils.invalidateValue(manager, CacheUtils.ORGAN_DATASOURCE, user.getUserId()+"");
			CacheUtils.updateValue(manager, CacheUtils.ORGAN_DATASOURCE,
					user.getUserId()+"", organService.getOrganTree(query, SystemConfig.DATABASE));
		}catch(Exception e){
			logger.debug(e.getMessage());
			return ReturnResult.MESSAGE(MessageCode.STATUS_FAIL,MessageCode.DELETE_ORGAN_FAIL);
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,MessageCode.DELETE_ORGAN_SUCCESS);
	}
	/**
	 * 根据用户id来获取用户包含的跨机构ids，然后将跨机构添加到原先的树后面
	 * 1、如果当前机构和跨机构是平行机构，则直接在后面添加机构数据
	 * 2、如果跨机构是当前机构的上级，则不必再当前树添加接待呢，则直接改变原先的树。通过他的上级来显示机构树
	 * 3、如果跨机构时当前机构的下级。（可能性极少），则不用在当前树添加节点，直接用生成好的树
	 * @param name
	 * @param pageSize
	 * @param pageNo
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="/addOrganTreeElement.do")
	@ResponseBody
	public ReturnResult testOrganTree(
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="sessionId",required=false)String sessionId,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNo",required=false,defaultValue="1")Integer pageNo,
			HttpServletRequest request){
		User user=null;
		if(!StringUtils.isEmpty(sessionId)){
			HttpSession session=UserCache.getSession(sessionId);
			user=(User) session.getAttribute("User");
		}else{
			user=UserCache.instance.getUser(request.getSession());
		}
		OrganQuery organQuery=new OrganQuery();
		if(name !=null && !StringUtils.isEmpty(name)){
			organQuery.setName(name);
		}
		organQuery.setPageNo(pageNo);
		organQuery.setPageSize(pageSize);
		List<Organ> organStrList=new ArrayList<Organ>();//这是声明一个总的List<Organ> 把节点对象返回给jsp页面
		
		Organ organVo=organService.getOrganByPrimaryKey(user.getOrgId());
		List<String> childList=organService.getStringByPath(organVo.getPath());//这是获取当前机构的子机构集合
		
		List<String> nameList=new ArrayList<String>();//存储通过Name查询获取的机构Ids
		List<String> ssList=new ArrayList<String>();//总包括id的list
		//这里是当跨机构中包含当前机构的上级。所取得上级下面的所有机构ids集合
		if(!StringUtils.isEmpty(organQuery.getName())){
			List<Organ> list=organService.getOrgansByName(organQuery);//获取Name模糊查询出来的数据
			for (Organ organ : list) {
				nameList.add(organ.getId()+"");
			}
		}
		List<String> parentList=OrganServiceImpl.parentMap.get("parentList");
		List<String> strList=userOtherService.getOrganIdByUserId(user.getUserId());//获取当前用户包含的跨机构集合
		for (String string : strList) {
			//看看是否包含在当前机构树的父机构中，如果是 则直接跳过
			if(parentList==null  || !parentList.contains(string)){
				if(!childList.contains(string)){//看看是否包含在当前机构的子机构中。同上
					boolean flag=false;
					//查找机构的子机构集合
					Organ organ=organService.getOrganByPrimaryKey(Integer.parseInt(string));
					List<String> oneList=organService.getStringByPath(organ.getPath());
					if(!ssList.contains(string)){
						//这个地方处理的是当{1,2}  当跨机构集合中第2个是1的子机构时，则没必要再去请求一次，直接跳过
						for (String string2 : oneList) {//将查找出来的添加到总list中
							if(nameList.contains(string2) || nameList.size()==0){
								flag=true;
							}
							ssList.add(string2);
						}
						if(flag==true){
							organQuery.setId(Integer.parseInt(string));
							Organ o=organService.getOrganTree(organQuery,SystemConfig.DATABASE);
							organStrList.add(o);
						}
					}
				}
			}
		}
		return ReturnResult.MESSAGE(MessageCode.STATUS_SUCESS,"成功",organStrList);
	}*/
}
