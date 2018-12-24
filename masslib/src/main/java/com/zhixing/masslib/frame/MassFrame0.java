package com.zhixing.masslib.frame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.base.zhixing.www.widget.RecycleViewDivider;
import com.base.zhixing.www.widget.pullrefreshlayout.PullRefreshLayout;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableHelper;
import com.zhixing.masslib.R;
import com.zhixing.masslib._AllCheckActivity;
import com.zhixing.masslib._SomeCheckActivity;
import com.zhixing.masslib.adapter.EnterMass3Adapter;
import com.zhixing.masslib.adapter.EnterMassAdapter;
import com.zhixing.masslib.bean.MassItemBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MassFrame0 extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer,Serializable {
    EnterMassAdapter adapter;
    RecyclerView mRecyclerView;
    ListView mListView;
    EnterMass3Adapter adapter3;
    private Handler handler;
    public static MassFrame0 newInstance( ) {
        MassFrame0 listFragment = new MassFrame0();
        return listFragment;
    }
    private int type = -1;
    public void setType(int type){
        this.type = type;
    }
    private ArrayList<MassItemBean> massItemBeans ;
    public void updataData(ArrayList<MassItemBean> massItemBeans){
        this.massItemBeans = massItemBeans;
        if(adapter!=null){
            adapter.updata(massItemBeans);
        }
        if(adapter3!=null){
            adapter3.updata(massItemBeans);
        }
    }

    public void setHandler( Handler handler ){
        this.handler = handler;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHandler(handler);
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;

    }
   /* private ArrayList<Type> rbs;
    public void init(ArrayList<Type> rbs){
        Collections.sort(rbs,new LetterComparator());
        this.rbs = rbs;

    }*/
    private PullRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if(type==3){
            view = inflater.inflate(R.layout.mass_frame0_layout3, container, false);
            mListView = view.findViewById(R.id.recycler_view);
        }else{
            view = inflater.inflate(R.layout.mass_frame0_layout, container, false);
            mRecyclerView =view. findViewById(R.id.recycler_view);
          //  mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,20,getResources().getColor(R.color.content_line)));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(manager);

        }
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setLoadMoreEnable(false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(type==1){
            adapter = new EnterMassAdapter(getActivity(),massItemBeans);
            adapter.setOnItemClick(new EnterMassAdapter.onItemClick() {
                @Override
                public void clickItem(int pos) {

                            Intent intent = new Intent(getActivity(),_AllCheckActivity.class);
                            intent.putExtra("obj",massItemBeans.get(pos));
                            startActivity(intent);

                }
            });
            mRecyclerView.setAdapter(adapter);

        }else if (type==2){
            adapter = new EnterMassAdapter(getActivity(),massItemBeans);
            adapter.setType(type);
            adapter.setOnItemClick(new EnterMassAdapter.onItemClick() {
                @Override
                public void clickItem(int pos) {
                    Intent intent2 = new Intent(getActivity(),_SomeCheckActivity.class);
                    intent2.putExtra("obj",massItemBeans.get(pos));
                    startActivity(intent2);

                }
            });
            mRecyclerView.setAdapter(adapter);
        }else if(type==3){
            adapter3 = new EnterMass3Adapter(getActivity(),massItemBeans,handler);
            //添加维修的列表事件
            mListView.setAdapter(adapter3);
        }


        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullRefreshLayout pullRefreshLayout) {
                handler.sendEmptyMessage(4);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(PullRefreshLayout pullRefreshLayout) {

            }
        });

    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }

}
