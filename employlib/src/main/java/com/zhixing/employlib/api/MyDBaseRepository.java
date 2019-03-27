package com.zhixing.employlib.api;


import android.content.Context;

import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseHttpSubscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyDBaseRepository<T> {


    protected Context mContext;
    private  boolean isShow;
    private  Context context;

    /**
     * RxJava Subscriber回调
     */
    private MyDBaseHttpSubscriber<T> baseHttpSubscriber;
    /**
     * 解决背压
     */
    private Flowable<DBaseResponse<T>> flowable;

    /**
     * 发送请求服务器事件
     */
    public MyDBaseHttpSubscriber<T> send() {
            flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     */
    public MyDBaseRepository(Context context) {

        this.context=context;



        baseHttpSubscriber = new MyDBaseHttpSubscriber<>(context);

    }

    /**
     * 初始化flowable
     *
     * @param flowable
     * @return
     */
    public MyDBaseRepository<T> request(Flowable<DBaseResponse<T>> flowable) {
        this.flowable = flowable;
        return this;
    }

}
