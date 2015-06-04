package com.tianyi.bph.service.system;

import java.util.List;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.domain.system.Module;
import com.tianyi.bph.domain.system.ModuleJson;
import com.tianyi.bph.domain.system.Role;
import com.tianyi.bph.query.system.RoleQuery;

public interface RoleService {
	
	//增
	public int addRole(Role role);
	//删
	public int deleteRole(Role role);
	//改
	public int updateRole(Role role);
	//查询
	public Role getRoleById(Integer id);
	
	//查询用户拥有的权限
	public List<Role> getRolesByUserId(Long id);
	//获取所有权限
	public List<ModuleJson> getModuleList();
	
	//条件查询
	public List<Role> getQueryList(RoleQuery query);
	//分页查询
	public Pager<Role> getPageList(RoleQuery query);
	/**
	 * 通过角色id获取功能模块Id
	 * @param item
	 * @return
	 */
	List<String> getModuleIdsByRoleIds(String[] roleIds);

}
