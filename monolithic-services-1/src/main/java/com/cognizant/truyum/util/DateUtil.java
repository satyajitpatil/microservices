package com.cognizant.truyum.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static Date convertToDate(String data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
		Date convertedDate = new Date();
		try {
			convertedDate = format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedDate;
	}
}
