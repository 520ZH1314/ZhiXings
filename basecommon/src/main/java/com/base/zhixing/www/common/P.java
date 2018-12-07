package com.base.zhixing.www.common;

import android.util.Log;

public class P {
	private static final boolean DEBUG = true;

	public static void c(String param){
		if(DEBUG){
			Log.v("数本", param);
		}
	}
	public static void b(boolean flag){
		if(DEBUG){
			Log.v("数本", ""+flag);
		}
	}
}
