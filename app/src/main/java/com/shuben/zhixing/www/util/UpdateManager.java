package com.shuben.zhixing.www.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.carl_yang.uplib.UpVersions;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.service.DownLoadService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class UpdateManager {
	 private Activity mContext;

	    //提示语  
	    private String updateMsg = "有最新的软件包哦，亲快下载吧~";  
	      
	    //返回的安装包url  
	    //private static String apkUrl = "http://192.168.1.201:8090/Files/APK/Byd.apk";  
	    private Dialog noticeDialog;  
	    private Dialog downloadDialog;  
	     /* 下载包安装路径 */  
	    private static final String savePath = "/sdcard/updatedemo/";
	    private static final String saveFileName =   "/external_storage_root.apk";
	  
	    /* 进度条与通知ui刷新的handler和msg常量 */  
	    private ProgressBar mProgress;  
	  
	      
	    private static final int DOWN_UPDATE = 1;  
	      
	    private static final int DOWN_OVER = 2;  
	      
	    private int progress;  
	      
	    private Thread downLoadThread;  
	      
	    private boolean interceptFlag = false;
	    private UpVersions upVersions;
	      
	    private Handler mHandler = new Handler(){  
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case DOWN_UPDATE:  
	                mProgress.setProgress(progress);  
	                break;  
	            case DOWN_OVER:  
	                //installApk();
					install(mContext);
	                break;  
	            default:  
	                break;  
	            }  
	        };  
	    };  
	      
	    public UpdateManager(Activity context) {
	        this.mContext = context;  
	    }  
	      
	    //外部接口让主Activity调用  
	    public void checkUpdateInfo(String url){
	       loadData();
	    }  
	      
	      
	    private void showNoticeDialog(){  
	        Builder builder = new Builder(mContext);
	        builder.setTitle("软件版本更新");  
	        builder.setMessage(updateMsg);  
	        builder.setPositiveButton("下载", new OnClickListener() {           
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                //showDownloadDialog();
					if(checkWriteExternalPermission()){
						upVersions=new UpVersions()
								.getInstance()
								.setTitle("提示")
								.setContent("有新版本需要更新！")
								.setDownloadUrl(SharedPreferencesTool.getMStool(mContext).getUpdateUrl())
								.downAndUpApp(mContext);
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setTitle("权限禁用提示").setIcon(R.drawable.icon)
								.setMessage("对不起，文件读写权限未开启")
								.setPositiveButton("马上开启", new DialogInterface.OnClickListener() {// 积极

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub
										ActivityCompat.requestPermissions(mContext,new String []{Manifest.permission.READ_EXTERNAL_STORAGE},1);
										upVersions=new UpVersions()
												.getInstance()
												.setTitle("提示")
												.setContent("有新版本需要更新！")
												.setDownloadUrl(SharedPreferencesTool.getMStool(mContext).getUpdateUrl())
												.downAndUpApp(mContext);
									}
								}).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {// 消极

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								// TODO Auto-generated method stub
							}
						});
						builder.create().show();
					}

					//startDownload(mContext,SharedPreferencesTool.getMStool(mContext).getUpdateUrl() );
				}
	        });  
	        builder.setNegativeButton("以后再说", new OnClickListener() {             
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();                 
	            }  
	        });  
	        noticeDialog = builder.create();  
	        noticeDialog.show();  
	    }  
	      
	    private void showDownloadDialog(){  
	        Builder builder = new Builder(mContext);
	        builder.setTitle("软件版本更新");  
	          
	        final LayoutInflater inflater = LayoutInflater.from(mContext);  
	        View v = inflater.inflate(R.layout.progress, null);
	        mProgress = (ProgressBar)v.findViewById(R.id.progress);
	          
	        builder.setView(v);  
	        builder.setNegativeButton("取消", new OnClickListener() {   
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                dialog.dismiss();  
	                interceptFlag = true;  
	            }  
	        });  
	        downloadDialog = builder.create();  
	        downloadDialog.show();  
	          
	        //downloadApk();





	    }
	public static void startDownload(Context context, String downUrl) {
		Intent intent = new Intent(context, DownLoadService.class);
		intent.putExtra("downUrl", downUrl);
		context.startService(intent);
	}
	      
	    private Runnable mdownApkRunnable = new Runnable() {      
	        @Override  
	        public void run() {  
	            try {  
	                URL url = new URL(SharedPreferencesTool.getMStool(mContext).getUpdateUrl());
	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	                conn.connect();  
	                int length = conn.getContentLength();  
	                InputStream is = conn.getInputStream();  
	                  
	                //File file = new File(savePath);
					//File file = new File(getSDCardPath());
					/*File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/esp");

	                if(!appDir.exists()){
						appDir.mkdir();
	                }  
	                String apkFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/esp/new.apk";
	                File ApkFile = new File(apkFile);
	                Log.e("**文件1**",apkFile);*/
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(
							new File(Environment.getExternalStoragePublicDirectory(
									Environment.DIRECTORY_DOWNLOADS),
									"myApp.apk")),
							"application/vnd.android.package-archive");
					File file= new File(
							Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
							, "myApp.apk");
	                FileOutputStream fos = new FileOutputStream(file);
					//Log.e("*文件2**",apkFile);
	                int count = 0;  
	                byte buf[] = new byte[1024];  
	                  
	                do{                   
	                    int numread = is.read(buf);  
	                    count += numread;  
	                    progress =(int)(((float)count / length) * 100);  
	                    //更新进度
	                    mHandler.sendEmptyMessage(DOWN_UPDATE);  
	                    if(numread <= 0){      
	                        //下载完成通知安装  
	                        mHandler.sendEmptyMessage(DOWN_OVER);  
	                        break;  
	                    }  
	                    fos.write(buf,0,numread);  
	                }while(!interceptFlag);//点击取消就停止下载.  
	                  
	                fos.close();  
	                is.close();  
	            } catch (MalformedURLException e) {  
	                e.printStackTrace();  
	            } catch(IOException e){  
	                e.printStackTrace();  
	            }  
	              
	        }  
	    };  
	      
	     /** 
	     * 下载apk 
	     */
	      
	    private void downloadApk(){
           Log.e("*****下载1APK******","eeee");
	        downLoadThread = new Thread(mdownApkRunnable);
			Log.e("*****下载2APK******","eeee");
	        downLoadThread.start();  
	    }


	/**
	 * 通过隐式意图调用系统安装程序安装APK
	 */
	public static void install(Context context) {
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
				, "myApp.apk");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// 由于没有在Activity环境下启动Activity,设置下面的标签
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
			//参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
			Uri apkUri =
					FileProvider.getUriForFile(context, "com.a520wcf.chapter11.fileprovider", file);
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		}else{
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
		}
		context.startActivity(intent);
	}
	     /** 
	     * 安装apk 
	     */
	    private void installApk(){  
	        File apkfile = new File(saveFileName);  
	        if (!apkfile.exists()) {  
	            return;  
	        }      
	       /* Intent i = new Intent(Intent.ACTION_VIEW);
	        
	        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
	        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        mContext.startActivity(i);*/
			Intent intent = new Intent(Intent.ACTION_VIEW);
         //判断是否是AndroidN以及更高的版本
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				Uri contentUri = FileProvider.getUriForFile(mContext,BaseApplication.application.getPackageName() + ".fileprovider", apkfile);
				intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
			} else {
				intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			}
			mContext.startActivity(intent);
	      
	    }  
	  //作业信息文件解析

	private void loadData() {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		Map<String, String> params = new HashMap<String, String>();
		params.put("AppCode", "EPS");
		params.put("ApiCode", "GetAppVersion");

		JsonObjectRequest newMissRequest = new JsonObjectRequest(
				Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+UrlUtil.Url,
				new JSONObject(params), new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				Log.e("KKKK更新检测KKKK", " " + jsonObject.toString());
				try {
						int newVersion = jsonObject.getInt("CurrentVersion");
						String apkUrl=jsonObject.getString("AndroidURL");
						SharedPreferencesTool.getMStool(mContext).saveUpdateUrl(apkUrl);
						if (newVersion >PackageUtils.getCurrVersion(mContext)) {
							Toast.makeText(mContext,"Login"+PackageUtils.getCurrVersion(mContext),Toast.LENGTH_SHORT).show();
							showNoticeDialog();
						}


				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		requestQueue.add(newMissRequest);
	}
	private boolean checkWriteExternalPermission() {
		String permission = "android.permission.READ_EXTERNAL_STORAGE"; //你要判断的权限名字
		int res = mContext.checkCallingOrSelfPermission(permission);
		return (res == PackageManager.PERMISSION_GRANTED);
	}

	// 获取SDCard的目录路径功能
	public static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}


}
