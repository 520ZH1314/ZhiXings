package com.shuben.zhixing.www.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;

public class SysUtils {
	
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	  
	  @SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity){
		  int width = 0;
		  WindowManager windowManager = activity.getWindowManager();    
          Display display = windowManager.getDefaultDisplay();    
          width=display.getWidth();
		  return width;
	  }

	public static boolean isAppAlive(Context context, String packageName) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
		if (list.size() <= 0) {
			return false;
		}
		for (ActivityManager.RunningTaskInfo info : list) {
			if (info.baseActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}
	
}
