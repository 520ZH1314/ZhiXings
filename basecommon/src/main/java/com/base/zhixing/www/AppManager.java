package com.base.zhixing.www;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author 
 */
public class AppManager {
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	private AppManager(){}
	/**
	 * 同步123
	 */
	public static synchronized AppManager getAppManager() {

		if (instance == null) {
		synchronized (AppManager.class) {
			if (instance == null) {
				instance=new AppManager();
			}
		}
	}
		return instance;
	}
	/**
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		if(!activityStack.contains(activity)){
			activityStack.add(activity);
		}
		
	}
	/**
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 */
	public void finishActivity(){
		try {
			Activity activity=activityStack.lastElement();
			finishActivity(activity);
		}catch (NoSuchElementException E){

		}

	}
	/**
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			
		}
	}
}