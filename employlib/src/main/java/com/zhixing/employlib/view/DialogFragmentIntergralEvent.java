package com.zhixing.employlib.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.IntegralEventAdapt;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.performance.PersonDayEventBean;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogFragmentIntergralEvent extends DialogFragment implements View.OnClickListener {

    private ImageView iv_close;
    private RecyclerView recyclerView;
    private ImageView iv_down;
    private TextView tvName;
    private TextView tvTime;
    private CircleImageView profile_image;
      String time;
    public DialogFragmentIntergralEvent(String time){
          this.time = time;
      }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PerFormanceViewModel integralEventViewModel = ViewModelProviders.of(getActivity()).get(PerFormanceViewModel.class);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.integral_event, null);
        iv_close = (ImageView) view.findViewById(R.id.iv_integral_event_close);
        iv_down = (ImageView) view.findViewById(R.id.iv_integral_event_down);
        profile_image  = view.findViewById(R.id.profile_image);

        String userName = SharedPreferencesTool.getMStool(getActivity()).getUserName();

        tvName=view.findViewById(R.id.tv_integral_persion_name);
          tvTime=view.findViewById(R.id.tv_integral_persion_time);
        tvName.setText(userName);
        //错误时间赋值代码
       /* Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);*/
        tvTime.setText( time);
        iv_close.setOnClickListener(this);
         recyclerView=(RecyclerView) view.findViewById(R.id.recy_integral_event);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String path = SharedPreferencesTool.getMStool(getActivity()).getString("head_ico");//头像
        String ph = SharedPreferencesTool.getMStool(getActivity()).getIp()+path;

        MyImageLoader.load(getActivity(),ph ,profile_image);
          isDown();
//        integralEventViewModel.getData().observe(getActivity(), new Observer<List<IntegralEventEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<IntegralEventEntity> integralEventEntities) {
//
//            }
//        });
        integralEventViewModel.personDayEventData.observe(getActivity(), new Observer<DBaseResponse<PersonDayEventBean>>() {
            @Override
            public void onChanged(@Nullable DBaseResponse<PersonDayEventBean> personDayEventBeanDBaseResponse) {
                  if (personDayEventBeanDBaseResponse!=null){
                      List<IntegralEventEntity> integralEventEntities=new ArrayList<>();
                      List<PersonDayEventBean> rows = personDayEventBeanDBaseResponse.getRows();


                      for (PersonDayEventBean bean: rows) {
                          integralEventEntities.add(new IntegralEventEntity(rows.size()+1+"",bean.getScore()+"",bean.getItemName()));

                      }
                      recyclerView.setAdapter(new IntegralEventAdapt(R.layout.item_integral_event,integralEventEntities));

                  }
            }
        });



        return view;


    }

    private void isDown() {

        recyclerView.addOnScrollListener(new OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    //获取最后一个完全显示的ItemPosition ,角标值
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //所有条目,数量值
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        iv_down.setVisibility(View.GONE);
                    }else{
                        iv_down.setVisibility(View.VISIBLE);
                    }

                }
                }


            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });


    }


    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.5f;

        window.setAttributes(windowParams);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_integral_event_close) {
            dismiss();
        }
    }
}
