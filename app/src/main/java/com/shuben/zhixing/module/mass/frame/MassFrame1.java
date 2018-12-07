package com.shuben.zhixing.module.mass.frame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.zhixing.www.util.TimeUtil;
import com.shuben.zhixing.module.mass._AllCheckActivity;
import com.shuben.zhixing.module.mass._SomeCheckActivity;
import com.shuben.zhixing.module.mass.adapter.EnterMassAdapter;
import com.shuben.zhixing.module.mass.adapter.EnterMassCjAdapter;
import com.shuben.zhixing.module.mass.bean.MassItemBean;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.base.zhixing.www.widget.ChangeTime;
import com.shuben.zhixing.www.widget.RecycleViewDivider;
import com.shuben.zhixing.www.widget.XEditText;
import com.base.zhixing.www.widget.pullrefreshlayout.PullRefreshLayout;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class MassFrame1 extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer,Serializable {
    EnterMassAdapter adapter;
    EnterMassCjAdapter cjAdapter;
    RecyclerView mRecyclerView;

    private Handler handler;
    public static MassFrame1 newInstance( ) {
        MassFrame1 listFragment = new MassFrame1();
        return listFragment;
    }

    public void setHandler( Handler handler ){
        this.handler = handler;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHandler(handler);
    }
    private int type = -1;
    public void setType(int type){
        this.type = type;
    }
    public RecyclerView getmRecyclerView() {
        return mRecyclerView;

    }
    private boolean isRight = false;
    public void isRight(boolean isRight ){
        this.isRight = isRight;
    }

    private ArrayList<MassItemBean> massItemBeans ;
    public void updataData(ArrayList<MassItemBean> massItemBeans){
        this.massItemBeans = massItemBeans;

            if(cjAdapter!=null){
                cjAdapter.updata(massItemBeans);
            }


            if(adapter!=null){
                adapter.updata(massItemBeans);
            }


    }

    TextView select_time;
    private PullRefreshLayout refreshLayout;
    private XEditText edit2;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if(type==3){
            view = inflater.inflate(R.layout.mass_frame0_layout, container, false);
            mRecyclerView =view. findViewById(R.id.recycler_view);
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,10,getResources().getColor(R.color.content_line)));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(manager);

        }else if(type==2){
            view = inflater.inflate(R.layout.mass_frame0_cr_layout, container, false);
            mRecyclerView =view. findViewById(R.id.recycler_view);
            edit2 = view.findViewById(R.id.edit2);
            select_time = view.findViewById(R.id.select_time);
            refreshLayout = view.findViewById(R.id.refreshLayout);
            refreshLayout.setLoadMoreEnable(false);
            refreshLayout.setRefreshEnable(false);
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,10,getResources().getColor(R.color.content_line)));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(manager);
        }else if(type==1){
            view = inflater.inflate(R.layout.mass_frame0_r_layout, container, false);
            mRecyclerView =view. findViewById(R.id.recycler_view);
            select_time = view.findViewById(R.id.select_time);
            refreshLayout = view.findViewById(R.id.refreshLayout);
            refreshLayout.setLoadMoreEnable(false);
            refreshLayout.setRefreshEnable(false);
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL,10,getResources().getColor(R.color.content_line)));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(manager);

        }


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     if(type==1){
            select_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeTime changeTime = new ChangeTime(getActivity(),"",2);
                    changeTime.setPastCanendar(1);
                    changeTime.setSelect(new SelectTime() {
                        @Override
                        public void select(String time, long timestp) {
                            long now = TimeUtil.parseTime_(TimeUtil.getTimeO(System.currentTimeMillis()));
                            if(timestp>=now){
                                Toasty.INSTANCE.showToast(getActivity(),"请选择历史时间");
                                return;
                            }
                           // P.c(TimeUtil.getTime(timestp)+"==="+TimeUtil.getTimeO(System.currentTimeMillis()));
                            select_time.setText(TimeUtil.getTimeCh(timestp));
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj  = TimeUtil.getTimeCh(timestp);
                            handler.sendMessage(msg);
                        }
                    });
                    changeTime.showSheet();
                }
            });
            adapter = new EnterMassAdapter(getActivity(),massItemBeans);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClick(new EnterMassAdapter.onItemClick() {
             @Override
             public void clickItem(int pos) {
                 Intent intent = new Intent(getActivity(),_AllCheckActivity.class);
                 intent.putExtra("type",type);
                 intent.putExtra("obj",massItemBeans.get(pos));
                 intent.putExtra("old",true);
                 startActivity(intent);

             }
         });
        }else if(type==2){
         select_time.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 ChangeDoubleTime doubleTime = new ChangeDoubleTime(getActivity());
                 doubleTime.setSelect(new SelectDoubleTime() {
                     @Override
                     public void select(String start, String end, long stt, long ed) {
                         /*long now = TimeUtil.parseTime_(TimeUtil.getTimeO(System.currentTimeMillis()));
                         if(ed>=now){
                             Toasty.INSTANCE.showToast(getActivity(),"请选择历史时间");
                             return;
                         }*/
                         select_time.setText(start+"至"+end );

                         String edit = edit2.getTextEx().intern();
                         Message msg = new Message();
                         msg.what = -21;
                         msg.obj  = TimeUtil.getTimeCh(stt)+"#"+TimeUtil.getTimeCh(ed)+"#"+edit;
                         handler.sendMessage(msg);

                     }
                 });
                 doubleTime.showSheet();


             }
         });
         cjAdapter = new EnterMassCjAdapter(getActivity(),massItemBeans);
         mRecyclerView.setAdapter(cjAdapter);
         cjAdapter.setOnItemClick(new EnterMassCjAdapter.onItemClick() {
             @Override
             public void clickItem(int pos) {
                 Intent intent = new Intent(getActivity(),_SomeCheckActivity.class);
                 intent.putExtra("type",type);
                 intent.putExtra("isRight",true);
                 intent.putExtra("obj",massItemBeans.get(pos));
                 intent.putExtra("old",true);
                 startActivity(intent);
             }
         });
        }else if(type==3){
         adapter = new EnterMassAdapter(getActivity(),massItemBeans);
         mRecyclerView.setAdapter(adapter);
     }



    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }



}
