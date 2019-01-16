package com.zhixing.tpmlib.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckDetailAdapter;
import com.zhixing.tpmlib.bean.CheckItemEntity;
import com.zhixing.tpmlib.bean.PicEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * @Author smart
 * @Date 2018/12/25
 * @Des 进入日常点检项界面
 */
public class DailyCheckDetailActivity extends BaseTpmActivity implements SpringView.OnFreshListener{
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    //    相册返回图片的列表集合
    private List<String> imageList = new ArrayList<>();
    List<CheckItemEntity> data = new ArrayList<>();
    private DailyCheckDetailAdapter dailyCheckAdapter;
    private static final int REQUEST_IMAGE =1;
    private CheckItemEntity checkItemEntity;
    private CheckItemEntity checkItemEntity1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_check_detail;
        //        初始化EvnentBus

    }

    @Override
    public void process(Message msg) {
    }

    @Override
    public void newIniLayout() {
        EventBus.getDefault().register(this);
//        初始化数据
        initData();
    }

    private void initData() {
        tvTite.setText("日常点检项");
        List<CheckItemEntity> checkItemEntityList=new ArrayList<>();
        checkItemEntity = new CheckItemEntity();
        //设置上下拉事件
        springView.setListener(this);
        //设置springview的头和尾
        //设置上下控件
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        checkItemEntity.setMacheName("注塑机3");
        checkItemEntity1=new CheckItemEntity();
        checkItemEntity.setMacheName("注塑机2");
        data.add(checkItemEntity);
        data.add(checkItemEntity1);
        dailyCheckAdapter = new DailyCheckDetailAdapter(data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(dailyCheckAdapter);
    }

    @Override
    public void onRefresh() {
//        上拉刷新数据
        finishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {
//        下拉加载更多的数据
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
    /**
     * 接收选择筛选返回
     private event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(PicEntity event) {
        //如果是日常检查
        if (event != null) {
            String picNum=event.getPicNum();
            checkItemEntity.setPicNum(picNum);
            dailyCheckAdapter.notifyDataSetChanged();
        }
    }
}
