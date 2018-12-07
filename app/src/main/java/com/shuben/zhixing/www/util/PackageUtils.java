package com.shuben.zhixing.www.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils {
	public static int getCurrVersion(Context context) {
		PackageManager pm = 
			context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(
				context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getVersionName(Context context) {
		PackageManager pm =
				context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(
					context.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
