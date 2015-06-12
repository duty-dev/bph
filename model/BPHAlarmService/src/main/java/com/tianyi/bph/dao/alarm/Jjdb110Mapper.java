package com.tianyi.bph.dao.alarm;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.query.alarm.JJDBQuery;
import com.tianyi.bph.query.alarm.JJDBView;
import com.tianyi.bph.query.alarm.Jjdb110Example;

@MyBatisRepository
public interface Jjdb110Mapper {
	int deleteByPrimaryKey(String jjdbh);

	int insert(Jjdb110 record);

	int insertSelective(Jjdb110 record);

	List<Jjdb110> selectByExample(JJDBQuery JjdbQuery);

	Jjdb110 selectByPrimaryKey(String jjdbh);

	int updateByExampleSelective(Jjdb110 record);

	int updateByExample(Jjdb110 record);

	int updateByPrimaryKeySelective(Jjdb110 record);

	int updateByPrimaryKey(Jjdb110 record);
	void updateJjdbInfo(Jjdb110 Jjdb110);
	List<JJDBView> getJjdbListByQuery(JJDBQuery JjdbQuery);
	List<JJDBView> getJjdbListMixed(JJDBQuery JjdbQuery);
}