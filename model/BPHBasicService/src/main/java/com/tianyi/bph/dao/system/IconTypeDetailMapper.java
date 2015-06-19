package com.tianyi.bph.dao.system;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.system.IconTypeDetail;
import com.tianyi.bph.query.admin.TypeQuery;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface IconTypeDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IconTypeDetail record);

    int insertSelective(IconTypeDetail record);

    List<IconTypeDetail> selectByExample(TypeQuery typeQuery);

    IconTypeDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IconTypeDetail record);

    int updateByExample(@Param("record") IconTypeDetail record);

    int updateByPrimaryKeySelective(IconTypeDetail record);

    int updateByPrimaryKey(IconTypeDetail record);
    
    List<IconTypeDetail> getTypeListByGoupId(int iconGroupId);
}