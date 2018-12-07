package com.shuben.zhixing.www.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shuben.zhixing.www.BaseApplication;


/**
 * Description:  工具类提供一些公共方法
 */

public class SMEDTools {
	// 屏幕尺寸
    public static float mScreenWidth;
    public static float mScreenHeight;
    public static float mDensity;
    //设置输出日志
    private static boolean sign = true;
    public static Handler mHandler = new Handler();
    
    //获取屏幕的尺寸
    public static void init() {
        initScreenSize();
    }
    /**
     * 获取屏幕的尺寸
     */
    private static void initScreenSize() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        mScreenWidth =  dm.widthPixels;
        mScreenHeight =  dm.heightPixels;
        mDensity =  dm.density;
    }
    /**
     * @return  通过Application 获取全局的上下文
     */
    public static Context getContext() {
        return BaseApplication.application;
    }

    /** 当前是否是主线程 */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /** 主线程运行 */
    public static void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    public static View inflate(int layoutRes, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(layoutRes, parent, false);
    }

    public static int dp2px(int dp) {
        return (int) (mDensity * dp + 0.5f);
    }
    //日志输出
    public static void logs(String msg){
        if(sign){
            Log.i("AAAAA", msg);
        }
    }
    
//  //设置改变图标  通过代码实现DrawableTop图标
//  	public static void setRbDrawableTop(RadioButton view,int id,int color) {
//
//  		Drawable top = getContext().getResources().getDrawable(id);
//  		 view.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
//  		 view.setTextColor(color);
//  	}
	//设置改变图标  通过代码实现DrawableTop图标
	public static void setRbDrawableTop(Activity activity, RadioButton view, int id, int color) {

		Drawable top = activity.getResources().getDrawable(id);
		view.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
		view.setTextColor(color);
	}
  	//通过RGB值  生成颜色工具
  	public static int setColor(int r,int g,int b){
  		if((r >= 0 | g >= 0 | b >= 0)|( r <= 255|g <= 255|b <= 255)){
  			int rgb= Color.rgb(r,g,b);
  	  		return rgb;
  		}else{
  			return 200;
  		}
  	}
  	
  	
//代码绘制渐变的shape    
//int colors[] = { Tools.setColor(255, 0, 0) , Tools.setColor(0, 255, 0), Tools.setColor(0, 0, 255) };//分别为开始颜色，中间夜色，结束颜色
  	public static Drawable getDrawable(int colors[]){
  		//创建颜色值数组
  		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
  		return gd;
  	}
  	
  	//设置点击事件
  	public static void setViewListener(View view,Object O){
		view.setOnClickListener((OnClickListener) O);
	}
  	
  	
  	//通过布局及参数改变布局的内容结构
  	public static void setLayouMargin(RelativeLayout timepr,int left,int top,int right,int botton) {
		LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) timepr.getLayoutParams();  
        paramTest.setMargins(left,top,right,botton);
        timepr.setLayoutParams(paramTest);
	}
  	
//  	//定时任务
//  	public static void timingTask(final SpringProgressView view,final String path) {
//  		//默认值
//  		view.setMaxCount(45.0f);
//  		view.setCurrentCount(10);
//		
//  		mHandler.postDelayed(new Runnable(){
//		@Override
//		public void run() {
//			ProtocolToAsyncTask  asyncTask = new ProtocolToAsyncTask();
//			asyncTask.getString(path, new OnResultListener() {
//				
//				@Override
//				public void onSucceed(String result) {
//					Gson gson = new Gson();
//					SMEDTime time = gson.fromJson(result, SMEDTime.class);
//					int smedTime = time.getSMEDTime();
//					view.setCurrentCount(smedTime);
//				}
//				
//				@Override
//				public void onDefeated(String error) {
//					Toast.makeText(getContext(), "网络异常，获取时长失败", 1).show();
//					view.setCurrentCount(45);
//				}
//			});
//		
//			mHandler.postDelayed(this, 10000);
//			} 
//		},1000);
//		
//	}
  	
  	//移除mHandler
  	public static void removes(){
  		mHandler.removeCallbacksAndMessages(null);
  		mHandler = null;
  	}
  	
  //点击跳转工具  实测可用
  	public static void oclickIntents(Context context,Class cls) {
  		 Intent intent = new Intent(context,cls);   
  		 context.startActivity(intent);
	}
  	
  //点击跳转传数据  实测可用
  	public static void oclickIntentData(Context context,Class cls,String key,int data) {
  		Intent intent = new Intent(context,cls);
  		intent.putExtra(key, data);
  		context.startActivity(intent);
	}
  	
  	
  	
  	
 
 	
 	
 
  	//************************************************************************************************
 // 存Boolean  
   	public static void putBoolean(Context context, String name,String key, Boolean value) {
   		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
   		Editor editor = sp.edit();
   		editor.putBoolean(key, value);
   		editor.commit();
   	}
 	
 	// 取boolean
 	public static boolean getBoolean(Context context, String name,String key, boolean defValue) {
 		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
 		return sp.getBoolean(key, defValue);
 	}
   	
//*************************************************************************************************
 // 存String  
   	public static void putString(Context context, String name,String key, String value) {
   		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
   		Editor editor = sp.edit();
   		editor.putString(key, value);
   		editor.commit();
   	}
   	
 // 取String
  	public static String getString(Context context, String name,String key, String value) {
  		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
  		return sp.getString(key, value);
  	}
  	
  	
 // 存String
   	public static void putString(Context context, String key, String value) {
   		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_APPEND);
   		Editor editor = sp.edit();
   		editor.putString(key, value);
   		editor.commit();
   	}
   	
  // 取String
    	public static String getString(Context context, String key, String value) {
    		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    		return sp.getString(key, value);
    	}
 	
//*************************************************************************************************************
    	// 存int
    	public static void putInt(Context context, String name,String key, int value) {
    		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    		Editor editor = sp.edit();
    		editor.putInt(key, value);
    		editor.commit();
    	}


    	
    	// 取int
    	public static int getInt(Context context, String name,String key, int defValue) {
    		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    		return sp.getInt(key, defValue);
    	}
    	
    	
    	
//    	//判断标记
//    	public static void JudgementMarker(Context context,String o1,String o2,String o3,TextView tv,String txt){
//    		boolean M1 = Tools.getBoolean(context, o1, "ok01", true);
//			boolean M2 = Tools.getBoolean(context, o2, "ok01", true);
//			boolean M3 = Tools.getBoolean(context, o3, "ok01", true);
//			if (M1 == M2 && M1 == M3) {
//				// 处理标记
//				tv.setText("0k");
//				Tools.putString(context, txt, "ok01s", Constant.OK);
//				tv.setTextColor(Color.GREEN);
//			} else {
//				// 处理标记
//				tv.setText("异常");
//				Tools.putString(context, txt, "ok01s", "异常");
//				tv.setTextColor(Color.RED);
//			}
//    	}
    	//tos工具方法
    	private static Toast toast;
        public static void  show( String text){
            if(toast==null){
                toast=Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
            }
            toast.setText(text);
            toast.show();
        }
        public static void  show( int textRes){
            if(toast==null){
                toast=Toast.makeText(getContext(),textRes,Toast.LENGTH_SHORT);
            }
            toast.setText(textRes);
            toast.show();
        }
        
        
      //生成对应的数组
    	public static void createArr(String[] res, String[] datas1,int i) {
            String str = "";
            for (int j = 0; j <datas1.length ; j++) {
                if(j>0){
                    str += datas1[j];  //获取到第一 二个   04  05
                }
            }
            String month = str.substring(0, 2)+"月";   //获取月
            String days = str.substring(2)+"日";  //获取日
            res[i] = month+days;
        }
 
	
    
}
