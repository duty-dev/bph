package com.tianyi.bph.service.alarm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianyi.bph.dao.alarm.JJDBStateMapper;
import com.tianyi.bph.domain.alarm.JJDBState;
import com.tianyi.bph.service.alarm.JJDBStateService;

@Service
public class JJDBStateServiceImpl implements JJDBStateService {
	
	@Resource
	private JJDBStateMapper jjdbStateMapper;

	@Override
	public List<JJDBState> getJJDBState() {
		// TODO Auto-generated method stub
		List jjdbStateList = jjdbStateMapper.selectJJDBStateList();
		return jjdbStateList;
	}

	@Override
	public void updateJJDBState(JJDBState jjdbState) {
		// TODO Auto-generated method stub
		jjdbStateMapper.updateByPrimaryKey(jjdbState);
	}


}
