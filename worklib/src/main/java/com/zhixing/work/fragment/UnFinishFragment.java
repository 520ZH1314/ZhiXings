package com.zhixing.work.fragment;

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
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.CopyPeopleBean;
import com.zhixing.work.bean.PostTaskDetailJson;
import com.zhixing.work.bean.ResponseCompeteBean;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 未完成的执行人列表
 *@author zjq
 *create at 2018/12/10 下午2:48
 */

public class UnFinishFragment  extends BaseFragment {
    private RecyclerView mRecylerView;
    private TaskUnFinishPeopleListAdapter taskUnFinishPeopleListAdapter;
    private String ip;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initData() {
        ip = SharedPreferencesTool.getMStool(getActivity()).getIp();
        String tenantId = SharedPreferencesTool.getMStool(getActivity()).getTenantId();
        String TaskId = SharedPreferencesTool.getMStool(getActivity()).getString("TaskID");
        PostTaskDetailJson jsonBean = new PostTaskDetailJson();
        jsonBean.setApiCode("GetUnfinishedUsers");
        jsonBean.setAppCode("CEOAssist");
        jsonBean.setTenantId(tenantId);
        jsonBean.setTaskId(TaskId);
        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        RetrofitClients.getInstance(getActivity(),ip).create(WorkAPi.class)
                .getTaskUnFinishPeople(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                }).subscribe(new Consumer<ResponseCompeteBean >() {
            @Override
            public void accept(ResponseCompeteBean o) throws Exception {

                        dismissDialog();
                List<CopyPeopleBean> data =new ArrayList<>();
                for (ResponseCompeteBean.RowsBean bean:o.getRows()) {
                     data.add(new CopyPeopleBean(bean.getToDoUserName()));
                }
                Logger.d(data.size());
                 taskUnFinishPeopleListAdapter = new TaskUnFinishPeopleListAdapter(R.layout.item_compete, data);
                mRecylerView.setAdapter(taskUnFinishPeopleListAdapter);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unfinish, container, false);

          mRecylerView =view.findViewById(R.id.recy_unfinish);
          LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
          mRecylerView.setLayoutManager(layoutManager);
            initData();

          return view;
    }

    @Override
    public void process(Message msg) {

    }


    public class TaskUnFinishPeopleListAdapter extends BaseQuickAdapter<CopyPeopleBean, BaseViewHolder> {
        public TaskUnFinishPeopleListAdapter(int layoutResId, List<CopyPeopleBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, CopyPeopleBean menuItem) {
            baseViewHolder.setText(R.id.tv_item_name,menuItem.getCopyName());

        }
    }
}
