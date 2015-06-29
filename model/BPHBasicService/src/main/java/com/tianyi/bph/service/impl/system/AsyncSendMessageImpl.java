package com.tianyi.bph.service.impl.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tianyi.bph.common.JsonUtils;
import com.tianyi.bph.common.mq.BaseData;
import com.tianyi.bph.service.system.AsyncSendMessage;

@Service
public class AsyncSendMessageImpl implements AsyncSendMessage {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	static final Logger log = LoggerFactory
			.getLogger(AsyncSendMessageImpl.class);

	@Override
	@Async
	public void async(BaseData baseData) {
		try {
			if (rabbitTemplate != null) {
				if (baseData != null) {
					rabbitTemplate.convertAndSend(baseData.routeKey(),
							JsonUtils.toJson(baseData.getData()));
				}
			} else {
				log.info("mq模版没有准备好！！！！！！！！");
			}
		} catch (AmqpException e) {
			e.printStackTrace();
		}

	}

	@Override
	@Async
	public void asyncJsonData(String routeKey, String jsonStr) {
		try {
			if (rabbitTemplate != null) {
				rabbitTemplate.convertAndSend(routeKey, jsonStr);
				log.info("mq数据发送完成================"+routeKey+":" +jsonStr);
			} else {
				log.info("mq模版没有准备好！！！！！！！！");
			}
		} catch (AmqpException e) {
			e.printStackTrace();
		}

	}

}
