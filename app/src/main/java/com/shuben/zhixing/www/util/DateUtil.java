
package com.shuben.zhixing.www.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SuppressLint("SimpleDateFormat") public class DateUtil {

private  static  DateUtil dateUtil=null;
	
	public static synchronized DateUtil getInstance() {
		  
		if (dateUtil==null){
			dateUtil=new DateUtil();
		}
	    return dateUtil;
		}
	
	public  String getDateOfToDay(){
		String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");     
		Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate);     
        return  result;
    }

    public  String getDateOfToDay02(){

        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date   curDate   =   new   Date(System.currentTimeMillis());

        result=formatter.format(curDate);
        return  result;
    }
    public  String getDateOfToDay03(){
        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        if(18<Calendar.HOUR_OF_DAY&&Calendar.HOUR_OF_DAY<=24){
            ca.add(Calendar.HOUR_OF_DAY,12);
        }else{
            ca.add(Calendar.HOUR_OF_DAY,24);
        }


        result=formatter.format(ca.getTimeInMillis());
        return  result;
    }
    public  String getDateOfToDay04(){
        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate);
        String createTime = "";
        try {
            createTime = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  result+createTime;
    }
    public  String getDateOfToMonth(){
        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM");
        Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate);
        return  result;
    }
	public  String getProblemNo(){
		String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyyMMddHHmmss");
		Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate); 
        
       
        return  result;
    }
	public  String getDateOfWeekStart(){
		String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");     
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_WEEK, ca.getFirstDayOfWeek());
        result=formatter.format(ca.getTimeInMillis());     
        return  result;
    }
    
    public  String getDateOfMonthStart(){
    	String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        result=formatter.format(ca.getTimeInMillis());     
        return  result;
    }
    
    public  String getDateOfYearStart(){
    	String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_YEAR, 1);
        result=formatter.format(ca.getTimeInMillis());     
        return  result;
    }
    
    public  int getDay(String Date1,String Date2){
    	int result=0;
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = formatter.parse(Date1);
			Date d2 = formatter.parse(Date2);
			result=(int) Math.abs(((d1.getTime() - d2.getTime())/(24*3600*1000)));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return  result;
    }
     //本季度
    public String getCurrentQuarterStartTime() { 
    	String result="";
		SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance(); 
    	int currentMonth = c.get(Calendar.MONTH) + 1; 
    	Date now = null; 
    	try { 
    	if (currentMonth >= 1 && currentMonth <= 3) 
    	c.set(Calendar.MONTH, 1); 
    	else if (currentMonth >= 4 && currentMonth <= 6) 
    	c.set(Calendar.MONTH, 3); 
    	else if (currentMonth >= 7 && currentMonth <= 9) 
    	c.set(Calendar.MONTH, 4); 
    	else if (currentMonth >= 10 && currentMonth <= 12) 
    	c.set(Calendar.MONTH, 9); 
    	c.set(Calendar.DATE, 1); 
    	result=formatter.format(c.getTimeInMillis());
    	} catch (Exception e) { 
    	e.printStackTrace(); 
    	} 
    	return result; 
    	}
    
    
    public  String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    /**
     * 
     * 描述:获取上个月的最后一天.
     * 
     * @return
     */
    public  String getLastMaxMonthDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    /**
     * 获得该月第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    //获取某月时间段
    public static String getPeriodOfMonth(int year,int month){
        String date="";
        String star=getFirstDayOfMonth( year, month);
        String end=getLastDayOfMonth( year, month);
        try {
            date = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return star+date+end;
    }

    public static Date strToDate(String style, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String dateToStr(String style, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }

    public static String clanderTodatetime(Calendar calendar, String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(calendar.getTime());
    }

    public static String DateTotime(long date, String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }
    //获取倒计时
    public static  String getCountdown(String patrolDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate);
        try
        {
            Date date = df.parse(patrolDate);
            Date now = df.parse(result);
            long diff = date.getTime() - now.getTime();
            if(diff<=0){
                result="已超期";
            }else{
                long day=diff/(24*60*60*1000);
                long hour=(diff/(60*60*1000)-day*24);
                long min=((diff/(60*1000))-day*24*60-hour*60);
                long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
                if(day>0){
                    result=day+"天"+hour+"时";
                }else {
                    if(hour>0){
                        result=hour+"时"+min+"分";
                    }else {
                        result=min+"分"+s+"秒";
                    }
                }

            }

        }
        catch (Exception e)
        {
        }

        return result;
    }
    //获取倒计时
    public static  void setCountdownColor(String patrolDate, String type, TextView textView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result="";
        SimpleDateFormat   formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date   curDate   =   new   Date(System.currentTimeMillis());
        result=formatter.format(curDate);
        try
        {
            Date date = df.parse(patrolDate);
            Date now = df.parse(result);
            long diff = date.getTime() - now.getTime();
            long hour=(diff/(60*60*1000));
            if(type.equals("一级巡线")){
                if(hour>3&&hour<=6){
                    textView.setTextColor(Color.YELLOW);
                }else if(hour<3){
                    textView.setTextColor(Color.RED);
                }else {
                    textView.setTextColor(Color.GREEN);
                }

            }else {
                //此处有错误
                if(hour>24&&R.id.day<=36){
                    textView.setTextColor(Color.YELLOW);
                }else if(hour<=24){
                    textView.setTextColor(Color.RED);
                }else{
                    textView.setTextColor(Color.GREEN);
                }

            }



        }
        catch (Exception e)
        {
        }
    }

}

