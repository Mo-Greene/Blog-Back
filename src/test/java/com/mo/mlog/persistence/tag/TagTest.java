package com.mo.mlog.persistence.tag;

import org.junit.jupiter.api.Test;

class TagTest {

	@Test
	void test() {
		Tag tag = Tag.builder()
			.name("hello world")
			.build();
	}

}