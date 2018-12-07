package com.shuben.zhixing.www.reminder;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.reminder.r_fragment.CreateReminderss_CaiGouFragment;
import com.shuben.zhixing.www.reminder.r_fragment.MyTask_CaiGouFragment;
import com.shuben.zhixing.www.reminder.r_fragment.QueryFragment;
import com.shuben.zhixing.www.reminder.r_fragment.StatisticsFragment;
import com.shuben.zhixing.www.util.LTBAlertDialog;
import com.base.zhixing.www.util.SharedPreferencesTool;

import java.util.Timer;
import java.util.TimerTask;

//采购催单
public class Reminder_CaiGouActivity extends BaseActvity implements View.OnClickListener {
    private Fragment[] fragments;
    public ReminderTabIndicator tabIndicator;
    private int isExit = 0;
    private Timer mTimer;
    private TextView tv_dialog_commit_but,tv_dialog_i_know_but,tx_content_info;
    private LTBAlertDialog ltbAlertDialog;
    private SharedPreferencesTool sharedPreferencesTool;
    private String tab_num;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reminder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }
    @SuppressLint("NewApi")
    private void initFragments(){
        fragments = new Fragment[4];
        fragments[0] = new CreateReminderss_CaiGouFragment();
        fragments[1] = new MyTask_CaiGouFragment();
        fragments[2] = new StatisticsFragment(2);
        fragments[3] = new QueryFragment(2);
        final FragmentManager fragmentManager = getFragmentManager();
        tabIndicator = (ReminderTabIndicator) findViewById(R.id.tab_indicator);
        tabIndicator.setOnSelectListener(new ReminderTabIndicator.OnSelectListener() {
            public void onSelect(int i) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_layout, fragments[i]);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tabIndicator.setTab(getIntent().getIntExtra("tag",0));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == 0) {
                Toast.makeText(Reminder_CaiGouActivity.this, "再点一下退出", Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(this,NavigationActivity.class);
                startActivity(intent);*/
                finish();
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
                AppManager.getAppManager().finishAllActivity();
            }
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
