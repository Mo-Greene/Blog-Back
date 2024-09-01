package com.mo.mlog;

import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class MlogApplicationTests {

	public static void main(String[] args) {
		String title = "";
		String encode = URLEncoder.encode(title, StandardCharsets.UTF_8);
		System.out.println("encode = " + encode);
	}
}
