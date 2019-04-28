package com.zhixing.employlib.api;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.zhixing.netlib.base.ApiException;

import com.zhixing.netlib.base.ExceptionEngine;
import com.zhixing.netlib.base.ServerException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 自定义请服务器被观察者
 *
 * @author weishixiong
 * @Time 2018-04-12
 */
public class MyDBaseHttpSubscriber<T> implements Subscriber<DBaseResponse<T>> {

    private  Context context;
    //异常类
    private ApiException ex;
    private MutableLiveData<DBaseResponse<T>> data;

    private Subscription s;
    private void onFinish(DBaseResponse<T> t) {
        set(t);
    }

    MyDBaseHttpSubscriber(Context context) {
       this.context=context;
        data = new MutableLiveData();
    }


    public MutableLiveData<DBaseResponse<T>> get() {
        return data;
    }

    public void set(DBaseResponse<T> t) {
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
    public void onNext(DBaseResponse<T> t) {
        onFinish(t);
    }

    @Override
    public void onError(Throwable e) {
        ex = ExceptionEngine.handleException(e);
        Toast.makeText(context,ex.getMsg(),Toast.LENGTH_SHORT).show();
       getErrorDto(ex);

    }

    public String getMsg(){
        return  ex.getMsg();
    }



    private void getErrorDto(ApiException ex) {
        DBaseResponse dto = new DBaseResponse();
        dto.setStatus("404");
        dto.setMessage(ex.getMsg());
        onFinish((DBaseResponse<T>) dto);
    }

}
