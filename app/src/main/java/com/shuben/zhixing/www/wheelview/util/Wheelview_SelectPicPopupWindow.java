package com.shuben.zhixing.www.wheelview.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.wheelview.OnWheelScrollListener;
import com.shuben.zhixing.www.wheelview.WheelView;
import com.shuben.zhixing.www.wheelview.adapter.NumericWheelAdapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Wheelview_SelectPicPopupWindow extends PopupWindow {

  private Context contexts;
	private TextView btn_cancel,btn_ok;
	private View mMenuView;
	private LinearLayout ll_ayout;
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView min;
	private WheelView sec;
	private String birthday,minute,second,times_date;
	private int mYear;
	private int mMonth;
	private int mDay;
	View view=null;
	private SharedPreferences_wheel sharedPreferencesTool;
	boolean isMonthSetted=false, isDaySetted=false;
	private Calendar calendar;


	public Wheelview_SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
		super(context);
		calendar = Calendar.getInstance();
// 获取当前对应的年、月、日的信息
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		contexts = context;
		sharedPreferencesTool = new  SharedPreferences_wheel(contexts);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.companyaddress_alert_dialog_wheelview, null);

		ll_ayout = (LinearLayout) mMenuView.findViewById(R.id.ll_ayout);
		btn_cancel = (TextView) mMenuView.findViewById(R.id.btn_cancel);
		btn_ok = (TextView) mMenuView.findViewById(R.id.btn_ok);
		//取消按钮
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//销毁弹出框
				dismiss();
			}
		});
		btn_ok.setOnClickListener(itemsOnClick);
		ll_ayout.setOnClickListener(itemsOnClick);
		ll_ayout.addView(getDataPick());
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//避免虚拟键盘遮挡
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()== MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}

	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR)+1;

		int curYear = mYear;
		int curMonth =mMonth+1;
		int curDate = mDay;

		view = inflater.inflate(R.layout.wheel_date_picker, null);
		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(contexts,2013, norYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);//是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(contexts,1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setCyclic(true);
		day.addScrollingListener(scrollListener);

		min = (WheelView) view.findViewById(R.id.min);
		NumericWheelAdapter numericWheelAdapter3=new NumericWheelAdapter(contexts,1, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		min.setViewAdapter(numericWheelAdapter3);
		min.setCyclic(true);
		min.addScrollingListener(scrollListener);

		sec = (WheelView) view.findViewById(R.id.sec);
		NumericWheelAdapter numericWheelAdapter4=new NumericWheelAdapter(contexts,1, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		sec.setViewAdapter(numericWheelAdapter4);
		sec.setCyclic(true);
		sec.addScrollingListener(scrollListener);

		year.setVisibleItems(5);//设置显示行数
		month.setVisibleItems(5);
		day.setVisibleItems(5);
		min.setVisibleItems(5);
		sec.setVisibleItems(5);

		year.setCurrentItem(curYear - 2013);
		Log.i("Text", "pppppppppppppppppppdsdddddddddddddddddddddddddddddddddddddddddddddddddd"+curYear);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}
//second;
		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + 2013;//年
			int n_month = month.getCurrentItem() + 1;//月
			String mins = min.getCurrentItem()+1+"";
			String secs = sec.getCurrentItem()+1+"";
			if (mins.length()==1){
				Log.i("Text", "fffffff");
				minute = "0"+mins;
			}else if (mins==null||mins.equals("")){
				Log.i("Text", "gggggg");
				minute = "01";
			}else {
				Log.i("Text", "jjjjjjjjjj");
				minute = mins;
			}

			if (secs.length()==1){
				Log.i("Text", "hhhhhhhh");
				second = "0"+secs;
			}else if (secs==null||secs.equals("")){
				Log.i("Text", "kkkkkkkkkk");
				second = "01";
			}else {
				Log.i("Text", "lllllll");
				second = secs;
			}

			initDay(n_year, n_month);
			birthday = new StringBuilder().append((year.getCurrentItem() + 2013)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem() + 1) < 10) ? "0" + (day.getCurrentItem() + 1) : (day.getCurrentItem() + 1)).toString();
			String timess =  birthday+" "+minute+":"+second;
			if (timess.length()>0&&timess.length()<11){
				Log.i("Text", "lllllll");
				times_date = birthday+" 01:01";
			}else {
				times_date = timess;
			}
			Log.i("Text", "333333333333333333333333333333333333333" + times_date);
			sharedPreferencesTool.save_Key(times_date);
			Log.i("Text", "555555555555555555555555555555" +times_date);
		}
	};
	/**
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
			case 0:
				flag = true;
				break;
			default:
				flag = false;
				break;
		}
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = 31;
				break;
			case 2:
				day = flag ? 29 : 28;
				break;
			default:
				day = 30;
				break;
		}
		return day;
	}
	/**
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(contexts,1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

	/**
	 * 根据日期计算年龄
	 * @param birthday
	 * @return
	 */
	public static final String calculateDatePoor(String birthday) {
		try {
			if (TextUtils.isEmpty(birthday))
				return "0";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdayDate = sdf.parse(birthday);
			String currTimeStr = sdf.format(new Date());
			Date currDate = sdf.parse(currTimeStr);
			if (birthdayDate.getTime() > currDate.getTime()) {
				return "0";
			}
			long age = (currDate.getTime() - birthdayDate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new DecimalFormat("0.00").format(age / 365f);
			if (TextUtils.isEmpty(year))
				return "0";
			return String.valueOf(new Double(year).intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}
}
