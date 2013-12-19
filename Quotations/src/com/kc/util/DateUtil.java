package com.kc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kc.constant.CommonConstants;

public class DateUtil
{
	public static SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
