package com.tianyi.bph.service.impl.system;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.Md5Encrypt;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.system.UserDAO;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.system.UserQuery;
import com.tianyi.bph.service.system.UserService;

/**
 * 用户管理业务层接口
 * @author fantedan@tieserv.com
 * @date 2015-1-13 上午10:44:50
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired UserDAO userDao;
	
	@Autowired UserRoleServiceImpl userRoleService;
	
	@Autowired UserAddModuleServiceImpl userAddModuleService;
	@Autowired UserOtherOrganServiceImpl userOtherOrganService;
	
	/**
	 * 添加用户信息
	 * @param user 用户信息
	 * @param rolesId 角色ID
	 * @param modulesId 模块ID
	 * @return
	 */
	@MQDataInterceptor(type = Constants.MQ_TYPE_USER, operate = Constants.MQ_OPERATE_ADD)
	@Transactional
	public ReturnResult addUser(User user, Integer[] rolesId, Integer[] userOrganIds){
		//验证用户名重复
		UserQuery userQuery = new UserQuery();
		userQuery.setLoginName((user.getLoginName()));
		boolean isUnique = isUnique(userQuery);
		if (!isUnique) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "用户名重复");
		}
		if(user.getPoliceUserId()!=null){
			List uList = userDao.getListForPoliceId(user.getPoliceUserId());
			if(uList.size()>0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "该警员已绑定其它帐号");
			}
		}		
		user.setPassword(Md5Encrypt.md5(user.getPassword()));
		user.setCreateTime(new Date());
		//保存用户
		userDao.insert(user);
		Long userId = user.getUserId();
		System.out.println("*****userId="+userId);
		//保存用户角色
		userRoleService.allotUserRoles((long)userId, rolesId);
		//保存用户跨机构
		//userAddModuleService.allotUserModules((long)userId, modulesId);
		userOtherOrganService.allotUserOtherOrgans(userId, userOrganIds);
		return ReturnResult.SUCCESS("用户添加成功", userId);
	}
	
	/**
	 * 删除用户
	 * @param 用户id
	 * @return
	 */
	@MQDataInterceptor(type = Constants.MQ_TYPE_USER, operate = Constants.MQ_OPERATE_DELETE)
	@Transactional
	public ReturnResult deleteUser(Long userId){
		User user = userDao.selectByPrimaryKey(userId);
//		if (null != user.getLoginTime()) {
//			return ReturnResult.FAILUER("对不起，该账号已经生效，请勿删除！");
//		}
		userRoleService.deleteByUserKey(userId);
		userAddModuleService.deleteByUserKey(userId);
		userDao.deleteByPrimaryKey(userId);
		return ReturnResult.SUCCESS();
	}
	/**
	 * 批量删除多个用户,此方法需要修改,for循环效率太低
	 * @param userIds 用户ID号
	 * @return
	 */
	@Transactional
	public ReturnResult deleteUsers(Long[] ids){
		for (Long id : ids) {
			userDao.deleteByPrimaryKey(id);
		}
		return ReturnResult.SUCCESS();
	}
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 * @param rolesId 角色ID
	 * @param modulesId 模块ID
	 */
	@MQDataInterceptor(type = Constants.MQ_TYPE_USER, operate = Constants.MQ_OPERATE_UPDATE)
	@Transactional
	public ReturnResult updateUser(User user, Integer[] rolesId, Integer[] userOrganIds){
		if (null == user.getUserId()) {
			return ReturnResult.FAILUER("更新对象ID不能为空！");
		}		
		//验证用户名重复
		UserQuery userQuery = new UserQuery();
		userQuery.setUserId(user.getUserId());
		userQuery.setLoginName(user.getLoginName());		
		boolean isUnique = isUnique(userQuery);
		if (!isUnique) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "用户名重复");
		}
		if(user.getPoliceUserId()!=null){
			UserQuery query = new UserQuery();
			query.setUserId(user.getUserId());
			query.setPoliceUserId(user.getPoliceUserId());
			Integer uCount = userDao.getListForPoliceIdAndUserId(query);
			if(uCount>0){
				return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "该警员已绑定其它帐号");
			}
		}
		user.setUpdateTime(new Date());
		Long userId = user.getUserId();
		userDao.updateByPrimaryKeySelective(user);
		//保存用户角色
		userRoleService.allotUserRoles(user.getUserId(), rolesId);
		//保存用户模块
		//userAddModuleService.allotUserModules(user.getUserId(), modulesId);
		//保存用户跨机构分配信息
		userOtherOrganService.allotUserOtherOrgans(userId, userOrganIds);
		return ReturnResult.SUCCESS();
	}
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 */
	@Transactional
	public void updateUserBySelect(User user){
		userDao.updateByPrimaryKeySelective(user);		
	}
	
	/**
	 * 根据ID获取用户信息
	 * @param id 用户ID
	 */	
	public User getUserById(Long id){
		User user = new User();
		user = userDao.selectByPrimaryKey(id);
		String userOtherOrgans = userOtherOrganService.getOrganIdsByUserId(id);
		if(userOtherOrgans != null){
			user.setUserOtherOrgans(userOtherOrgans);
		}
		return user;
	}
	
	/**
	 * 判断用户关键信息是否重复
	 * @param userQuery 用户信息
	 */	
	public boolean isUnique(UserQuery userQuery){		
		int count = userDao.getUniqueCountByQuery(userQuery);
		if (count > 0) {
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 用户密码
	 * @return
	 */
	public ReturnResult userLogin(String username,String password) {
		ReturnResult result = new ReturnResult();
		if (StringUtils.isEmpty(username)) {
			return result.FAILUER("用户名不能为空！");
		}
		if (StringUtils.isEmpty(password)) {
			return result.FAILUER("密码不能为空！");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			return ReturnResult.FAILUER("用户不存在！");
		} else if (SystemConfig.STATUS_VALID == user.getState()) {
			return ReturnResult.FAILUER("用户已禁用！");
		} else {
			String eycptPassword = Md5Encrypt.md5(password);
			if (user.getPassword().equalsIgnoreCase(eycptPassword)) {
				//修改登陆时间
				User tempUser = new User();
				tempUser.setUserId(user.getUserId());
				tempUser.setLoginTime(new Date());
				userDao.updateByMySelective(tempUser);	
				
				//将用户所拥有的功能id添加到user中
				List<String> funListStr=userRoleService.getFunctionsByUserId(user.getUserId());
				user.setFunctionList(funListStr);
				return result.SUCCESS("登录成功！",user);
			} else {
				return ReturnResult.FAILUER("密码错误！");
			}
		}
	}
	
	/**
	 * 修改用户密码
	 * @param id 用户ID
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param cfmPwd 确认密码
	 * @return
	 */
	@Transactional
	public ReturnResult updateUserPassword(Long id, String oldPwd, String newPwd, String cfmPwd) {
		ReturnResult result = new ReturnResult();
		if (StringUtils.isEmpty(newPwd)) {
			return result.FAILUER("新密码不能为空！");
		}
		if (StringUtils.isEmpty(oldPwd)) {
			return result.FAILUER("旧密码不能为空！");
		}
		if (StringUtils.isEmpty(cfmPwd)) {
			return result.FAILUER("确认密码不能为空！");
		}
		if (!StringUtils.equals(newPwd, cfmPwd)) {
			return result.FAILUER("确认密码与新密码不一置！");
		}
		User user = userDao.selectByPrimaryKey(id);
		if(user != null){
			if (user.getPassword().equals(Md5Encrypt.md5(oldPwd))) {
				return result.FAILUER("传入参数【旧密码】错误！");
			}
		}
		userDao.updateUserPassword(id, newPwd);
		return result.SUCCESS("修改成功!");
		
	}
	
	/**
	 * 分页查询,返回page对象
	 * @param query 用户信息
	 * @return
	 */
	public Pager<User> getPageList(UserQuery query){
		Pager<User> page = new Pager<User>(query.getPageSize(),
				query.getPageNo());
		System.out.println("**********query.username="+query.getUserName());
		page.setTotalRows(userDao.findCount(query));// 总条数
		List<User> userList = userDao.findByPage(query);
		if(userList!=null && userList.size()>0){
			page.setData(userList);// 数据
		}
		page.setPageNo(query.getPageNo());
		return page;
	}
	
	/**
	 * 分页查询,返回List
	 * @param query 用户信息
	 * @return
	 */
	public List<User> getUserPageList(UserQuery query){
		Pager<User> page = new Pager<User>(query.getPageSize(),
				query.getPageNo());
		List<User> userList = userDao.findByPage(query);
		return userList;
	}
	/**
	 * 条件查询返回记录总数
	 * @param query 用户信息
	 * @return
	 */
	public Integer getUserCount(UserQuery query){
		Pager<User> page = new Pager<User>(query.getPageSize(),
				query.getPageNo());
		return userDao.findCount(query);
	}
		
	/**
	 * 不分页条件查询
	 * @param query 用户信息
	 * @return
	 */
	public List<User> getQueryList(UserQuery query){		
		return userDao.findByQuery(query);
	}
	/**
	 * 获取单个用户详细信息
	 * @param query 用户信息
	 * @return
	 */
	public void updateUserByMySelect(User user){
		userDao.updateByMySelective(user);		
	}

	@Override
	public List<User> getListForPoliceId(Integer policeId) {
		// TODO Auto-generated method stub
		return userDao.getListForPoliceId(policeId);
	}
	
	/**
	 * 修改用户密码
	 * 2015-5-6
	 * @param id 用户ID
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param cfmPwd 确认密码
	 * @return
	 */
	@Transactional
	public ReturnResult updatePassword(Long id, String oldPwd, String newPwd,
			String cfmPwd) {
		ReturnResult result = new ReturnResult();
		// 基本参数验证
		if (StringUtils.isEmpty(newPwd)) {
			return result.FAILUER("新密码不能为空！");
		}
		if (StringUtils.isEmpty(oldPwd)) {
			return result.FAILUER("旧密码不能为空！");
		}
		if (StringUtils.isEmpty(cfmPwd)) {
			return result.FAILUER("确认密码不能为空！");
		}
		if (!StringUtils.equals(newPwd, cfmPwd)) {
			return result.FAILUER("确认密码与新密码不一置！");
		}
		// 获取用户基本信息
		User user = userDao.selectByPrimaryKey(id);
		if(user != null){
			if (!user.getPassword().equals(Md5Encrypt.md5(oldPwd))) {
				return result.FAILUER("传入参数【旧密码】错误！");
			}
		}
		System.out.println(Md5Encrypt.md5(oldPwd) + " == " +user.getPassword());
		// 执行更新密码的操作（通过MD5加密后，再保存数据库）
		userDao.updatePassword(id, Md5Encrypt.md5(newPwd));
		return result.SUCCESS("修改成功!");
	}

	@Override
	@Transactional
	public ReturnResult resetPassword(Long id) {
		ReturnResult result = new ReturnResult();
		String def = "123456"; // 默认的重置密码
		try{
			// 执行更新密码的操作（通过MD5加密后，再保存数据库）
			userDao.updatePassword(id, Md5Encrypt.md5(def));
			return result.SUCCESS("重置密码成功！");
		}catch(Exception e){
			return result.FAILUER("重置密码失败，失败原因：" + e.getMessage());
		}
	}

}
