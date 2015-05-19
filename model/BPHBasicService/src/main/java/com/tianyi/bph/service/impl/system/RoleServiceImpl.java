package com.tianyi.bph.service.impl.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.SystemConfig;
import com.tianyi.bph.dao.system.ModuleDAO;
import com.tianyi.bph.dao.system.RoleDAO;
import com.tianyi.bph.dao.system.RoleModuleFuctionDAO;
import com.tianyi.bph.dao.system.UserRoleDAO;
import com.tianyi.bph.domain.system.Role;
import com.tianyi.bph.domain.system.RoleModuleFuctionKey;
import com.tianyi.bph.domain.system.UserRoleKey;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.RoleQuery;
import com.tianyi.bph.service.system.RoleService;


@Service
public class RoleServiceImpl implements RoleService{

	@Autowired RoleDAO roleDao;
	@Autowired UserRoleDAO userRoleDao;
	@Autowired RoleModuleFuctionDAO roleModuleFuctionDao;
	@Autowired ModuleDAO moduleDao;
	
	//增
	public int addRole(Role role){
		//添加到角色表
		role.setCreateTime(new Date());
		
		int i=roleDao.selectByRoleName(role.getName());
		if(i>0){
			return 0;
		}
		int id=roleDao.insert(role);
		
		//将角色id 和 功能id 对应起来。添加到数据库
		if(role.getModuleIds()!= null && role.getModuleIds().size() > 0 ){
			List<String> moduleList=role.getModuleIds();
			
			List<String> list=new ArrayList<String>();
			for (String string : moduleList) {
				list.add(string);
			}
			addModuleBak(list,moduleList);//绑定角色权限
			
			RoleModuleFuctionKey roleModuleFuctionKey = new RoleModuleFuctionKey();
			for (String moduleId : list) {
				roleModuleFuctionKey.setRoleid(role.getId());
				roleModuleFuctionKey.setModFunId(Integer.parseInt(moduleId));
				roleModuleFuctionDao.insert(roleModuleFuctionKey);
			}
		}
		
		UserRoleKey roleKey=new UserRoleKey();
		roleKey.setRoleId(role.getId());
		roleKey.setUserId(role.getUserId());
		userRoleDao.insert(roleKey);
		
		return id;
	}
	
	
	public void addModuleBak(List<String> list,List<String> moduleList){
		//系统管理功能集合
		List<String> conductArray=moduleDao.findModuleSecondLevel(Integer.parseInt(SystemConfig.SYSTEM_MANAGER));
		//获取基础数据集合
		List<String> baseArray=moduleDao.findModuleSecondLevel(Integer.parseInt(SystemConfig.BASE_MANAGER));
		//勤务报备集合
		List<String> dutyArray=moduleDao.findModuleSecondLevel(Integer.parseInt(SystemConfig.DUTY_MANAGER));
		//接处警
		List<String> policeArray=moduleDao.findModuleSecondLevel(Integer.parseInt(SystemConfig.POLICE_MANAGER));
		//报备分组
		//List<String> reportArray=moduleDao.findModuleSecondLevel(Integer.parseInt(SystemConfig.REPORT_GROUP));
		int c=0;
		int b=0;
		int p=0;
		int d=0;
		//int r=0;
		
		int con=0;
		int base=0;
		int police=0;
		int duty=0;
		//int report=0;
		for (String string : moduleList) {
			if(!string.equals("1")){
				if(string.equals(SystemConfig.SYSTEM_MANAGER)){
					con=1;
				}else if(string.equals(SystemConfig.BASE_MANAGER)){
					base=1;
				}else if(string.equals(SystemConfig.DUTY_MANAGER)){
					duty=1;
				}else if(string.equals(SystemConfig.POLICE_MANAGER)){
					p=1;
				}/*else if(string.equals(SystemConfig.REPORT_GROUP)){
					r=1;
				}*/
				
				if(!string.equals(SystemConfig.SYSTEM_MANAGER) && conductArray.contains(string)){
					if(con == 0){
						if(c == 0){
							list.add(SystemConfig.SYSTEM_MANAGER);
							c=1;
						}
					}
				}else if(!string.equals(SystemConfig.BASE_MANAGER) && baseArray.contains(string)){
					if(base == 0){
						if(b == 0){
							list.add(SystemConfig.BASE_MANAGER);
							b=1;
						}
					}
				}else if(!string.equals(SystemConfig.DUTY_MANAGER) && dutyArray.contains(string)){
					if(duty == 0){
						if(d == 0){
							list.add(SystemConfig.DUTY_MANAGER);
							d=1;
						}
					}
				}else if(!string.equals(SystemConfig.POLICE_MANAGER) && policeArray.contains(string)){
					if(police == 0){
						if(p == 0){
							list.add(SystemConfig.POLICE_MANAGER);
							p=1;
						}
					}
				}/*else if(!string.equals(SystemConfig.REPORT_GROUP) && reportArray.contains(string)){
					if(report == 0){
						if(r == 0){
							list.add(SystemConfig.REPORT_GROUP);
							r=1;
						}
					}
				}*/
			}
		}
	}
	//删
	public int deleteRole(Role role){
		int  i=0;
		if(role == null){
			throw new RestException("对象不能为空");
		}
		UserRoleKey key=new UserRoleKey();
		key.setRoleId(role.getId());
		key.setUserId(role.getUserId());
		userRoleDao.deleteByPrimaryKey(key);
		
		roleModuleFuctionDao.deleteByRoleKey(role.getId());
		
		i=roleDao.deleteByPrimaryKey(role.getId());
		return i;
	}
	//改
	public int updateRole(Role role){
		if(role == null){
			throw new RestException("role对象不能为空");
		}
		Role r=roleDao.selectByPrimaryKey(role.getId());
		if(!r.getName().equals(role.getName())){
			int ro=roleDao.selectByRoleName(role.getName());
			if(ro > 0){
				return 0;
			}
		}
		int i=roleDao.updateByPrimaryKeySelective(role);
		
		//先根据角色id删除相关的功能模块
		//再根据角色添加相应的功能模块
		if(role.getModuleIds() != null && role.getModuleIds().size() > 0  ){
			List<String> moduleList=role.getModuleIds();
			
			List<String> list=new ArrayList<String>();
			for (String string : moduleList) {
				list.add(string);
			}
			addModuleBak(list,moduleList);
			
			roleModuleFuctionDao.deleteByRoleKey(role.getId());
			
			RoleModuleFuctionKey roleModuleFuctionKey = new RoleModuleFuctionKey();
			for (String moduleId : list) {
				roleModuleFuctionKey.setRoleid(role.getId());
				roleModuleFuctionKey.setModFunId(Integer.parseInt(moduleId));
				roleModuleFuctionDao.insert(roleModuleFuctionKey);
			}
		}
		return i;
	}
	//查询
	public Role getRoleById(Integer id){
		return roleDao.selectByPrimaryKey(id);
	}
	
	//查询用户拥有的权限
	public List<Role> getRolesByUserId(Long id) {
		return roleDao.getRolesByUserId(id);
	}
	
	//条件查询
	public List<Role> getQueryList(RoleQuery query){
		
		return roleDao.findByQuery(query);
	}
	//分页查询
	public Pager<Role> getPageList(RoleQuery query){
		Pager<Role> page = new Pager<Role>(query.getPageSize(),
				query.getPageNo());
		page.setTotalRows(roleDao.findCount(query));// 总条数
		page.setData(roleDao.findByPage(query));// 数据
		page.setPageNo(query.getPageNo());
		return page;
	}
	@Override
	public List<String> getModuleIdsByRoleIds(String[] roleIds) {
		// TODO Auto-generated method stub
		return roleDao.getModuleIdsByRoleIds(roleIds);
	}

}
