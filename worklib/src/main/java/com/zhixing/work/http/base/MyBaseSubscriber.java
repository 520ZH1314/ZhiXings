package com.zhixing.work.http.base;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by goldze on 2017/5/10.
 * 该类仅供参考，实际业务Code, 根据需求来定义，
 */
public abstract class MyBaseSubscriber<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);

    private Context context;
    private boolean isNeedCahe;

    public MyBaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getMessage());
        // todo error somthing

        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }

    }

    @Override
    public void onComplete() {

        // todo some common as  dismiss loadding
    }


    public abstract void onError(ResponseThrowable e);

    @Override
    public void onNext(Object o) {


            onResult((T)o);

    }
}
