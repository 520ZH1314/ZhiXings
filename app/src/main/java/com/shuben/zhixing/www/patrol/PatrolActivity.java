package com.shuben.zhixing.www.patrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.fragment.PatrolFragment01;
import com.shuben.zhixing.www.patrol.fragment.PatrolFragment02;
import com.shuben.zhixing.www.patrol.fragment.PatrolFragment03;
import com.shuben.zhixing.www.patrol.fragment.PatrolFragment04;
import com.shuben.zhixing.www.patrol.fragment.PatrolFragment05;
import com.shuben.zhixing.www.wheelview.PatrolTab;


public class PatrolActivity extends FragmentActivity {
    private Fragment[] fragments;
    public PatrolTab tabIndicator;
    private  boolean isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);
        initFragments();
    }
    @SuppressLint("NewApi")
    private void initFragments(){
        fragments = new Fragment[5];
        fragments[0] = new PatrolFragment01();
        fragments[1] = new PatrolFragment02();
        fragments[2] = new PatrolFragment03();
        fragments[3] = new PatrolFragment04();
        fragments[4] = new PatrolFragment05();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        tabIndicator = (PatrolTab) findViewById(R.id.tab_indicator);
        tabIndicator.setOnSelectListener(new PatrolTab.OnSelectListener() {
            public void onSelect(int i) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_layout, fragments[i]);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tabIndicator.setTab(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };

}
