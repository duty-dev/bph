package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.CJOrder;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface CJOrderMapper {
    int deleteByPrimaryKey(String cjdbh);

    int insert(CJOrder record);

    int insertSelective(CJOrder record);

    List<CJOrder> selectByExample(CJOrder record);

    CJOrder selectByPrimaryKey(String cjdbh);

    int updateByExampleSelective(@Param("record") CJOrder record);

    int updateByExample(@Param("record") CJOrder record);

    int updateByPrimaryKeySelective(CJOrder record);

    int updateByPrimaryKey(CJOrder record);
}