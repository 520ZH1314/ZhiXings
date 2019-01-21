package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.CardAdapter;
import com.zhixing.tpmlib.bean.Section;
import com.zhixing.tpmlib.utils.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DailyCheckItemActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.view_pager)
    ViewPager mViewPager;
    private CardAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_check_item;
    }
    @Override
    public void process(Message msg) {
    }

    @Override
    public void newIniLayout() {
//        初始化数据
        initData();
    }
    private void initData() {
        tvTite.setText("日常点检项");
        mViewPager.setPageTransformer(true,new ScalePageTransformer());
        mAdapter = new CardAdapter(this);
        List<Section> sectionList = createSectionList(20);
        mAdapter.setData(sectionList);
        //在设置数据之后必须得调setAdapter，否则会导致Transform初始状态不正常
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0, true);
    }
    private List<Section> createSectionList(int count) {
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0; i< count; i++) {
            Section section = new Section();
            sectionList.add(section);
        }
        return sectionList;
    }
}
