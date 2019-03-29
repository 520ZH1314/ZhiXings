package com.zhixing.netlib.base;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 自定义请服务器被观察者
 *
 * @author weishixiong
 * @Time 2018-04-12
 */
public class MyBaseHttpSubscriber<T> implements Subscriber<BaseResponse<T>> {

    private  Context context;
    //异常类
    private ApiException ex;
    private MutableLiveData<BaseResponse<T>> data;

    private Subscription s;
    public void onFinish(BaseResponse<T> t) {
        set(t);
    }

    public MyBaseHttpSubscriber(Context context) {
       this.context=context;
        data = new MutableLiveData();
    }


    public MutableLiveData<BaseResponse<T>> get() {
        return data;
    }

    public void set(BaseResponse<T> t) {
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
    public void onNext(BaseResponse<T> t) {
        if (t.getMessage()!=null){
            onFinish(t);

        }else{
            ex = ExceptionEngine.handleException(new ServerException( t.getMessage()));
            getErrorDto(ex);
            Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
        }
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
        BaseResponse dto = new BaseResponse();

        dto.setMessage(ex.getMsg());
        onFinish((BaseResponse<T>) dto);
    }

}
