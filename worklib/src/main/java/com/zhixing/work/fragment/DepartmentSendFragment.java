package com.zhixing.work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.work.R;
import com.zhixing.work.activity.MeetDetailActivity;
import com.zhixing.work.activity.MeetListActivity;
import com.zhixing.work.activity.TaskListActivity;
import com.zhixing.work.activity.WorkTaskDetailActivity;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.MySendWorkEntity;
import com.zhixing.work.bean.PostTaskCreateJsonBean;
import com.zhixing.work.bean.PostTaskListJsonBean;
import com.zhixing.work.bean.ResponseMeetingEntity;
import com.zhixing.work.bean.TaskListItemEntity;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 *部门发送(现在改为我发出的)
 *@author zjq
 *create at 2018/11/28 下午3:01
 */
public class DepartmentSendFragment  extends BaseFragment {
    private RecyclerView mRecyleView;
    private String ip;
    private String tenantId;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_depart_send, container, false);
         mRecyleView =view.findViewById(R.id.recy_work_my_send);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
         mRecyleView.setLayoutManager(layoutManager);
         ip=SharedPreferencesTool.getMStool(getActivity()).getIp();
        tenantId=SharedPreferencesTool.getMStool(getActivity()).getTenantId();
         userId=SharedPreferencesTool.getMStool(getActivity()).getUserId();
         initData();
         return view;
    }

    private void initData() {
//        List<MySendWorkEntity> entityList = new ArrayList<>();
//        setMeetListData(); //获取我发出的会议列表
    }




    @Override
    public void process(Message msg) {

    }

    /**
     *
     *@author zjq
     *create at 2018/12/14 上午9:51 设置会议列表数据
     */
    private void setMeetListData() {
        PostTaskListJsonBean jsonBean = new PostTaskListJsonBean();
        jsonBean.setApiCode("GetLaunchMeeting");
        jsonBean.setAppCode("CEOAssist");
        jsonBean.setTenantId(tenantId);
        jsonBean.setSystemCurrentUserID(userId);

        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);


        RetrofitClients.getInstance(getActivity(),ip).create(WorkAPi.class)
                .getMeetingList(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                })
                .subscribe(new MyBaseSubscriber<ResponseMeetingEntity>(getActivity()) {


                               @Override
                               public void onResult(ResponseMeetingEntity entity) {
                                   dismissDialog();
                                   final List<ResponseMeetingEntity.RowsBean> rows = entity.getRows();
                               }

                               @Override
                               public void onError(ResponseThrowable e) {
                                   dismissDialog();
                               }
                           }
                );





    }


    private void setTaskListData() {

//        PostTaskCreateJsonBean jsonBean=new PostTaskCreateJsonBean();
//        jsonBean.setApiCode(ApiCode);
//        jsonBean.setAppCode(AppCode);
//        jsonBean.setTenantId(tenantId);
//        jsonBean.setCreateUserId(userId);
//        String json = GsonUtil.getGson().toJson(jsonBean);
//        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
//
//        RetrofitClients.getInstance(getActivity(),ip).create(WorkAPi.class)
//                .getTaskList(body)
//                .compose(RxUtils.schedulersTransformer())  // 线程调度
//                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        showDialog("加载中");
//                    }
//                })
//                .subscribe(new Consumer<TaskListItemEntity>() {
//                    @Override
//                    public void accept(TaskListItemEntity entity) throws Exception {
//                        dismissDialog();
//                        final List<TaskListItemEntity.RowsBean> rows = entity.getRows();
//
//
//                    }
//                });


    }







}
