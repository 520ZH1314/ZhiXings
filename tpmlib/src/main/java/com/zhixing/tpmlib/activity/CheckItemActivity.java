package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.CheckItemAdapter;
import com.zhixing.tpmlib.adapter.DailyCheckDetailAdapter;
import com.zhixing.tpmlib.bean.CheckItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CheckItemActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private CheckItemAdapter checkItemAdapter;
    List<CheckItem> checkItemList=new ArrayList<>();
    @Override
    public void newIniLayout() {
//    初始化数据
        initData();
    }

    private void initData() {
        tvTite.setText("日常点检项");
        CheckItem checkItem=new CheckItem("1");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View headerView=getLayoutInflater().inflate(R.layout.item_check_header, null);
        TextView tv_current_matche = headerView.findViewById(R.id.tv_current_matche);
        //tv_current_matche.setText(equipmentName);
        //tv_cell = headerView.findViewById(R.id.tv_cell);
       // tv_position = headerView.findViewById(R.id.tv_position);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkItemList.add(checkItem);
        checkItemAdapter = new CheckItemAdapter(checkItemList);
        //checkItemAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(checkItemAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_item;
    }

    @Override
    public void process(Message msg) {

    }

}
