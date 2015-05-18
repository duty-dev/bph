package com.tianyi.bph.service.impl.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.dao.system.LogMapper;
import com.tianyi.bph.domain.system.Log;
import com.tianyi.bph.query.system.LogQuery;
import com.tianyi.bph.service.system.LogService;
@Service
@Transactional
public class LogServiceImpl implements LogService {
	
	@Autowired 
	private LogMapper logDao;

	@Override
	public Pager<Log> getPageList(LogQuery query) {
		// TODO Auto-generated method stub
		Pager<Log> page = new Pager<Log>(query.getPageSize(),query.getPageNo());
		page.setTotalRows(logDao.findCount(query));// 总条数
		page.setData(logDao.findByPage(query));// 数据
		page.setPageNo(query.getPageNo());
		return page;
	}

	@Override
	public void insert(String loginIp,String userId,String userName,String msg,Integer type) {
		// TODO Auto-generated method stub
		/*Log log=new Log();
		log.setLoginIp(loginIp);
		log.setLoginUserId(userId);
		log.setLoginUserName(userName);
		log.setOperateDate(new Date());
		log.setLogContext(msg);
		log.setLogTypeId(type);
		logDao.insert(log);*/
	}

}
