package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.reminder.ReminderActivity;
import com.shuben.zhixing.www.reminder.Reminder_CaiGouActivity;
import com.shuben.zhixing.www.util.SelectPicPopupWindow;

/**
 * Created by Administrator on 2017/8/22.
 * 应用广场
 */

public class SquareActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,tetle_tv_ok;
    private RelativeLayout square_cuidan,caigou_cuidan;
    private SelectPicPopupWindow menuWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_square;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        init();
    }

    private void init() {
        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_tv_ok = (TextView) findViewById(R.id.tetle_tv_ok);//title
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("使用说明");
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("应用广场");

        square_cuidan = (RelativeLayout) findViewById(R.id.square_cuidan);//紧急催单
        caigou_cuidan = (RelativeLayout) findViewById(R.id.caigou_cuidan);//采购催单

        setOnclick();
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
        tetle_tv_ok.setOnClickListener(this);
        square_cuidan.setOnClickListener(this);
        caigou_cuidan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
            case R.id.tetle_tv_ok:
                //使用说明
                intent = new Intent(SquareActivity.this,StateActivity.class);
                startActivity(intent);
                break;

            case R.id.square_cuidan:

                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(SquareActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(SquareActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        Intent intent;
        Uri imageUri;
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_cancel:
                    menuWindow.dismiss();
                    break;

                case R.id.btn_gongshang_photo:
                    //内部催单
                    intent = new Intent(SquareActivity.this,ReminderActivity.class);
                    startActivity(intent);

                    break;
                case R.id.btn_zhaoshang_photo:
                    //采购催单
                    intent = new Intent(SquareActivity.this, Reminder_CaiGouActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}
