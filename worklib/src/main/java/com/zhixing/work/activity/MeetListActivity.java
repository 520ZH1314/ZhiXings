package com.zhixing.work.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.stateviewlibrary.StateView;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.MeetCompeteRefrshDataEvent;
import com.zhixing.work.bean.MeetDisRefrshDataEvent;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostTaskListJsonBean;
import com.zhixing.work.bean.ResponseMeetingEntity;
import com.zhixing.work.bean.TaskListItemEntity;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.MeetStatusTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;
import okhttp3.RequestBody;

/**
 * 会议列表
 *
 * @author zjq
 * create at 2018/11/30 下午2:05
 */
public class MeetListActivity extends BaseActvity implements View.OnClickListener, MeetStatusTypeDialog.OnDialogInforCompleted {

    private TextView mTvMeetStatusType;
    private RelativeLayout mRelativeLayout;
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    private String AppCode = "CEOAssist";
    private String ApiCode = "GetLaunchMeeting";//默认我的发起
    private String userId;
    private String tenantId;
    private TextView mTvTaskTitle;
    private TextView mTvTaskCreateTime;
    private TextView mTvTaskContent;
    private TextView mTvTaskOpenTime;
    private TextView mTvTaskAddress;
    private TextView mTvTaskOriginator;
    private TextView mTvTaskStatus;
    private TextView mTvTaskDynamic;
    private CheckBox mCheckBox;
    private MeetListAdapter adapter;
    private RecyclerView mRecycleViewMeetList;
    private String ip;
    private LinearLayout mContentView;
    private StateView mStateView;
    private  RequestBody body;


    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_list;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         EventBus.getDefault().register(this);
        ip = SharedPreferencesTool.getMStool(this).getIp();
        userId = SharedPreferencesTool.getMStool(this).getUserId();
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        mTvMeetStatusType = (TextView) findViewById(R.id.tv_meet_type);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_meet_type);
        mContentView=(LinearLayout) findViewById(R.id.ll_meeting_list);
        mIvadd = (ImageView) findViewById(R.id.iv_work_add_work);
        mIvadd.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_work_title);
        mTvSend = (TextView) findViewById(R.id.tv_work_send);
        mRecycleViewMeetList=(RecyclerView) findViewById(R.id.recy_meet_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MeetListActivity.this);
        mRecycleViewMeetList.setLayoutManager(layoutManager);
        mIvadd.setImageResource(R.mipmap.back);
        mTvTitle.setText("会议");
        mTvSend.setVisibility(View.GONE);
        mRelativeLayout.setOnClickListener(this);
        mTvMeetStatusType.setText("我的发起");
         mStateView = StateView.inject(mContentView);

        initData();
    }

    private void initData() {
        //初始化数据
        mStateView.showLoading();
        PostTaskListJsonBean jsonBean = new PostTaskListJsonBean();
        jsonBean.setApiCode(ApiCode);
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setSystemCurrentUserID(userId);

         String json = GsonUtil.getGson().toJson(jsonBean);
         body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);


        setMeetListData(body);


    }

   /**
    *
    *@author zjq
    *create at 2018/12/14 上午9:51 设置会议列表数据
    */
    private void setMeetListData(final RequestBody body) {
        RetrofitClients.getInstance(this,ip).create(WorkAPi.class)
                .getMeetingList(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .subscribe(new Observer<ResponseMeetingEntity>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(ResponseMeetingEntity entity) {
                                   mStateView.showContent();
                                   if (entity.getRows().size()!=0&&entity!=null){
                                       final List<ResponseMeetingEntity.RowsBean> rows = entity.getRows();
                                       if (adapter!=null){
                                           adapter.replaceData(rows);
                                           adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                   Intent intent =new  Intent(MeetListActivity.this,MeetDetailActivity.class);
                                                   intent.putExtra("meetingDataID",rows.get(position).getMeetingDataID());
                                                   intent.putExtra("meetingID",rows.get(position).getMeetingID());
                                                   startActivity(intent);
                                               }
                                           });
                                       }else{
                                           adapter=new MeetListAdapter(R.layout.item_meet_message,rows);
                                           adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                                   Intent intent =new  Intent(MeetListActivity.this,MeetDetailActivity.class);
                                                   intent.putExtra("meetingDataID",rows.get(position).getMeetingDataID());
                                                   intent.putExtra("meetingID",rows.get(position).getMeetingID());
                                                   startActivity(intent);
                                               }
                                           });

                                           mRecycleViewMeetList.setAdapter(adapter);
                                       }
                                   }else{
                                      mStateView.showEmpty();
                                   }

                               }

                               @Override
                               public void onError(Throwable e) {
                                   mStateView.showRetry();
                                   mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                                       @Override
                                       public void onRetryClick() {
                                           setMeetListData(body);
                                       }
                                   });

                               }

                               @Override
                               public void onComplete() {

                               }



                }
                );





    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if ( i== R.id.rl_meet_type) {
            List<MeetStatusType> data = new ArrayList<>();
            data.add(new MeetStatusType("我的发起"));
            data.add(new MeetStatusType("我的参与"));
            data.add(new MeetStatusType("已发起"));
            data.add(new MeetStatusType("确认"));
            data.add(new MeetStatusType("结束"));
            data.add(new MeetStatusType("取消"));
            String json = GsonUtil.getGson().toJson(data);
            MeetStatusTypeDialog dialog = MeetStatusTypeDialog.newInstance(json);
            dialog.setOnDialogInforCompleted(this);
            dialog.show(getSupportFragmentManager(), "MeetStatusTypeDialog");
        }else if(i==R.id.iv_work_add_work){
            AppManager.getAppManager().finishActivity();
        }
    }


    //event事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky =true)
    public void disRefrsh(MeetDisRefrshDataEvent event){
        if (event.isRefrsh()){

            String name = mTvMeetStatusType.getText().toString();

            getMeetListData(name);


        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky =true)
    public void competeRefrsh(MeetCompeteRefrshDataEvent event){
        String name = mTvMeetStatusType.getText().toString();

        getMeetListData(name);

    }




    //弹窗返回数据
    @Override
    public void dialogInforCompleted(String name) {
        mTvMeetStatusType.setText(name);
        getMeetListData(name);

    }

    private void getMeetListData(String name) {
        switch (name) {
            case "我的发起":
                ApiCode = "GetLaunchMeeting";
                initData();
                break;
            case "我的参与":
                ApiCode = "GetPartakeMeeting";
                initData();
                break;
            case "已发起":
                ApiCode = "GetIssuedMeeting";
                initData();
                break;
            case "确认":
                ApiCode = "GetConfirmMeeting";
                initData();
                break;
            case "结束":
                ApiCode = "GetFinishMeeting";
                initData();
                break;
            case "取消":
                ApiCode = "GetAbolishMeeting";
                initData();
                break;
        }
    }


    //adapt
    public class MeetListAdapter extends BaseQuickAdapter<ResponseMeetingEntity.RowsBean, BaseViewHolder> {
        public MeetListAdapter(int layoutResId, List<ResponseMeetingEntity.RowsBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, ResponseMeetingEntity.RowsBean menuItem) {

            String[] ts = menuItem.getCreateTime().split("T");
            Logger.d(ts[0]+ts[1]);
            String createTime=ts[0]+" "+ts[1];
            baseViewHolder.setText(R.id.tv_item_meet_message_time,createTime);//会议创建时间
            baseViewHolder.setText(R.id.tv_meet_message_content, menuItem.getMeetingContent());//会议内容
            String startDate[] = menuItem.getStartDate().split("T");
            String time1=startDate[0];
            String time3=startDate[1];
            String time=time1+" "+time3;

            //

            baseViewHolder.setText(R.id.tv_item_meet_message_count_down,getMeetStatus(menuItem.getMeetingStatus()));//会议状态
            TextView  tvStatus= baseViewHolder.itemView.findViewById(R.id.tv_item_meet_message_count_down);

            if (menuItem.getMeetingStatus()==1){
                //进行
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.title_bg)
                        .radius(10)
                        .into(tvStatus);
                tvStatus.setTextColor(getResources().getColor(R.color.title_bg));
            }else if (menuItem.getMeetingStatus()==2){
                //确认
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.green)
                        .radius(10)
                        .into(tvStatus);
                tvStatus.setTextColor(getResources().getColor(R.color.green));
            }else if (menuItem.getMeetingStatus()==3){
                //完成
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.red)
                        .radius(10)
                        .into(tvStatus);
                tvStatus.setTextColor(getResources().getColor(R.color.red));
            }else{
                //取消
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.gray)
                        .radius(10)
                        .into(tvStatus);
                tvStatus.setTextColor(getResources().getColor(R.color.gray));

            }
            baseViewHolder.setText(R.id.tv_item_meet_message_open_time, TimeUtil.getFormatData(time));//会议开始时间
            baseViewHolder.setText(R.id.tv_item_meet_message_address, menuItem.getMeetingPlace());//会议地址
            baseViewHolder.setText(R.id.tv_item_meet_message_originator, menuItem.getCreateUserName());//会议创建人
            baseViewHolder.setText(R.id.tv_item_meet_message_dynamic, menuItem.getCount()+"");//会议动态
            baseViewHolder.setText(R.id.tv_item_meet_message_total, menuItem.getParticipantsCount()+"");//会议参与人

        }
    }



    public String getMeetStatus(int i){
        String name="";
        switch (i){
            case 1:
                name="已发起";
                break;

            case 2:
                name="已确认";
                break;

            case 3:
                name="已结束";
                break;

            case 4:
                name="已取消";
                break;

        }

      return  name;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
