package com.zhixing.netlib.base;


import android.content.Context;

import com.android.tu.loadingdialog.LoadingDailog;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
public class BaseRepository<T> {

    private  LoadingDailog dialog;
    private  boolean isShow;
    private  Context context;

    /**
     * RxJava Subscriber回调
     */
    private BaseHttpSubscriber<T> baseHttpSubscriber;
    /**
     * 解决背压
     */
    private Flowable<T> flowable;

    /**
     * 发送请求服务器事件
     */
    public BaseHttpSubscriber<T> send() {
            flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     */
    public BaseRepository(Context context) {

        this.context=context;



        baseHttpSubscriber = new BaseHttpSubscriber<>(context);

    }

    /**
     * 初始化flowable
     *
     * @param flowable
     * @return
     */
    public BaseRepository<T> request(Flowable<T> flowable) {
        this.flowable = flowable;
        return this;
    }

}
