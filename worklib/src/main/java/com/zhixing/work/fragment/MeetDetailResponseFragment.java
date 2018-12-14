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
import com.zhixing.work.bean.ResponseMeetDetailEntity;
import com.zhixing.work.bean.TaskDetailEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author zjq
 * create at 2018/12/12 下午3:39 meet详情界面中的回复
 */
public class MeetDetailResponseFragment extends BaseFragment {


    private RecyclerView mRecyleView;
    private MeetDetailResponseListAdapter meetDetailResponseListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_meet_response, container, false);
        String meetResponseData = SharedPreferencesTool.getMStool(getActivity()).getString("meetResponseData");
        Type mType = new TypeToken<List<ResponseMeetDetailEntity.CommentListBean.RowsBean>>() {
        }.getType();
        List<ResponseMeetDetailEntity.CommentListBean.RowsBean> ListBeans = GsonUtil.getGson().fromJson(meetResponseData, mType);
        mRecyleView = view.findViewById(R.id.recy_meet_response);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyleView.setLayoutManager(layoutManager);
        meetDetailResponseListAdapter = new MeetDetailResponseListAdapter(R.layout.item_task_detail_recyle, ListBeans);
        mRecyleView.setAdapter(meetDetailResponseListAdapter);
        return view;
    }


    @Override
    public void process(Message msg) {

    }


    public class MeetDetailResponseListAdapter extends BaseQuickAdapter<ResponseMeetDetailEntity.CommentListBean.RowsBean, BaseViewHolder> {


        public MeetDetailResponseListAdapter(int layoutResId, @Nullable List<ResponseMeetDetailEntity.CommentListBean.RowsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ResponseMeetDetailEntity.CommentListBean.RowsBean menuItem) {
            baseViewHolder.setText(R.id.tv_task_detail_reply_name, menuItem.getCommentUserName());//名字

            String[] ts = menuItem.getCommentDate().split("T");
            String time = ts[0] + " " + ts[1];
            Logger.d(time);
            baseViewHolder.setText(R.id.tv_task_detail_reply_time, TimeUtil.getFormatData(time));//时间
            baseViewHolder.setText(R.id.tv_task_detail_reply_content, menuItem.getCommentText());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(MeetDeatilResponseEvent event) {
        if (!TextUtils.isEmpty(event.getData())) {
            String data = event.getData();
            Type mType = new TypeToken<List<ResponseMeetDetailEntity.CommentListBean.RowsBean>>() {
            }.getType();
            List<ResponseMeetDetailEntity.CommentListBean.RowsBean> ListBeans = GsonUtil.getGson().fromJson(data, mType);

            if (meetDetailResponseListAdapter != null) {
                meetDetailResponseListAdapter.replaceData(ListBeans);

            } else {
                MeetDetailResponseListAdapter meetDetailResponseListAdapter = new MeetDetailResponseListAdapter(R.layout.item_task_detail_recyle, ListBeans);
                mRecyleView.setAdapter(meetDetailResponseListAdapter);

            }
        }
    }


}
