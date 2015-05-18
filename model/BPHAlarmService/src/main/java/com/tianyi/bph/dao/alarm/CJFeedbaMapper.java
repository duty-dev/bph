package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.CJFeedba;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface CJFeedbaMapper {
    int insert(CJFeedba record);

    int insertSelective(CJFeedba record);

    List<CJFeedba> selectByExample(CJFeedba record);

    int updateByExampleSelective(CJFeedba record);

    int updateByExample(CJFeedba record);
}