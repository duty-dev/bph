package com.tianyi.bph.service;



import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( {TransactionalTestExecutionListener.class, 
	 DependencyInjectionTestExecutionListener.class
	})
@Transactional
@ContextConfiguration("classpath:spring/*.xml")
@ActiveProfiles("test") 
public abstract  class BaseTest {
	
}