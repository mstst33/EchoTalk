package com.mstst33.echoproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TodayDate {
	public static String getDate() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String nowDate = sdf.format(currentDate.getTime());

		return nowDate;
	}

	public static String getTime() {
		Calendar cal = new GregorianCalendar();

		int mHour = cal.get(Calendar.HOUR_OF_DAY);
		int mMinute = cal.get(Calendar.MINUTE);
		int mSecond = cal.get(Calendar.SECOND);

		String nowTime = mHour + ":" + mMinute + ":" + mSecond;
		
		return nowTime;
	}
	
	public static long getMilliSecond(){
		Calendar cal = new GregorianCalendar();
		
		return cal.getTimeInMillis();
	}
}
