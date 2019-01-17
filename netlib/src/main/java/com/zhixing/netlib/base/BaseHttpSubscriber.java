package com.zhixing.netlib.base;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;


import com.android.tu.loadingdialog.LoadingDailog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/**
 * 自定义请服务器被观察者
 *
 * @author weishixiong
 * @Time 2018-04-12
 */
public class BaseHttpSubscriber<T> implements Subscriber<T> {

    private  Context context;
    //异常类
    private ApiException ex;
    private MutableLiveData<T> data;

    private Subscription s;
    public void onFinish(T t) {
        set(t);
    }

    public BaseHttpSubscriber(Context context) {
       this.context=context;
        data = new MutableLiveData();
    }


    public MutableLiveData<T> get() {
        return data;
    }

    public void set(T t) {
        this.data.setValue(t);
    }





    @Override
    public void onComplete() {

        // todo some common as  dismiss loadding
    }




    @Override
    public void onSubscribe(Subscription s) {


        s.request(1);

    }

    @Override
    public void onNext(T t) {
        if (t!=null){
            onFinish(t);

        }else{
            Toast.makeText(context,"请求数据失败",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onError(Throwable e) {
        ex = ExceptionEngine.handleException(e);
        Toast.makeText(context,ex.getMsg(),Toast.LENGTH_SHORT).show();


    }

    public String getMsg(){
        return  ex.getMsg();
    }
}
