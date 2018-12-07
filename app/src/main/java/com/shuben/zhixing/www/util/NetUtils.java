package com.shuben.zhixing.www.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class NetUtils {
	public static byte[] readBytes(InputStream is){
		try {
		byte[] buffer = new byte[1024];
		int len = -1 ;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while((len = is.read(buffer)) != -1){
		baos.write(buffer, 0, len);
		}
		baos.close();
		return baos.toByteArray();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return null ;
		}
		public static String readString(InputStream is){
		return new String(readBytes(is));
		}
		
		public static boolean isNetworkAvailable(Activity activity)
	    {
	        Context context = activity.getApplicationContext();
	        // 获取手机�?有连接管理对象（包括对wi-fi,net等连接的管理�?
	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        
	        if (connectivityManager == null)
	        {
	            return false;
	        }
	        else
	        {
	            // 获取NetworkInfo对象
	            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
	            
	            if (networkInfo != null && networkInfo.length > 0)
	            {
	                for (int i = 0; i < networkInfo.length; i++)
	                {
	                    System.out.println(i + "===状�??===" + networkInfo[i].getState());
	                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
	                    // 判断当前网络状�?�是否为连接状�??
	                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
	                    {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }

}
