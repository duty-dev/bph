package com.tianyi.bph.service.system;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tianyi.bph.service.BaseTest;

public class AreaServiceTest extends BaseTest {

	@Before
	public void init() {

	}

	@Autowired
	 LogService servie;

	@Test
	@Rollback(true)
	public void testQuery() {
		System.out.println("qqqqqqqqqqqqqqqqqqqq");
		System.out.println(servie);
	}
}
