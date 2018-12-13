package com.zhixing.work.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.CopyPeopleBean;
import com.zhixing.work.bean.MeetDeatilResponseEvent;
import com.zhixing.work.bean.MeetJoinStatusBean;
import com.zhixing.work.bean.PostMeetDetailJson;
import com.zhixing.work.bean.ResponseJoinBean;
import com.zhixing.work.bean.ResponseMeetDetailEntity;
import com.zhixing.work.bean.UpdateMeetSureEvent;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * @author zjq
 * create at 2018/12/12 下午3:39 meet详情界面中的确认参加
 */
public class MeetDetailIsSureFragment extends BaseFragment {


    private RecyclerView mRecyleView;
    private String tenantId;
    private String meetingID;
    private String ip;
    private MeetDetailIsSureListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         EventBus.getDefault().register(this);
         View view = inflater.inflate(R.layout.fragment_meet_sure, container, false);
         tenantId = SharedPreferencesTool.getMStool(getContext()).getTenantId();
         meetingID = SharedPreferencesTool.getMStool(getContext()).getString("meetingID");
         ip=SharedPreferencesTool.getMStool(getActivity()).getIp();
         mRecyleView = view.findViewById(R.id.recy_meet_sure);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
          mRecyleView.setLayoutManager(layoutManager);
          initData();
        return view;
    }

    private void initData() {
        //获取确认的联系人
        PostMeetDetailJson jsonBean = new PostMeetDetailJson("CEOAssist", "GetParticipation", meetingID, tenantId);

        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        RetrofitClients.getInstance(getActivity(), ip).create(WorkAPi.class)
                .getJoinStatus(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                }).subscribe(new MyBaseSubscriber<ResponseJoinBean>(getActivity()) {
            @Override
            public void onResult(ResponseJoinBean o) {
                dismissDialog();
                Logger.d(o.getRows().size());
                List<MeetJoinStatusBean> data=new ArrayList<>();
                List<ResponseJoinBean.RowsBean> rows = o.getRows();
                for (ResponseJoinBean.RowsBean bean:rows) {
                    data.add(new MeetJoinStatusBean(bean.getParticipantsName(),bean.getMeetingStatusName(),bean.getCreateTime()));
                }
                Logger.d(data.size());
                 adapter = new MeetDetailIsSureListAdapter(R.layout.item_meet_detail_join_recyle, data);
                 mRecyleView.setAdapter(adapter);

            }

            @Override
            public void onError(ResponseThrowable e) {
                dismissDialog();
                Logger.d(e.getMessage());
            }
        });


    }


    @Override
    public void process(Message msg) {

    }


    public class MeetDetailIsSureListAdapter extends BaseQuickAdapter<MeetJoinStatusBean, BaseViewHolder> {


        public MeetDetailIsSureListAdapter(int layoutResId, @Nullable List<MeetJoinStatusBean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder baseViewHolder, MeetJoinStatusBean menuItem) {
            baseViewHolder.setText(R.id.tv_item_meet_detail_join_name, menuItem.getJoinName());//名字
            String[] ts = menuItem.getJoinDate().split("T");
            String time = ts[0] + " " + ts[1];
            baseViewHolder.setText(R.id.tv_meet_join_detail_reply_time, TimeUtil.getFormatData(time));//时间
            baseViewHolder.setText(R.id.tv_item_meet_detail_join_content, menuItem.getJoinStatus());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


      @Subscribe(threadMode = ThreadMode.MAIN)
       public void Update(UpdateMeetSureEvent event){
        if (event.isTrue){
            initData();//更新数据

        }

      }




    }



