package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.CallType;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface CallTypeMapper {
    int deleteByPrimaryKey(String code);

    int insert(CallType record);

    int insertSelective(CallType record);

    List<CallType> selectByExample(CallType record);

    CallType selectByPrimaryKey(String code);

    int updateByExampleSelective(CallType record);

    int updateByExample(CallType record);

    int updateByPrimaryKeySelective(CallType record);

    int updateByPrimaryKey(CallType record);
}