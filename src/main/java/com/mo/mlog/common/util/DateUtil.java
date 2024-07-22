package com.mo.mlog.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static String dateTimeFormat(LocalDateTime localDateTime) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd");
		return localDateTime.format(dateTimeFormatter);
	}
}
