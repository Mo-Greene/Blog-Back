package com.mo.mlog;

import org.springframework.boot.test.context.SpringBootTest;

import static com.mo.mlog.common.util.UrlSlugUtil.generateSlug;

@SpringBootTest
class MlogApplicationTests {

	public static void main(String[] args) {
		String title = "Spring DI(Dependency Injection)";

		String encodeTitle = generateSlug(title);
		System.out.println("encodeTitle = " + encodeTitle);
	}
}
