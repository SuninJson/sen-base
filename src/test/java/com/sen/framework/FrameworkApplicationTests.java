package com.sen.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrameworkApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void basePackage(){
		System.out.println(FrameworkApplication.class.getPackage().getName());
	}

}
