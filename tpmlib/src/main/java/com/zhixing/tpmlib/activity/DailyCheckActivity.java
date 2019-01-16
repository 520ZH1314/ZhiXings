package com.zhixing.tpmlib.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.pullrefreshlayout.PullRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckAdapter;
import com.zhixing.tpmlib.adapter.DialogContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * @Author smart
 * @Date 2018/12/24
 * @Des 进入日常点检的界面
 */
public class DailyCheckActivity extends BaseTpmActivity implements SpringView.OnFreshListener{
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    List<String> datas = new ArrayList<>();
    private DailyCheckAdapter dailyCheckAdapter;
    private TextView tvSure;
    private TextView tvCancel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_check;
    }
    @Override
    public void process(Message msg) {
    }

    @Override
    public void newIniLayout() {
//    初始化数据
        initData();
//        设置item点击事件
        setClickListener();
    }
    List<String> titleList=new ArrayList<>();
    List<String> contentList=new ArrayList<>();
    private void setClickListener() {
        dailyCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                titleList.clear();
                titleList.add("设备编号:");
                titleList.add("当前用户:");
                contentList.clear();
                contentList.add("ZSJ002/卧式注塑机2");
                contentList.add("est1admin/STD");
                showSexTypeDialog(titleList,contentList);
                Toast.makeText(DailyCheckActivity.this,"item点击事件"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSexTypeDialog(List<String> titleList,List<String> contentList) {
        /* 列表弹窗 */
        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.list_planet_dialog, null);
        ListView dialogRecyclerView = (ListView) view.findViewById(R.id.dialog_list);
//        弹出框确定的文本文件
        tvSure = (TextView) view.findViewById(R.id.tv_sure);
//        弹出框取消的文本文件
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        DialogContentAdapter adapter = new DialogContentAdapter(titleList,contentList);
        dialogRecyclerView.setAdapter(adapter);
        dialog.setView(view);
        dialog.show();
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyCheckActivity.this,DailyCheckDetailActivity.class));
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void initData() {
        //设置上下拉事件
        springView.setListener(this);
        //设置springview的头和尾
        //设置上下控件
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        tvTite.setText("日常点检");
        datas.add("注塑机1");
        datas.add("注塑机2");
        datas.add("注塑机3");
        dailyCheckAdapter = new DailyCheckAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(dailyCheckAdapter);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this,"上拉加载更多",Toast.LENGTH_SHORT).show();
       datas.add("塑胶4");
       datas.add("塑胶5");
       datas.add("塑胶6");
        dailyCheckAdapter.notifyDataSetChanged();
        //这个方法就是在刷新或者加载1秒的时间后关闭
        finishFreshAndLoad();
    }
    //来设置刷新时间的
    private void finishFreshAndLoad() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    springView.onFinishFreshAndLoad();
                }
            }, 1000);


    }

    @Override
    public void onLoadmore() {
        datas.add("塑胶7");
        datas.add("塑胶8");
        datas.add("塑胶9");
        dailyCheckAdapter.notifyDataSetChanged();
        finishFreshAndLoad();
        Toast.makeText(this,"下拉加载更多",Toast.LENGTH_SHORT).show();
    }
}
