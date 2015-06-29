package com.tianyi.bph.service.alarm;

import java.util.List;

import com.tianyi.bph.domain.alarm.AlarmCommunication;
import com.tianyi.bph.domain.alarm.AlarmType;
import com.tianyi.bph.domain.alarm.CJOrder;
import com.tianyi.bph.domain.alarm.JJDBState;
import com.tianyi.bph.domain.alarm.Jjdb110;
import com.tianyi.bph.domain.alarm.PJPolice;
import com.tianyi.bph.query.alarm.JJDBQuery;
import com.tianyi.bph.query.alarm.JJDBView;

public interface AlarmDispatchService {

	/**
	 * 写入
	 * 
	 * @param jjdb110
	 * @return
	 */
	public void saveOrUpdateJjdb110(Jjdb110 jjdb110);

	/**
	 * 获取警情简要信息列表
	 * 
	 * @param JJDBQuery
	 * @return
	 */
	public List<JJDBView> getJjdbListByQuery(JJDBQuery JjdbQuery);

	/**
	 * 获取警情详细信息列表
	 * 
	 * @param JJDBQuery
	 * @return
	 */
	public List<Jjdb110> getJjdbListInfoByQuery(JJDBQuery JjdbQuery);

	/**
	 * 获取警情状态列表
	 * 
	 * @param
	 * @return
	 */
	public List<JJDBState> getJjdbStateList();

	/**
	 * 客户端更新警情信息
	 * 
	 * @param Jjdb110
	 * @return
	 */
	public void updateJjdbInfo(Jjdb110 Jjdb110);
	/**
	 * 根据编号获取警情详情
	 * 
	 * @param jjdbh
	 * @return
	 */
	public Jjdb110 getAlarmInfo(String jjdbh);
	/**
	 * 警情信息推送
	 * 
	 * @param jjdbh
	 * @return
	 */
	public void pushAlarmMsg(AlarmCommunication alarmCommunication);

	/**
	 * 接受三台何以警情接受三 * 
	 * @param cjOrder
	 */
	public void saveOrUpdateCjOrder(CJOrder cjOrder);
	/**
	 * 获取警情类型列表
	 * 
	 * @param
	 * @return
	 */
	public List<AlarmType> getAlarmTypeList(String parentTypeCode);
	/**
	 * 警情派警
	 * 
	 * @param
	 * @return
	 */
	public void dispatchPolice(PJPolice pJPolice);
	/**
	 * 删除派警
	 * 
	 * @param
	 * @return
	 */
	int deletePJByPrimaryKey(PJPolice record);
	/**
	 * 获取警情简要信息列表
	 * 
	 * @param JJDBQuery
	 * @return
	 */
	public List<JJDBView> getJjdbListMixed(JJDBQuery JjdbQuery);
	/**
	 * 获取不含反馈信息的警情详情
	 * 
	 * @param jjdbh
	 * @return
	 */
	public Jjdb110 getAlarmDetail(String jjdbh);
	

}
