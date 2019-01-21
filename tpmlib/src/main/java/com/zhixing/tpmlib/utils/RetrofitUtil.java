package com.zhixing.tpmlib.utils;

import android.util.Log;

import com.zhixing.tpmlib.service.RetrofitInterface;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private Retrofit retrofit;
    private static RetrofitUtil retrofitUtil;

    private RetrofitUtil() { }
    private RetrofitUtil(String baseUrl) {
        //第三方的日志拦截器
        HttpLoggingInterceptor logInterceptor;
        logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OKhttp3  设置拦截器打印日志
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(logInterceptor)
                .addInterceptor(new LoggingInterceptor())//自定义拦截器
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava2平台
                .client(okHttpClient)//OKhttp3添加到Retrofit
                .build();
    }
    //可指定baseUrl
    public static RetrofitUtil getInstance(String baseUrl){
        if (retrofitUtil==null){
            synchronized (RetrofitUtil.class){
                if (null==retrofitUtil){
                    retrofitUtil = new RetrofitUtil(baseUrl);
                }
            }
        }
        return retrofitUtil;
    }
    //默认的baseUrl
    public static RetrofitUtil getInstance(){
        if (null == retrofitUtil){
            return  getInstance("http://192.168.2.29:9008/");
        }
        return retrofitUtil;
    }
    //获得Retrofit
    public Retrofit getRetrofit(){
        return retrofit;
    }
    //直接获得RetrofitInterface
    public RetrofitInterface getRetrofitInterface(){
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        return retrofitInterface;
    }
    //自定义拦截器
    public static class LoggingInterceptor implements Interceptor {
        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //请求前--打印请求信息
            long t1 = System.nanoTime();
            Log.e("myMessage",String.format("Sending url %s \n %s \n\n%s \n\n %s",
                    request.url(), chain.connection(), request.headers(),request.body()));

            //网络请求
            okhttp3.Response response = chain.proceed(request);

            //网络响应后--打印响应信息
            long t2 = System.nanoTime();
            Log.e("myMessage",String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
    //日志信息
    public static class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.e("myMessage", message);
        }
    }

}
