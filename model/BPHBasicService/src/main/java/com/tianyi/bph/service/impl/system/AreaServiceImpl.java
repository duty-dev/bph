package com.tianyi.bph.service.impl.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.system.AreaMapper;
import com.tianyi.bph.dao.system.AreaPointMapper;
import com.tianyi.bph.dao.system.AreaRelationUserMapper;
import com.tianyi.bph.dao.system.OrganDAO;
import com.tianyi.bph.domain.system.Area;
import com.tianyi.bph.domain.system.AreaPoint;
import com.tianyi.bph.domain.system.AreaRelationUserKey;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.AreaExample;
import com.tianyi.bph.query.system.AreaPointExample;
import com.tianyi.bph.query.system.AreaRelationUserExample;
import com.tianyi.bph.service.system.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaMapper mapper;
	@Resource
	private OrganDAO organDao;
	@Resource
	private AreaPointMapper areaPointMapper;
	@Resource
	private AreaRelationUserMapper areaRelationUserMapper;

	@Override
	@MQDataInterceptor(type = Constants.MQ_TYPE_AREA, operate = Constants.MQ_OPERATE_ADD)
	@Transactional(readOnly = false)
	public Area addArea(Area area) {
		int count = mapper
				.checkAreaName(area.getAreaName(), area.getAreaType());
		if (count > 0) {
			throw new RestException("区域名已经存在！");
		}
		mapper.insert(area);
		if (area.getRelationUserKeys() != null) {
			for (Integer userId : area.getRelationUserKeys()) {
				AreaRelationUserKey key = new AreaRelationUserKey();
				key.setAreaId(area.getId());
				key.setUserId(userId);
				areaRelationUserMapper.insert(key);
			}
		}
		return area;
	}

	@Override
	public List<Area> selectByExample(AreaExample example) {
		List<Area> list = mapper.selectByExample(example);
		// for (final Area area : list) {
		// area.setRelationUserKeys(areaRelationUserMapper.selectByAreaId(area
		// .getId()));
		// }
		return list;
	}

	@Override
	public Area queryByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	@MQDataInterceptor(type = Constants.MQ_TYPE_AREA, operate = Constants.MQ_OPERATE_UPDATE)
	@Transactional(readOnly = false)
	public Area updateByPrimaryKey(Area record) {
		Area area = mapper.selectByPrimaryKey(record.getId());
		if (area == null) {
			throw new RestException("修改的区域不存在！");
		}
		if (StringUtils.hasLength(record.getAreaName())) {
			area.setAreaName(record.getAreaName());
		}
		if (StringUtils.hasLength(record.getTel())) {
			area.setTel(record.getTel());
		}
		if (record.getNnt() != null) {
			area.setNnt(record.getNnt());
		}
		if (StringUtils.hasLength(record.getMapProperty())) {
			area.setMapProperty(record.getMapProperty());
		}
		if (StringUtils.hasLength(record.getDisplayProperty())) {
			area.setDisplayProperty(record.getDisplayProperty());
		}
		if (record.getRelationUserKeys() != null) {
			AreaRelationUserExample example = new AreaRelationUserExample();
			example.createCriteria().andAreaIdEqualTo(record.getId());
			areaRelationUserMapper.deleteByExample(example);
			for (Integer userId : record.getRelationUserKeys()) {
				AreaRelationUserKey key = new AreaRelationUserKey();
				key.setAreaId(area.getId());
				key.setUserId(userId);
				areaRelationUserMapper.insert(key);
			}
		}
		mapper.updateByPrimaryKey(area);
		return area;
	}

	@Override
	@Transactional(readOnly = false)
	@MQDataInterceptor(type = Constants.MQ_TYPE_AREA, operate = Constants.MQ_OPERATE_DELETE)
	public int deleteByPrimaryKey(Integer id) {
		Area area = mapper.selectByPrimaryKey(id);
		if (area == null) {
			throw new RestException("修改的区域不存在！");
		}
		if (area.getFlag() != AreaExample.flag_escape) {
			area.setFlag(AreaExample.flag_escape);
			mapper.updateByPrimaryKey(area);
		}
		return id;
	}

	@Override
	public List<Area> selectByOrganId(Integer organId) {
		Organ organ = null;
		try {
			organ = organDao.selectByPrimaryKey(organId);
			if (organ == null) {
				throw new RestException("机构不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapper.selectByOrganId(organ.getPath());
	}

	@Override
	@Transactional(readOnly = false)
	public AreaPoint addAreaPoint(AreaPoint areaPoint) {
		Area area = mapper.selectByPrimaryKey(areaPoint.getAreaId());
		if (area == null) {
			throw new RestException("巡区不存在!");
		}
		int count = areaPointMapper.checkAreaPointName(areaPoint.getAreaId(),
				areaPoint.getName());
		if (count > 0) {
			throw new RestException("必达点名字已经存在!");
		}
		areaPointMapper.insert(areaPoint);
		return areaPoint;
	}

	@Override
	public List<AreaPoint> queryAreaPointList(Integer areaId) {
		AreaPointExample example = new AreaPointExample();
		example.createCriteria().andAreaIdEqualTo(areaId);
		return areaPointMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false)
	public AreaPoint updateAreaPoint(AreaPoint point) {
		int count = areaPointMapper.checkAreaPointName(point.getAreaId(),
				point.getName());
		if (count > 0) {
			throw new RestException("必达点名字已经存在!");
		}
		areaPointMapper.updateByPrimaryKey(point);
		return point;
	}

	@Override
	@Transactional(readOnly = false)
	public int deleteAreaPoint(Integer areaPointId) {
		areaPointMapper.deleteByPrimaryKey(areaPointId);
		return areaPointId;
	}
}
