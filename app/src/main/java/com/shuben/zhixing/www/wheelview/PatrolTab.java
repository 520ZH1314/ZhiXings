package com.shuben.zhixing.www.wheelview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.CustomToast;
import com.shuben.zhixing.www.util.LTBAlertDialog;
import com.base.zhixing.www.util.SharedPreferencesTool;

/**
 * Created by Geyan on 2018/3/13.
 */

public class PatrolTab extends LinearLayout implements View.OnClickListener {


    private SharedPreferencesTool mStool;
    private View mIndicator;
    private RelativeLayout[] tab;
    private ImageView[] tabImage;
    private TextView[] tabText;
    private OnSelectListener mOnSelectListener;
    private int lastTab;
    private int textColor, textColorSelected;
    private int[] tabImageId = {R.mipmap.tab_home,R.mipmap.tab_group,R.mipmap.tab_task,R.mipmap.tab_bill,R.mipmap.tab_set};
    private int[] tabImageSelectedId ={R.mipmap.tab_homeclick,R.mipmap.tab_groupclick,R.mipmap.tab_taskclick,R.mipmap.tab_billclick,R.mipmap.tab_setclick};
    private CustomToast customToast;
    private Activity mActivity;
    private SharedPreferencesTool sharedPreferencesTool;
    private TextView tv_dialog_commit_but,tv_dialog_i_know_but,tx_content_info;
    private LTBAlertDialog ltbAlertDialog;
    public PatrolTab(Context context) {
        super(context);
        mActivity=(Activity) context;
        sharedPreferencesTool = new SharedPreferencesTool(context);
        init();
    }

    public PatrolTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity=(Activity) context;
        sharedPreferencesTool = new SharedPreferencesTool(context);

        init();
    }

    @SuppressLint("NewApi")
    public PatrolTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity=(Activity) context;
        init();
    }

    public void setOnSelectListener(OnSelectListener listener) {
        mOnSelectListener = listener;
    }

    private void init() {
        mStool= SharedPreferencesTool.getMStool(mActivity);

        mIndicator = LayoutInflater.from(getContext()).inflate(R.layout.patrol_tab_indicator, this,
                true);

        tab = new RelativeLayout[5];
        tabImage = new ImageView[5];
        tabText = new TextView[5];

        tab[0] = (RelativeLayout) mIndicator.findViewById(R.id.tab_0);
        tabImage[0] = (ImageView) mIndicator.findViewById(R.id.tab_0_image);
        tabText[0] = (TextView) mIndicator.findViewById(R.id.tab_0_text);

        tab[1] = (RelativeLayout) mIndicator.findViewById(R.id.tab_1);
        tabImage[1] = (ImageView) mIndicator.findViewById(R.id.tab_1_image);
        tabText[1] = (TextView) mIndicator.findViewById(R.id.tab_1_text);

        tab[2] = (RelativeLayout) mIndicator.findViewById(R.id.tab_2);
        tabImage[2] = (ImageView) mIndicator.findViewById(R.id.tab_2_image);
        tabText[2] = (TextView) mIndicator.findViewById(R.id.tab_2_text);

        tab[3] = (RelativeLayout) mIndicator.findViewById(R.id.tab_3);
        tabImage[3] = (ImageView) mIndicator.findViewById(R.id.tab_3_image);
        tabText[3] = (TextView) mIndicator.findViewById(R.id.tab_3_text);

        tab[4] = (RelativeLayout) mIndicator.findViewById(R.id.tab_4);
        tabImage[4] = (ImageView) mIndicator.findViewById(R.id.tab_4_image);
        tabText[4] = (TextView) mIndicator.findViewById(R.id.tab_4_text);


        tab[0].setOnClickListener(this);
        tab[1].setOnClickListener(this);
        tab[2].setOnClickListener(this);
        tab[3].setOnClickListener(this);
        tab[4].setOnClickListener(this);

        lastTab = -1;
        textColor = getResources().getColor(R.color.text_g9);
        textColorSelected = getResources().getColor(R.color.blue);
    }

    public void setTab(int i) {
        if (lastTab == i) {
            return;
        }

        if (lastTab != -1) {
            tabImage[lastTab].setImageResource(tabImageId[lastTab]);
            tabText[lastTab].setTextColor(textColor);
        }

        mOnSelectListener.onSelect(i);
        tabImage[i].setImageResource(tabImageSelectedId[i]);
        tabText[i].setTextColor(textColorSelected);
        lastTab = i;
    }

    @Override
    public void onClick(View v) {
        customToast =new CustomToast();
        switch (v.getId()) {
            case R.id.tab_0:
                setTab(0);
                break;
            case R.id.tab_1:
                setTab(1);
                break;
            case R.id.tab_2:
                setTab(2);
                break;
            case R.id.tab_3:
                setTab(3);
                break;
           case R.id.tab_4:
                setTab(4);
                break;

            case R.id.tv_dialog_commit_but:
                if (ltbAlertDialog != null) {
                    ltbAlertDialog.dismiss();
                }
                break;
        }
    }
    //转入成功弹出框
    private void showRefundDialogs(String mIkowButStr, String mCommitButStr, String message) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_title,null);
        tv_dialog_i_know_but= (TextView) layout.findViewById(R.id.tv_dialog_i_know_but);
        tv_dialog_i_know_but.setVisibility(GONE);
        tv_dialog_commit_but= (TextView) layout.findViewById(R.id.tv_dialog_commit_but);
        tx_content_info= (TextView) layout.findViewById(R.id.tx_content_info);
        if(tv_dialog_i_know_but!=null){
            tv_dialog_i_know_but.setOnClickListener(this);
        }
        if(tv_dialog_commit_but!=null){
            tv_dialog_commit_but.setOnClickListener(this);
        }
        tx_content_info.setText(message);
        ltbAlertDialog = LTBAlertDialog.getLtbAlertDialog(mActivity, false, false);
        ltbAlertDialog.setViewContainer(layout).show();
    }
    public interface OnSelectListener {
        public void onSelect(int i);
    }
}

