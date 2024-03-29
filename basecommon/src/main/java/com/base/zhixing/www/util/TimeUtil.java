package com.base.zhixing.www.util;
import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * @author
 *
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
	public static long getNow(){

		return  System.currentTimeMillis();
	}


	public static String getTimeAll(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return formatter.format(time);
	}
	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
	public static String getTimeO(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return format.format(new Date(time));
	}
	public static String getTimemm(long time) {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(new Date(time));
	}
	public static long getTimepmm(String time) {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  0;
	}
	public static String getTimeYear(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(new Date(time));
	}
	public static String getTimeCh(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(time));
	}
	public static String getTimeChY(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(new Date(time));
	}
	public static String getTimePri(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(new Date(time));
	}
	public static String getTimeH(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}
	public static String get_HH(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(new Date(time));
	}
	public static String get_mm(long time) {
		SimpleDateFormat format = new SimpleDateFormat("mm");
		return format.format(new Date(time));
	}

	public static String getTimeLog(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date(time));
	}
	public static String getDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		return format.format(new Date(time));
	}
    public static String getYear(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(time);
    }
    public static String getMonth(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(time);
    }
	public static String getDay(Date time) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return format.format(time);
	}
	public static String getDateByDate(Date time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(time);
	}
	public static String getTimeTo(String mat,String now){
		SimpleDateFormat format = new SimpleDateFormat(mat);
		return format.format(new Date(parseTime(now)));

	}
	public static long parseTime(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  0;
	}

	public static long parseTimeC(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//format.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  0;
	}
	public static Date parseTimeDate(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//format.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return  format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  new Date();
	}

	public static String parseTime_h(String time){
//		1991-11-15 00:00:00
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format0 = new SimpleDateFormat("HH");
		try {
			return  format0.format(format.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  null;
	}
    public static String parseTime_day(String time){
//		1991-11-15 00:00:00
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat format0 = new SimpleDateFormat("dd");
        try {
            return  format0.format(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

	public static long parseTime_(String time){
//		1991-11-15 00:00:00
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  0;
	}
	public static long parseTime_y(String time){
//		1991-11-15 00:00:00
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return  format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  0;
	}
	public static String getTimeHome(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日\nHH:mm");
		return format.format(new Date(time));
	}

	public static String getDate_(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(time));
	}
	public static String getNextDay(String date,boolean next){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(date);
			Calendar g = Calendar.getInstance();
			g.setTime(d1);
			if(next){
				g.add(Calendar.DAY_OF_MONTH,1);
			}else{
				g.add(Calendar.DAY_OF_MONTH,-1);
			}
			Date d2 = g.getTime();
			return  df.format(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getDate(System.currentTimeMillis());
	}

	public static String getNextDate(String date,boolean next){
		DateFormat df = new SimpleDateFormat("yyyy.M");
		try {
			Date d1 = df.parse(date);
			Calendar g = Calendar.getInstance();
			g.setTime(d1);
			if(next){
				g.add(Calendar.MONTH,1);
			}else{
				g.add(Calendar.MONTH,-1);
			}
			Date d2 = g.getTime();
			return  df.format(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getSelectDate(System.currentTimeMillis());
	}
	public static String getSelectDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.M");
		return format.format(new Date(time));
	}
	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
			case 0:
				result = "今天 " + getHourAndMin(timesamp);
				break;
			case 1:
				result = "昨天 " + getHourAndMin(timesamp);
				break;
			case 2:
				result = "前天 " + getHourAndMin(timesamp);
				break;

			default:
				// result = temp + "天前 ";
				result = getTime(timesamp);
				break;
		}

		return result;
	}
	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	public static Calendar getPastCalendar(int past){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		return calendar;
	}
	/**
	 * 获取未来 第 past 天的日期
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}
	public static long getFetureSec(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = format.format(today);
		//	P.c("转换"+result);

		return today.getTime();
	}
	/**
	 * 日期转周
	 *
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 *
	 *@author zjq
	 *create at 2018/12/12 上午11:02
	 * 更新将时间带秒的字符串转为不带秒
	 */
	public static String getFormatData(String time)  {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String time2 = formatter.format(date);
		return time2;
	}


/**
 *
 *@author zjq
 *create at 2018/12/19 上午8:36
 * 比较两个时间的大小(yy-mm-dd:hh:mm)
 */
	public static int getTimeCompareSize(String startTime, String endTime){
		int i=0;


			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			try {
				Date dt1 = df.parse(startTime);
				Date dt2 = df.parse(endTime);
				if (dt1.getTime() > dt2.getTime()) {
					i=1;
				} else if (dt1.getTime() < dt2.getTime()) {

					i=2;
				} else  if (dt1.getTime()==dt2.getTime()){
					i=0;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return i;
		}

		/**
		 *
		 *@author zjq
		 *create at 2019/1/8 上午11:31
		 * 比较两个时间大小(yy-mm-dd)
		 */
	public static int getTimeCompareSizes(String startTime, String endTime){
		int i=0;


		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(startTime);
			Date dt2 = df.parse(endTime);
			if (dt1.getTime() > dt2.getTime()) {
				i=1;
			} else if (dt1.getTime() < dt2.getTime()) {

				i=2;
			} else  if (dt1.getTime()==dt2.getTime()){
				i=0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return i;
	}


	/**
	 *
	 *@author zjq
	 *create at 2018/12/19 下午1:26
	 * 将2018-4-8 3:2 转换为 2018-04-08 03:02
	 */

	public static String getCommonTime(String time)  {


		String LgTime1="";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date LgTime = simpleDateFormat.parse(time);
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String  LgTime2 = simpleDateFormat1.format(LgTime);
			LgTime1=LgTime2;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return  LgTime1;

	}

	/**
	 *
	 *@author zjq
	 *create at 2018/12/19 下午1:26
	 * 将2018-4-8  转换为 2018-04-08
	 */

	public static String getCommonTime1(String time)  {


		String LgTime1="";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd" );
			Date LgTime = simpleDateFormat.parse(time);
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			String  LgTime2 = simpleDateFormat1.format(LgTime);
			LgTime1=LgTime2;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return  LgTime1;

	}
	/**
	 *
	 *@author zjq
	 *create at 8-9 下午08-09
	 * 将2018-4-8  转换为 2018-04-08
	 */

	public static String getCommonTime2(String time)  {


		String LgTime1="";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm" );
			Date LgTime = simpleDateFormat.parse(time);
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
			String  LgTime2 = simpleDateFormat1.format(LgTime);
			LgTime1=LgTime2;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return  LgTime1;

	}

	/**
	 * 获取指定年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth1(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getMinimum(Calendar.DATE);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH,firstDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	/**
	 * 获取指定年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth1(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}



	/**
	 * date2比date1多的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}


}
