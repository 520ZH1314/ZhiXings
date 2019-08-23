package com.shuben.zhixing.www.inspection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.TypeInfo;
import com.shuben.zhixing.www.inspection.fragment.InspectionFragment01;
import com.shuben.zhixing.www.inspection.fragment.InspectionFragment02;
import com.shuben.zhixing.www.inspection.fragment.InspectionFragment03;
import com.shuben.zhixing.www.wheelview.InspectionTab;

public class InspectionActivity extends BaseActvity {

    public InspectionTab tabIndicator;
    private Fragment mainContent0,
            mainContent1,
            mainContent2;
    private int CONTENT = R.id.fragment_layout;
    public   final String fragment0Tag = "fragment0";
    public   final String fragment1Tag = "fragment1";
    public   final String fragment2Tag = "fragment2";
    private FragmentManager fragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_inspection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);

//        if (savedInstanceState == null) {
          
            mainContent0 = fragmentManager.findFragmentByTag(fragment0Tag);
            mainContent1 = fragmentManager.findFragmentByTag(fragment1Tag);
           /// mainContent2 = fragmentManager.findFragmentByTag(fragment2Tag);

//        }



    }

    /**
     * 接收handler消息处理方法
     *
     * @param msg
     */
    @Override
    public void process(Message msg) {

    }

    /**
     * 空间数据初始化方法
     */
    @Override
    public void initLayout() {
        Intent intent = getIntent();
        if(intent.hasExtra("des")){
            des = intent.getIntExtra("des",0);
        }
        initFragments();

        index = intent.getIntExtra("index",0);
        if(index==0){
            info = (TypeInfo) intent.getSerializableExtra("obj");
        }

        tabIndicator.setTab(index);
    }

    TypeInfo info;
    private  int index,des;
    @SuppressLint("NewApi")
    private void initFragments(){

//        final FragmentManager fragmentManager = getSupportFragmentManager();
        tabIndicator = (InspectionTab) findViewById(R.id.tab_indicator);
        tabIndicator.setOnSelectListener(new InspectionTab.OnSelectListener() {
            public void onSelect(int i) {
                /*FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_layout, fragments[i]);
                transaction.addToBackStack(null);
                transaction.commit();*/
                select(i);
            }
        });



    }

    public void select(int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mainContent0 = fragmentManager.findFragmentByTag(fragment0Tag);
        mainContent1 = fragmentManager.findFragmentByTag(fragment1Tag);
       // mainContent2 = fragmentManager.findFragmentByTag(fragment2Tag);

        if (mainContent0 != null) {
            transaction.hide(mainContent0);
        }
        if (mainContent1 != null) {
            transaction.hide(mainContent1);
        }
        if (mainContent2 != null) {
            transaction.hide(mainContent2);
        }
        switch (checkedId) {
            case 0:
                if (mainContent0 == null) {
                    mainContent0 = new InspectionFragment01();

                    ((InspectionFragment01) mainContent0).setObj(info);
                    transaction.add(CONTENT, mainContent0, fragment0Tag);
                } else {
                    transaction.show(mainContent0);
                }
                break;
            case 1:
                if (mainContent1 == null) {
                    mainContent1 = new InspectionFragment02();
                    ((InspectionFragment02) mainContent1).setDes(des);
                    transaction.add(CONTENT, mainContent1, fragment1Tag);
                } else {
                    transaction.show(mainContent1);
                }
                break;
            case 2:
                if (mainContent2 == null) {
                    mainContent2 = new InspectionFragment03( );
                    transaction.add(CONTENT, mainContent2, fragment2Tag);
                } else {
                    transaction.show(mainContent2);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }



}
