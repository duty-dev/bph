package com.tianyi.bph.service.alarm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.dao.alarm.AlarmCommunicationMapper;
import com.tianyi.bph.dao.alarm.AlarmTypeMapper;
import com.tianyi.bph.dao.alarm.CJFeedbaMapper;
import com.tianyi.bph.dao.alarm.CJInfoMapper;
import com.tianyi.bph.dao.alarm.CJOrderMapper;
import com.tianyi.bph.dao.alarm.JJDBStateMapper;
import com.tianyi.bph.dao.alarm.Jjdb110Mapper;
import com.tianyi.bph.dao.alarm.PJPoliceMapper;
import com.tianyi.bph.dao.alarm.PoleMapper;
import com.tianyi.bph.dao.system.OrganDAO;
import com.tianyi.bph.domain.alarm.AlarmCommunication;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.alarm.CJFeedba;
import com.tianyi.bph.domain.alarm.CJInfo;
import com.tianyi.bph.domain.alarm.CJOrder;
import com.tianyi.bph.domain.alarm.JJDBState;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.domain.alarm.PJPolice;
import com.tianyi.bph.domain.alarm.Pole;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.query.alarm.JJDBQuery;
import com.tianyi.bph.query.alarm.JJDBView;
import com.tianyi.bph.service.alarm.AlarmDispatchService;
import com.tianyi.bph.service.system.AsyncSendMessage;
import com.tianyi.bph.service.system.UserService;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class AlarmDispatchServiceImpl implements AlarmDispatchService {
	private Log logger = LogFactory.getLog(AlarmDispatchServiceImpl.class);

	static final private String alarmBeseRouteKey = "routeData.H.alarm";// 即时接警，接收如routeData.H.*.51000.#
	static final private String communicationBeseRouteKey = "routeData.H.communication";// 扁平化信息反馈
	static final private String dispatchPoliceBeseRouteKey = "routeData.H.dispatchPolice";// 警情派警
	static final private String deleteDispatchPoliceBeseRouteKey = "routeData.H.deleteDispatchPolice";// 警情派警
	static final private String markGpsBeseRouteKey = "routeData.H.markGps";// 标注GPS
	@Resource
	private Jjdb110Mapper jjdb110Mapper;
	@Resource
	private JJDBStateMapper jjdbStateMapper;
	@Resource
	private AlarmTypeMapper alarmTypeMapper;
	@Resource
	private CJOrderMapper cjOrderMapper;
	@Resource
	private PJPoliceMapper pjPoliceMapper;
	@Resource
	private PoleMapper poleMapper;
	@Resource
	private AlarmCommunicationMapper alarmCommunicationMapper;
	@Resource
	private CJFeedbaMapper cjFeedbaMapper;
	@Resource
	private CJInfoMapper cjInfoMapper;
	@Resource
	private OrganDAO organDAO;
	@Autowired
	private AsyncSendMessage sendMessage;
	@Resource
	private UserService userService;

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdateJjdb110(Jjdb110 jjdb110) {
		Jjdb110 db = jjdb110Mapper.selectByPrimaryKey(jjdb110.getJjdbh());
		if (db == null) {
			jjdb110Mapper.insert(jjdb110);
		} else {
			jjdb110Mapper.updateByPrimaryKeySelective(jjdb110);
		}
		// 发送 mq
		Organ organ = organDAO.selectByCode(jjdb110.getGxdwbh());
		if (organ != null) {
			String organPath = organ.getPath();
			sendMessage.asyncJsonData(
					alarmBeseRouteKey + organPath.replace("/", "."),
					JsonUtils.toJson(jjdb110.ctrate()));
		}
	}

	@Override
	public List<JJDBView> getJjdbListByQuery(JJDBQuery JjdbQuery) {
		// TODO Auto-generated method stub
		return jjdb110Mapper.getJjdbListByQuery(JjdbQuery);
	}

	@Override
	public List<Jjdb110> getJjdbListInfoByQuery(JJDBQuery JjdbQuery) {
		return jjdb110Mapper.selectByExample(JjdbQuery);
	}

	@Override
	public List<JJDBState> getJjdbStateList() {
		// TODO Auto-generated method stub
		return jjdbStateMapper.selectJJDBStateList();
	}

	@Override
	public void updateJjdbInfo(Jjdb110 Jjdb110) {
		// TODO Auto-generated method stub
		jjdb110Mapper.updateByExampleSelective(Jjdb110);
		Jjdb110 jjdb = new Jjdb110();
		jjdb = jjdb110Mapper.selectByPrimaryKey(Jjdb110.getJjdbh());
		// 发送 mq
		Organ organ = organDAO.selectByCode(jjdb.getGxdwbh());
		if (organ != null) {
			String organPath = organ.getPath();
			sendMessage.asyncJsonData(markGpsBeseRouteKey
					+ organPath.replace("/", "."), JsonUtils.toJson(Jjdb110));
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdateCjOrder(CJOrder cjOrder) {
		try {
			Jjdb110 db = jjdb110Mapper.selectByPrimaryKey(cjOrder.getJjdbh());
			if (db != null) {
				CJOrder cjdb = cjOrderMapper.selectByPrimaryKey(cjOrder
						.getCjdbh());
				if (cjdb == null) {
					cjOrderMapper.insert(cjOrder);
				} else {
					cjOrderMapper.updateByPrimaryKey(cjOrder);
				}
				// rabbitTemplate.convertAndSend("key", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Jjdb110 getAlarmInfo(String jjdbh) {
		// TODO Auto-generated method stub
		Jjdb110 jjdb = jjdb110Mapper.selectByPrimaryKey(jjdbh);
		/** 杆体 */
		if (jjdb.getLdgbh() != null) {
			Pole pole = poleMapper.selectByBm(jjdb.getLdgbh());
			jjdb.setPole(pole);
		}
		/** 处警信息 */
		CJInfo cjInfo = new CJInfo();
		cjInfo.setJjdbh(jjdbh);
		List<CJInfo> cjInfoList = cjInfoMapper.selectByExample(cjInfo);
		jjdb.setCjInfoList(cjInfoList);
		/** 三台合一处警反馈内容 */
		CJFeedba cjFeedba = new CJFeedba();
		cjFeedba.setJjdbh(jjdbh);
		List<CJFeedba> cjFeedBackList = cjFeedbaMapper
				.selectByExample(cjFeedba);
		jjdb.setCjFeedBackList(cjFeedBackList);
		/** 派警警员 */
		PJPolice pjPolice = new PJPolice();
		pjPolice.setJjdbh(jjdbh);
		List<PJPolice> pjPoliceList = pjPoliceMapper.selectByExample(pjPolice);
		jjdb.setPjPoliceList(pjPoliceList);
		/** 扁平化反馈信息 */
		AlarmCommunication communication = new AlarmCommunication();
		communication.setJjdbh(jjdbh);
		List<AlarmCommunication> alarmCommunicationList = alarmCommunicationMapper
				.selectByExample(communication);
		jjdb.setAlarmCommunicationList(alarmCommunicationList);
		return jjdb;
	}

	@Override
	public List<AlarmType> getAlarmTypeList(String parentTypeCode) {
		// TODO Auto-generated method stub;
		AlarmType type = new AlarmType();
		if (parentTypeCode != null && !"".equals(parentTypeCode)) {
			type.setParentTypeCode(parentTypeCode);
		}
		List<AlarmType> alarmTypeList = alarmTypeMapper.getAlarmTypeList(type);
		return alarmTypeList;
	}

	@Override
	public void dispatchPolice(PJPolice pJPolice) {
		// TODO Auto-generated method stub
		pjPoliceMapper.insert(pJPolice);
		Jjdb110 jjdb = new Jjdb110();
		jjdb = jjdb110Mapper.selectByPrimaryKey(pJPolice.getJjdbh());
		// 发送 mq
		Organ organ = organDAO.selectByCode(jjdb.getGxdwbh());
		if (organ != null) {
			String organPath = organ.getPath();
			sendMessage.asyncJsonData(
					dispatchPoliceBeseRouteKey + organPath.replace("/", "."),
					JsonUtils.toJson(pJPolice));
		}
	}

	@Override
	public void pushAlarmMsg(AlarmCommunication alarmCommunication) {
		// TODO Auto-generated method stub
		alarmCommunicationMapper.insert(alarmCommunication);
		User user = new User();
		if (alarmCommunication.getUserId() != null) {
			Long userId = alarmCommunication.getUserId().longValue();
			user = userService.getUserById(userId);
			alarmCommunication.setOrganName(user.getOrganName());
			alarmCommunication.setUserLoginName(user.getLoginName());
		}
		Jjdb110 jjdb = new Jjdb110();
		jjdb = jjdb110Mapper.selectByPrimaryKey(alarmCommunication.getJjdbh());
		// 发送 mq
		Organ organ = organDAO.selectByCode(jjdb.getGxdwbh());
		if (organ != null) {
			String organPath = organ.getPath();
			sendMessage.asyncJsonData(
					communicationBeseRouteKey + organPath.replace("/", "."),
					JsonUtils.toJson(alarmCommunication));
		}
	}

	@Override
	public int deletePJByPrimaryKey(PJPolice record) {
		// TODO Auto-generated method stub

		int temp = pjPoliceMapper.deleteByPrimaryKey(record);
		Jjdb110 jjdb = new Jjdb110();
		jjdb = jjdb110Mapper.selectByPrimaryKey(record.getJjdbh());
		// 发送 mq
		Organ organ = organDAO.selectByCode(jjdb.getGxdwbh());
		if (organ != null) {
			String organPath = organ.getPath();
			sendMessage.asyncJsonData(deleteDispatchPoliceBeseRouteKey
					+ organPath.replace("/", "."), JsonUtils.toJson(record));
		}
		return temp;
	}

	@Override
	public List<JJDBView> getJjdbListMixed(JJDBQuery JjdbQuery) {
		// TODO Auto-generated method stub
		return null;
	}

}
