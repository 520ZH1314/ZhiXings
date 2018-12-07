package com.shuben.zhixing.www;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.fragment.Fragment01;
import com.shuben.zhixing.www.fragment.Fragment02;
import com.shuben.zhixing.www.fragment.Fragment03;
import com.shuben.zhixing.www.fragment.Fragment04;
import com.shuben.zhixing.www.util.LTBAlertDialog;
import com.base.zhixing.www.util.SharedPreferencesTool;

import java.util.Timer;

public class NavigationActivity extends BaseActvity implements View.OnClickListener {

    public TabIndicator tabIndicator;
    private int isExit = 0;
    private Timer mTimer;
    private TextView tv_dialog_commit_but,tv_dialog_i_know_but,tx_content_info;
    private LTBAlertDialog ltbAlertDialog;
    private SharedPreferencesTool sharedPreferencesTool;
    private String tab_num;

    private Fragment mainContent0,
            mainContent1,
            mainContent2,
            mainContent3;
    public   final String fragment0Tag = "fragment0";
    public   final String fragment1Tag = "fragment1";
    public   final String fragment2Tag = "fragment2";
    public   final String fragment3Tag = "fragment3";

    @Override
    public int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);

       /* if (savedInstanceState == null) {
            mainContent0 = fragmentManager.findFragmentByTag(fragment0Tag);
            mainContent1 = fragmentManager.findFragmentByTag(fragment1Tag);
            mainContent2 = fragmentManager.findFragmentByTag(fragment2Tag);
            mainContent3 = fragmentManager.findFragmentByTag(fragment3Tag);
        }*/


    }


    private FrameLayout main_content;
    private FragmentManager fragmentManager;
    private int CONTENT = R.id.fragment_layout;
    @SuppressLint("NewApi")
    private void initFragments(){


        tabIndicator = (TabIndicator) findViewById(R.id.tab_indicator);
        tabIndicator.setOnSelectListener(new TabIndicator.OnSelectListener() {
            public void onSelect(int i) {
                select(i);
            }
        });
        tabIndicator.setTab(0);
    }

    public void select(int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mainContent0 = fragmentManager.findFragmentByTag(fragment0Tag);
        mainContent1 = fragmentManager.findFragmentByTag(fragment1Tag);
        mainContent2 = fragmentManager.findFragmentByTag(fragment2Tag);
        mainContent3 = fragmentManager.findFragmentByTag(fragment3Tag);
        if (mainContent0 != null) {
            transaction.hide(mainContent0);
        }
        if (mainContent1 != null) {
            transaction.hide(mainContent1);
        }
        if (mainContent2 != null) {
            transaction.hide(mainContent2);
        }
        if (mainContent3 != null) {
            transaction.hide(mainContent3);
        }



        switch (checkedId) {

            case 0:

                if (mainContent0 == null) {
                    mainContent0 = new Fragment01();
                    transaction.add(CONTENT, mainContent0, fragment0Tag);
                } else {
                    transaction.show(mainContent0);
                }

                break;
            case 1:

                if (mainContent1 == null) {
                    mainContent1 = new Fragment02();
                    transaction.add(CONTENT, mainContent1, fragment1Tag);
                } else {
                    transaction.show(mainContent1);
                }



                break;
            case 2:



                if (mainContent2 == null) {
                    mainContent2 = new Fragment03( );
                    transaction.add(CONTENT, mainContent2, fragment2Tag);
                } else {
                    transaction.show(mainContent2);
                }

                break;

            case 3:


                if (mainContent3 == null) {
                    mainContent3 = new Fragment04( );
                    transaction.add(CONTENT, mainContent3, fragment3Tag);

                } else {
                    transaction.show(mainContent3);
                }

                break;
            default:
                break;
        }
        transaction.commit();
    }


    private   int INDEX = 0;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //   super.onSaveInstanceState(outState);
        // 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
        // super.onSaveInstanceState(outState);
        outState.putInt("index", INDEX);



    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        INDEX = savedInstanceState.getInt("index");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {



        if (keyCode == KeyEvent.KEYCODE_BACK) {
          /*  if (isExit == 0) {

                Toast.makeText(NavigationActivity.this, "再点一下退出....", Toast.LENGTH_SHORT).show();
                Log.e("Text","11111111111111111111111111111111111111");

                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						isExit = 0;
					}
				}, 2000);

                isExit++;
            } else if (isExit == 1) {
//                mStool.savePassword("");
                ActivityCollector.finishAll();
            }*/

        }
        return false;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        sharedPreferencesTool = new SharedPreferencesTool(this);
        initFragments();
    }

    //弹出框
    private void showRefundDialogs(String mIkowButStr, String mCommitButStr, String message) {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_title,null);
        tv_dialog_i_know_but= (TextView) layout.findViewById(R.id.tv_dialog_i_know_but);
        tv_dialog_commit_but= (TextView) layout.findViewById(R.id.tv_dialog_commit_but);
        tx_content_info= (TextView) layout.findViewById(R.id.tx_content_info);
        if(tv_dialog_i_know_but!=null){
            tv_dialog_i_know_but.setOnClickListener(this);
        }
        if(tv_dialog_commit_but!=null){
            tv_dialog_commit_but.setOnClickListener(this);
        }
        tx_content_info.setText(message);
        ltbAlertDialog = LTBAlertDialog.getLtbAlertDialog(this, false, false);
        ltbAlertDialog.setViewContainer(layout).show();
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_dialog_i_know_but:
                if (ltbAlertDialog != null) {
                    ltbAlertDialog.dismiss();
                 //弹出框确定

                }
                break;

            case R.id.tv_dialog_commit_but:
                if (ltbAlertDialog != null) {
                    ltbAlertDialog.dismiss();
                }

                //弹出框取消
                break;
        }
    }
}
