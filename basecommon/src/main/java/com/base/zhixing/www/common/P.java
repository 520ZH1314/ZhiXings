package com.base.zhixing.www.common;

import android.util.Log;

public class P {
	private static final boolean DEBUG = true;

	public static void c(String param){
		if(DEBUG){
			Log.v("数本", param);
//			i("数本",param);
		}
	}
	public static void b(boolean flag){
		if(DEBUG){
			Log.v("数本", ""+flag);

		}
	}
	public static void i(String tag, String msg) {  //信息太长,分段打印
		//因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
		//  把4*1024的MAX字节打印长度改为2001字符数
		int max_str_length = 2001 - tag.length();
		//大于4000时
		while (msg.length() > max_str_length) {
			Log.v(tag, msg.substring(0, max_str_length));
			msg = msg.substring(max_str_length);
		}
		//剩余部分
		Log.v(tag, msg);
	}

}
