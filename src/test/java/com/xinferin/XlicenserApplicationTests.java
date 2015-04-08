package com.xinferin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xinferin.XlicenserApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = XlicenserApplication.class)
@WebAppConfiguration
public class XlicenserApplicationTests {

	@Test
	public void contextLoads() {
	}

}
