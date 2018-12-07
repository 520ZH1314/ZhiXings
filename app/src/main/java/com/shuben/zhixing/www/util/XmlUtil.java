package com.shuben.zhixing.www.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XmlUtil {
	private static String TAG="XmlUtil";
	public static String getDataByXml(String result,String name,String tag){
		ByteArrayInputStream tInputStringStream = null;
		String xml=result;

		 if (xml != null && !xml.trim().equals("")) {
			   tInputStringStream = new ByteArrayInputStream(xml.getBytes());
			  }
		 XmlPullParser parser = Xml.newPullParser();
		 try {
			parser.setInput(tInputStringStream, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
			    switch (eventType) {
			    case XmlPullParser.START_DOCUMENT:// 文档�?始事�?,可以进行数据初始化处�?
			    // persons = new ArrayList<Person>();  
			     break;
			    case XmlPullParser.START_TAG:// �?始元素事�?
			     String nodeName = parser.getName();
			     if (nodeName.equals(name)) {
			    	 try {
						result = parser.nextText();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	 //Log.e(tag, result);
			     } 
			     break;
			    case XmlPullParser.END_TAG:// 结束元素事件
			     break;
			    }
			    try {
					eventType = parser.next();
					tInputStringStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			   }
			 
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		
		return result;
		
	}
}
