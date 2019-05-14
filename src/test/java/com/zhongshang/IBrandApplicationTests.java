package com.zhongshang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IBrandApplicationTests {

	private static final String KEY = "f9a7f7g09s";
	@Test
	public void contextLoads() {
		String str = "123456" + KEY;
		String string = DigestUtils.md5DigestAsHex(str.getBytes());

		System.out.println(string);
	}

}
