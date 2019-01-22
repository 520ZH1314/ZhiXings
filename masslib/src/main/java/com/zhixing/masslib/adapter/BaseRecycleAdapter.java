package com.zhixing.masslib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.zhixing.masslib.inter.OnRecycleItemClickListener;

import java.util.List;

/**
* @author cloor
* @time   2018-12-26 10:25
* @describe  :
*/
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter {

    protected LayoutInflater inflater;
    protected List<T> list;

    protected OnRecycleItemClickListener onRecycleItemSelectListener;


    public void setOnRecycleItemSelectListener(OnRecycleItemClickListener onRecycleItemSelectListener) {
        this.onRecycleItemSelectListener = onRecycleItemSelectListener;
    }

    public BaseRecycleAdapter(Context context, List<T> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    public void updataB( List<T> list){
        this.list =list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public List<T> getList() {
        return list;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
