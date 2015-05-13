package com.tianyi.bph.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.common.Pager;
import com.tianyi.bph.dao.system.ServiceSetMapper;
import com.tianyi.bph.domain.system.FtpConfig;
import com.tianyi.bph.domain.system.GpsConfig;
import com.tianyi.bph.domain.system.MqConfig;
import com.tianyi.bph.domain.system.ServiceSet;
import com.tianyi.bph.domain.system.ServiceVO;
import com.tianyi.bph.domain.system.User;
import com.tianyi.bph.exception.RestException;
import com.tianyi.bph.query.system.ServiceSetQuery;
import com.tianyi.bph.service.ServiceSetService;
@Service
@Transactional
public class ServiceSetServiceImpl implements ServiceSetService{

	@Autowired 
	private ServiceSetMapper serviceSetDao;
	
	@Override
	public int insert(ServiceSet record) {
		// TODO Auto-generated method stub
		if(record==null){
			throw new RestException("对象不能为空");
		}
		ServiceSetQuery query=new ServiceSetQuery();
		query.setServiceType(record.getServiceType());
		int i=serviceSetDao.selectByQuery(query);
		if(i>0){
			return 0;
		}
		i=serviceSetDao.insert(record);
		System.out.println("添加成功"+record.getServiceId());
		return i;
	}

	@Override
	public int updateByPrimaryKey(ServiceSet record) {
		// TODO Auto-generated method stub
		if(record==null){
			throw new RestException("对象不能为空");
		}
		int i=serviceSetDao.updateByPrimaryKey(record);
		return i;
	}

	@Override
	public int deleteByPrimaryKey(Integer serviceId) {
		// TODO Auto-generated method stub
		if(serviceId == null){
			throw new RestException("服务id不能为空");
		}
		int i=serviceSetDao.deleteByPrimaryKey(serviceId);
		return i;
	}

	@Override
	public Pager<ServiceSet> getPageList(ServiceSetQuery query) {
		// TODO Auto-generated method stub
		Pager<ServiceSet> page = new Pager<ServiceSet>(query.getPageSize(),
				query.getPageNo());
		page.setTotalRows(serviceSetDao.findCount(query));// 总条数
		page.setData(serviceSetDao.findByPage(query));// 数据
		page.setPageNo(query.getPageNo());
		return page;
	}

	@Override
	public ServiceSet selectByPrimaryKey(Integer serviceId) {
		// TODO Auto-generated method stub
		if(serviceId == null){
			throw new RestException("服务ID不能为空");
		}
		return serviceSetDao.selectByPrimaryKey(serviceId);
	}

	@Override
	public User getServcieList(User user) {
		// TODO Auto-generated method stub
		List<ServiceSet> serviceList=serviceSetDao.getServcieList();
		MqConfig mqconf=null;
		FtpConfig ftpconf=null;
		GpsConfig gpsconf=null;
		if(serviceList !=null && serviceList.size()>0){
			for (ServiceSet service : serviceList) {
				if(service.getServiceType().intValue()==1){//mq
					mqconf=new MqConfig();
					mqconf.setMqIp(service.getServiceIp());
					mqconf.setMqPort(service.getServicePort()+"");
					mqconf.setMqAccount(service.getServiceAccount());
					mqconf.setMqPwd(service.getServicePwd());
					mqconf.setMqExchangeName(service.getExchangeName());
					user.setMqconf(mqconf);
				}else if(service.getServiceType().intValue()==2){//ftp
					ftpconf=new FtpConfig();
					ftpconf.setFtpIp(service.getServiceIp());
					ftpconf.setFtpPort(service.getServicePort()+"");
					ftpconf.setFtpAccount(service.getServiceAccount());
					ftpconf.setFtpPwd(service.getServicePwd());
					user.setFtpconf(ftpconf);
				}else{
					 gpsconf=new GpsConfig();
					 gpsconf.setGpsIp(service.getServiceIp());
					 gpsconf.setGpsPort(service.getServicePort()+"");
					 gpsconf.setGpsAccount(service.getServiceAccount());
					 gpsconf.setGpsPwd(service.getServicePwd());
					 user.setGpsconf(gpsconf);
				}
			}
		}
		return user;
	}


}
