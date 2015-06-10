package com.tianyi.bph.dao.system;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.system.OrganGBOrganKey;
import com.tianyi.bph.domain.system.OrganGbOrgan;
import com.tianyi.bph.query.system.OrganGBOrganExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface OrganGBOrganMapper {
	int deleteByExample(OrganGBOrganExample example);

	int deleteByPrimaryKey(OrganGBOrganKey key);

	int insert(OrganGBOrganKey record);

	int insertSelective(OrganGBOrganKey record);

	List<OrganGBOrganKey> selectByExample(OrganGBOrganExample example);

	int updateByExampleSelective(@Param("record") OrganGBOrganKey record,
			@Param("example") OrganGBOrganExample example);

	int updateByExample(@Param("record") OrganGBOrganKey record,
			@Param("example") OrganGBOrganExample example);

	List<Integer> selectGBOrganIdByOrganId(Integer organId);

	List<OrganGbOrgan> selectByOrganId(@Param("path") String path);

	List<Integer> queryExistGbId(@Param("path") String path);

}