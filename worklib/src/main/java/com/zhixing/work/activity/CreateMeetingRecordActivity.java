package com.zhixing.work.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.work.R;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.PostCreateMeetRecordJson;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * @author zjq
 * create at 2018/12/11 下午5:17 创建会议纪要
 */
public class CreateMeetingRecordActivity extends BaseActvity {


    private ImageView mImage;
    private TextView mTvSend;
    private TextView mTvTitle;
    private String tenantId;
    private String userId;
    private String meetId;
    private EditText mEditText;
    private String ip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_meeting_record;

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        userId = SharedPreferencesTool.getMStool(this).getUserId();
         ip = SharedPreferencesTool.getMStool(this).getIp();
        meetId = SharedPreferencesTool.getMStool(this).getString("MeetId");
        mEditText = (EditText) findViewById(R.id.ed_create_meet_record);

        mImage = (ImageView) findViewById(R.id.iv_work_add_work);
        mTvSend = (TextView) findViewById(R.id.tv_work_send);
        mTvTitle = (TextView) findViewById(R.id.tv_work_title);
        mTvTitle.setText("会议纪要");
        mImage.setImageResource(R.drawable.task_left);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });

        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送会议纪要
                sendMeetRecordMessage();
            }
        });

    }


    /**
     * @author zjq
     * create at 2018/12/11 下午5:23 联网发送会议纪要
     */
    private void sendMeetRecordMessage() {
        String trim = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toasty.INSTANCE.showToast(this, "内容不能为空");
        } else {
            PostCreateMeetRecordJson postCreateMeetRecordJson = new PostCreateMeetRecordJson();
            postCreateMeetRecordJson.setApiCode("EditMeetingDetails");
            postCreateMeetRecordJson.setAppCode("CEOAssist");
            PostCreateMeetRecordJson.RowsBean rowsBean = new PostCreateMeetRecordJson.RowsBean();
            PostCreateMeetRecordJson.RowsBean.ListBean ListBean = new PostCreateMeetRecordJson.RowsBean.ListBean();
            PostCreateMeetRecordJson.RowsBean.ListBean.InsertedBean insertedBean = new
                    PostCreateMeetRecordJson.RowsBean.ListBean.InsertedBean(meetId, trim, userId, userId, userId, tenantId);

            List<PostCreateMeetRecordJson.RowsBean.ListBean.InsertedBean> bean = new ArrayList<>();
            bean.add(insertedBean);
            ListBean.setInserted(bean);
            rowsBean.setList(ListBean);
            postCreateMeetRecordJson.setRows(rowsBean);
            String json = GsonUtil.getGson().toJson(postCreateMeetRecordJson);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

            RetrofitClients.getInstance(this,ip).create(WorkAPi.class)
                    .addMeetingRecord(body)
                    .compose(RxUtils.schedulersTransformer())  // 线程调度
                    .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("加载中");
                        }
                    }).subscribe(new MyBaseSubscriber<CreateTaskEntity>(this) {
                @Override
                public void onResult(CreateTaskEntity o) {
                   dismissDialog();
                   if (o.isStatus()){
                       //发送成功
                       AppManager.getAppManager().finishActivity();
                       //并且传递数据

                   }

                }

                @Override
                public void onError(ResponseThrowable e) {
                       dismissDialog();
                }
            });

        }

    }
}
