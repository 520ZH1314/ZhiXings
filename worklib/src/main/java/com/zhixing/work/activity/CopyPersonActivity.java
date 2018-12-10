package com.zhixing.work.activity;

import android.media.Image;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.shuben.contact.lib.event.TypeBean;
import com.zhixing.work.R;
import com.zhixing.work.bean.CopyPeopleBean;
import com.zhixing.work.bean.TaskListItemEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2018/12/10 下午1:47
 * 抄送人列表
 */
public class CopyPersonActivity extends BaseActvity {


    private ImageView mImage;
    private TextView mTvSend;
    private TextView mTvTitle;
    private RecyclerView mRecyCopyList;
    private TaskCopyPeopleListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_copy_person;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        mImage=(ImageView)findViewById(R.id.iv_work_add_work);
        mTvSend=(TextView)findViewById(R.id.tv_work_send);
        mTvTitle=(TextView)findViewById(R.id.tv_work_title);
        mTvTitle.setText("抄送人");
        mTvSend.setVisibility(View.GONE);
        mImage.setImageResource(R.drawable.task_left);

        mRecyCopyList=(RecyclerView) findViewById(R.id.recy_copy_people);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CopyPersonActivity.this);
        mRecyCopyList.setLayoutManager(layoutManager);
        initData();
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });

    }

    private void initData() {


        String copyJson = SharedPreferencesTool.getMStool(this).getString("copyJson");
        Type mType = new TypeToken<List<CopyPeopleBean>>() {
        }.getType();
        List<CopyPeopleBean> ListBeans = GsonUtil.getGson().fromJson(copyJson, mType);
        adapter =new TaskCopyPeopleListAdapter(R.layout.item_compete,ListBeans);
        mRecyCopyList.setAdapter(adapter);
    }


    public class TaskCopyPeopleListAdapter extends BaseQuickAdapter<CopyPeopleBean, BaseViewHolder> {
        public TaskCopyPeopleListAdapter(int layoutResId, List<CopyPeopleBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, CopyPeopleBean menuItem) {
                 baseViewHolder.setText(R.id.tv_item_name,menuItem.getCopyName());

        }
    }


}
