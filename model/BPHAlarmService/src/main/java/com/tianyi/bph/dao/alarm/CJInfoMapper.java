package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.CJInfo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface CJInfoMapper {
    int insert(CJInfo record);

    int insertSelective(CJInfo record);

    List<CJInfo> selectByExample(CJInfo record);

    int updateByExampleSelective(CJInfo record);

    int updateByExample(CJInfo record);
}