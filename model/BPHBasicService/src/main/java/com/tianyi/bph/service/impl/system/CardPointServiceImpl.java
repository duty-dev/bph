package com.tianyi.bph.service.impl.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.Constants;
import com.tianyi.bph.common.MessageCode;
import com.tianyi.bph.common.Pager;
import com.tianyi.bph.common.ReturnResult;
import com.tianyi.bph.common.annotation.MQDataInterceptor;
import com.tianyi.bph.dao.system.CardPointDAO;
import com.tianyi.bph.dao.system.TCardPointMapper;
import com.tianyi.bph.domain.system.CardPoint;
import com.tianyi.bph.domain.system.CardPointCamera;
import com.tianyi.bph.domain.system.CardPointHead;
import com.tianyi.bph.domain.system.CardPointUser;
import com.tianyi.bph.domain.system.CircleLayer;
import com.tianyi.bph.domain.system.TCardPoint;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.CardPointQuery;
import com.tianyi.bph.query.system.TCardPointExample;
import com.tianyi.bph.service.system.CardPointService;

/**
 * 卡点
 * 
 * @author Administrator
 * 
 */
@Service
public class CardPointServiceImpl implements CardPointService {

	@Autowired
	CardPointDAO cardPointDao;
	@Resource
	private TCardPointMapper mapper;

	// 增
	@Override
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_ADD)
	@Transactional(readOnly = false)
	public CardPoint addCardPoint(CardPoint cardPoint) {
		CardPointQuery cardPointQuery = new CardPointQuery();
		cardPointQuery.setName(cardPoint.getName());
		int count = cardPointDao.getUniqueCountByQuery(cardPointQuery);
		if (count > 0) {
			throw new RestException("卡点名不能重复");
		}
		cardPoint.setCreateTime(new Date());
		cardPointDao.insert(cardPoint);
		return cardPoint;
	}

	// 删
	@Override
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_DELETE)
	@Transactional(readOnly = false)
	public int deleteCardPoint(Integer id) {
		cardPointDao.deleteCardPointHead(id);
		cardPointDao.deleteCardPointCamera(id);
		cardPointDao.deleteByPrimaryKey(id);
		return id;
	}

	// 改
	@Override
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_UPDATE)
	@Transactional(readOnly = false)
	public CardPoint updateCardPoint(CardPoint cardPoint) {
		CardPointQuery cardPointQuery = new CardPointQuery();
		cardPointQuery.setId(cardPoint.getCardPointId());
		cardPointQuery.setName(cardPoint.getName());
		int count = cardPointDao.getUniqueCountByQuery(cardPointQuery);
		if (count != 0) {
			throw new RestException("卡点名不能重复");
		}
		cardPoint.setUpdateTime(new Date());
		cardPointDao.updateByPrimaryKeySelective(cardPoint);
		return cardPoint;
	}

	// 查
	@Override
	public CardPoint getCardPointById(Integer id) {
		return cardPointDao.selectById(id);
	}

	// 是否重复
	public ReturnResult isUnique(CardPointQuery cardPointQuery) {

		int count = cardPointDao.getUniqueCountByQuery(cardPointQuery);
		if (count > 0) {
			return ReturnResult.MESSAGE(MessageCode.STATUS_NOTUNIQUE, "卡点名称重复");
		}
		return ReturnResult.SUCCESS(MessageCode.STATUS_SUCESS);
	}

	// 分页查询
	public Pager<CardPoint> getPageList(CardPointQuery query) {
		Pager<CardPoint> page = new Pager<CardPoint>(query.getPageSize(),
				query.getPageNo());
		page.setTotalRows(cardPointDao.findCount(query)); // 总条数
		page.setData(cardPointDao.findByPage(query)); // 数据
		page.setPageNo(query.getPageNo());
		return page;
	}

	// 条件查询
	public List<CardPoint> getQueryList(CardPointQuery query) {
		return cardPointDao.findByQuery(query);
	}

	// 查询总条数
	public int queryCardPointTotal() {
		return cardPointDao.queryCardPointTotal();
	}

	// 客户端查询接口
	public List<CardPoint> queryCardPointList(int organId) {
		return cardPointDao.queryCardPointList(organId);
	}

	@Override
	public void addCardPointHead(CardPointHead cardPointHead) {
		cardPointDao.addCardPointHead(cardPointHead);
	}

	@Override
	public void deleteCardPointHead(int cardPointHead) {
		cardPointDao.deleteCardPointHead(cardPointHead);
	}

	@Override
	public void deleteCardPointCamera(int cardPoint) {
		cardPointDao.deleteCardPointCamera(cardPoint);
	}

	// 查询卡点负责人
	@Override
	public List<CardPointHead> getCardPointHead(int cardPointId) {
		return cardPointDao.getCardPointHead(cardPointId);
	}

	public List<CardPointCamera> getCardPointCamera(int cardPointId) {
		return cardPointDao.getCardPointCamera(cardPointId);
	}

	// public void addCardPointCamera(CardPointCamera camera) {
	// cardPointDao.addCardPointCamera(camera);
	// }

	public List<CircleLayer> getCircleLayerList() {
		return cardPointDao.getCircleLayerList();
	}

	public int modifyCardPointCoordinate(CardPoint cardPoint) {
		return cardPointDao.modifyCardPointCoordinate(cardPoint);
	}

	@Override
	public int getCount(CardPointQuery query) {
		// TODO Auto-generated method stub
		return cardPointDao.findCount(query);
	}

	// =============================by chen=================================

	/**
	 * 新增卡点信息
	 */
	@Override
	@Transactional(readOnly = false)
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_ADD)
	public TCardPoint insertCardPoint(TCardPoint cardPoint) {
		TCardPointExample example = new TCardPointExample();
		example.createCriteria().andNameEqualTo(cardPoint.getName());
		int count = mapper.countByExample(example);
		if (count > 0) {
			throw new RestException("卡点名不能重复");
		}
		if (cardPoint.getLatitude() != null && cardPoint.getLongitude() != null) {
			cardPoint.setMark(true);
		}
		cardPoint.setCreateTime(new Date());
		mapper.insert(cardPoint);
		// 添加关联警员
		if (cardPoint.getPoliceUsers() != null) {
			for (Integer userId : cardPoint.getPoliceUsers()) {
				CardPointUser user = new CardPointUser();
				user.setCardPointId(cardPoint.getId());
				user.setPoliceId(userId);
				mapper.addCardPointUser(user);
			}
		}
		if (cardPoint.getCameras() != null) {
			for (Integer cameraId : cardPoint.getCameras()) {
				CardPointCamera camera = new CardPointCamera();
				camera.setCardPointId(cardPoint.getId());
				camera.setCameraId(cameraId);
				mapper.addCardPointCamera(camera);
			}
		}
		return cardPoint;
	}

	@Override
	public List<TCardPoint> getCardPointList(TCardPointExample example) {

		return mapper.selectByExample(example);
	}

	@Override
	public int getCardPointListCount(TCardPointExample example) {
		return mapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = false)
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_DELETE)
	public int deleteCardPointById(Integer id) {
		mapper.deleteCardPointCamera(id);
		mapper.deleteCardPointUser(id);
		mapper.deleteByPrimaryKey(id);
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public TCardPoint getCardPointByparamKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	@MQDataInterceptor(type = Constants.MQ_TYPE_CARDPOINT, operate = Constants.MQ_OPERATE_UPDATE)
	public TCardPoint updateCardPoint(TCardPoint cardPoint) {
		mapper.deleteCardPointUser(cardPoint.getId());
		mapper.deleteCardPointCamera(cardPoint.getId());
		// 添加关联警员
		if (cardPoint.getPoliceUsers() != null) {
			for (Integer userId : cardPoint.getPoliceUsers()) {
				CardPointUser user = new CardPointUser();
				user.setCardPointId(cardPoint.getId());
				user.setPoliceId(userId);
				mapper.addCardPointUser(user);
			}
		}
		if (cardPoint.getCameras() != null) {
			for (Integer cameraId : cardPoint.getCameras()) {
				CardPointCamera camera = new CardPointCamera();
				camera.setCardPointId(cardPoint.getId());
				camera.setCameraId(cameraId);
				mapper.addCardPointCamera(camera);
			}
		}
		mapper.updateByPrimaryKey(cardPoint);
		return cardPoint;
	}

	@Override
	public List<Integer> getCardPointUser(Integer cardPointId) {
		return mapper.getCardPointUser(cardPointId);
	}

	@Override
	public List<Integer> getCardPointCamera(Integer cardPointId) {
		return mapper.getCardPointCamera(cardPointId);
	}

	@Override
	public TCardPoint updateCardPointKeySelective(TCardPoint cardPoint) {
		mapper.updateByPrimaryKeySelective(cardPoint);
		return cardPoint;
	}

	// @Override
	// public void addCardPointCamera(CardPointCamera camera) {
	//
	// }

}
