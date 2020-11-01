package com.xfd.comon.statics.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期时间工具
 * 
 * @author xfd
 *
 * 2019年3月23日
 */
public class DateUtil
{
	/** 标准时间格式 */
    public static final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日时间格式 */
    public static final String SIMPLETIMEFORMAT = "yyyy-MM-dd";

    /** 分段路径 */
    public static final String SECTION_FORMAT = File.separator + "yyyy" + File.separator + "MM" + File.separator + "dd";
    
    /**
     * 将日期格式化为String
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format)
    {
        if (date == null)
        {
            date = new Date();
        }
        if (StringUtil.isEmpty(format))
        {
            format = TIMEFORMAT;
        }
        return new SimpleDateFormat(format).format(date);
    }
    /**
     * 格式化时间
     * @param format
     * @return
     */
    public static String formatDate(String format)
    {
    	return formatDate(new Date(), format);
    }
    /**
     * 解析日期
     * @param dateStr
     * @param format
     * @return
     * @throws Exception 
     */
    public static Date parseDate(String dateStr, String format) throws Exception
    {
    	return new SimpleDateFormat(format).parse(dateStr);
    }
    /**
     * 时间往前或者往后改变
     * @param dateStr 时间字符串
     * @param n 往前或者往后推的天数
     * @return
     * @throws ParseException
     */
    public static Date addDay(String dateStr, int n) throws ParseException
    {
    	// 时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLETIMEFORMAT);   
        
        // 改变时间
        Calendar cd = Calendar.getInstance();   
        cd.setTime(sdf.parse(dateStr));   
        cd.add(Calendar.DATE, n);  
             
        return cd.getTime();     
    }
    /**
     * 时间添加月
     * @param date
     * @param monthCount
     * @return
     * @throws ParseException
     */
    public static Date addMonth(Date date, int monthCount) throws ParseException
    {
        // 改变时间
        Calendar cd = Calendar.getInstance();   
        cd.setTime(date);   
        cd.add(Calendar.MONTH, monthCount);  
             
        return cd.getTime();     
    }
}