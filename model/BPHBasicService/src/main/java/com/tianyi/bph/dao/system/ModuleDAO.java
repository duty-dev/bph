package com.tianyi.bph.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.system.Module;
import com.tianyi.bph.domain.system.ModuleJson;
import com.tianyi.bph.query.system.ModuleQuery;

@MyBatisRepository
public interface ModuleDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);
    /**
     * 通过ID获取当前ID下的子级模块
     * @param id
     * @return
     */
    ModuleJson selectModuleJsonByPrimaryKey(Integer id);
    
    List<String> getFirstByParentId(Integer id);
 
    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
    
    List<Module> getModulesByUserId(Integer userId);
    
    List<Module> findByQuery(ModuleQuery moduleQuery);
    /**
     * 获取所有权限信息
     * @return
     */
    List<ModuleJson> findModuleList();
    
    List<String> findModuleByParentId(@Param(value="parentIds")String[] parentIds);
    
    List<String> findModuleSecondLevel(Integer parentId);
    
    List<String> getModulesByRoleId(Integer roleId);
}