package com.zhixing.employlib.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;

public abstract class BaseLazyFragment extends BaseFragment {
    @Override
    public void process(Message msg) {

    }

    public Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    public BaseLazyFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("TAG", "fragment->setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
//        Log.d("TAG", "fragment->onCreate");
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.d("TAG", "fragment->onActivityCreated");
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        onVisible();
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }

        initData();
        isFirst = false;
    }

    //do something
    protected void onInvisible() {


    }

    protected void onVisible() {


    }

    public abstract void initData();



}
