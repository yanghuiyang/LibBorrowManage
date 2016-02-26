package com.yang.bishe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期工具类
 * 
 * @author yang
 * 
 */
public class DateUtil {
		public static void main(String[] args) {
//			Date date1=new Date();
//			Date date2=new Date();
//			System.out.println(date1);
//			date2=addYear(date2, 2);
//			System.out.println(date2);
//			System.out.println(getIntervalDays(date2,date1));
//			System.out.println(dateToString(date2));
		}

	    /** 
	     * 增加日期中某类型的某数值。如增加日期 
	     * @param date 日期 
	     * @param dateType 类型 
	     * @param amount 数值 
	     * @return 计算后日期 
	     */  
	    private static Date addInteger(Date date, int dateType, int amount) {  
	        Date myDate = null;  
	        if (date != null) {  
	            Calendar calendar = Calendar.getInstance();  
	            calendar.setTime(date);  
	            calendar.add(dateType, amount);  
	            myDate = calendar.getTime();  
	        }  
	        return myDate;  
	    }  
	    /** 
	     * 增加日期的年份。失败返回null。 
	     * @param date 日期 
	     * @param yearAmount 增加数量。可为负数 
	     * @return 增加年份后的日期 
	     */  
	    public static Date addYear(Date date, int yearAmount) {  
	        return addInteger(date, Calendar.YEAR, yearAmount);  
	    }  
	      
	    /** 
	     * 增加日期的月份。失败返回null。 
	     * @param date 日期 
	     * @param yearAmount 增加数量。可为负数 
	     * @return 增加月份后的日期 
	     */  
	    public static Date addMonth(Date date, int yearAmount) {  
	        return addInteger(date, Calendar.MONTH, yearAmount);  
	    }  
	    
	    /** 
	     * 增加日期的天数。失败返回null。 
	     * @param date 日期 
	     * @param dayAmount 增加数量。可为负数 
	     * @return 增加天数后的日期 
	     */  
	    public static Date addDay(Date date, int dayAmount) {  
	        return addInteger(date, Calendar.DATE, dayAmount);  
	    }  
	    
	    /** 
	     * 获取日期。默认yyyy-MM-dd格式。失败返回null。 
	     * @param date 日期 
	     * @return 日期 
	     */  
	    public static String getDate(Date date) {  
	        return dateToString(date, "yyyy-MM-dd");  
	    }  

		/**
		 * 日期转字符串
		 * 
		 * @param date
		 *            日期
		 * @param pattern
		 *            格式
		 * @return
		 */
		public static String dateToString(Date date, String pattern) {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.format(date);
			}
			return "";
		}

		/**
		 * 日期转字符串
		 * 
		 * @param date
		 * @return string yyyy-MM-dd hh:mm:ss
		 */
		public static String dateToString(Date date) {
			return dateToString(date, "yyyy-MM-dd hh:mm:ss");
		}
	      
	    /** 
	     * @param date 日期 
	     * @param otherDate 另一个日期 
	     * @return 相差天数 
	     */  
	    public static int getIntervalDays(Date date, Date otherDate) {  
	      //  date = DateUtil.StringToDate(DateUtil.getDate(date));  
	    	 if (null == date || null == otherDate) {

	             return -1;
	         }
	        long time = Math.abs(date.getTime() - otherDate.getTime());  
	        return new Long(time/(24 * 60 * 60 * 1000)).intValue();  //这里强制转换int可能会溢出
	    }  
	    /**
	     * 判断字符串格式的日期前后
	     * @param start
	     * @param end
	     * @return
	     * @throws ParseException 
	     */
	    public static int compareStringDate(String start, String end) throws ParseException {  
//	    	 if (null == start || null == end) {
//	             return -1;
//	         }
	    	 Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);  
	    	 Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);  
	    	 if(startDate.before(endDate))
	    		 return 1;
	    	 else return -1;
	    }  
}
