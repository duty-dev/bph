package com.tianyi.bph.dao.system;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.system.IconGroup;
import com.tianyi.bph.query.admin.IconsQuery;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface IconGroupMapper {
    int insert(IconGroup record);

    int insertSelective(IconGroup record);

    List<IconGroup> selectByExample(IconsQuery iconsQuery);

    int updateByExampleSelective(@Param("record") IconGroup record);

    int updateByExample(@Param("record") IconGroup record);
    
    void deleteByPrimaryKey(int groupId);
}