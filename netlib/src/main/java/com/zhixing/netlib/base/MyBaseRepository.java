package com.zhixing.netlib.base;


import android.content.Context;

import com.android.tu.loadingdialog.LoadingDailog;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyBaseRepository<T> {


    protected Context mContext;
    private  boolean isShow;
    private  Context context;

    /**
     * RxJava Subscriber回调
     */
    private MyBaseHttpSubscriber<T> baseHttpSubscriber;
    /**
     * 解决背压
     */
    private Flowable<BaseResponse<T>> flowable;

    /**
     * 发送请求服务器事件
     */
    public MyBaseHttpSubscriber<T> send() {
            flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     */
    public MyBaseRepository(Context context) {

        this.context=context;



        baseHttpSubscriber = new MyBaseHttpSubscriber<>(context);

    }

    /**
     * 初始化flowable
     *
     * @param flowable
     * @return
     */
    public MyBaseRepository<T> request(Flowable<BaseResponse<T>> flowable) {
        this.flowable = flowable;
        return this;
    }

}
