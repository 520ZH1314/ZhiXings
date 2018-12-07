package com.shuben.zhixing.www.util;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;


/**
 * 自定义提示框 <br/>
 */
public class IPQCLTBAlertDialog extends Dialog {

    private static final String TAG = IPQCLTBAlertDialog.class.getSimpleName();
    private Context mContext;
    private static IPQCLTBAlertDialog ltbAlertDialog = null;
    private TextView tv_dialog_title;
    private TextView tv_dialog_content;
    private TextView tv_dialog_cancel;
    private TextView tv_dialog_confirm;
    private LinearLayout llyt_container;
    private View viewContainer;

    private String confirmText = "";
    private String cancelText = "";
    private String title = "";
    private String message = "";
    /**
     * 确定按钮监听
     */
    private View.OnClickListener onPositiveClickListener;
    /**
     * 取消按钮监听
     */
    private View.OnClickListener onNegaClickListener;
    private IPQCLTBAlertDialog(Context context) {
        super(context);
        setOwnerActivity((Activity) context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ipqc_activity_dialog_alert_content);
        initView();
        initData();

        if (ltbAlertDialog != null) {
            // 位置
            Window window = ltbAlertDialog.getWindow();
            window.setGravity(Gravity.CENTER); // dialog显示的位置
            window.setWindowAnimations(R.style.AnimBottom); // 添加动画
            window.setBackgroundDrawableResource(R.color.gray);
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static IPQCLTBAlertDialog getLtbAlertDialog(Context context) {
        if (ltbAlertDialog != null) {
            ltbAlertDialog.dismiss();
        }
        ltbAlertDialog = new IPQCLTBAlertDialog(context);
        ltbAlertDialog.setCancelable(false);
        ltbAlertDialog.setCanceledOnTouchOutside(false);

        return ltbAlertDialog;
    }

    /**
     *
     * @param context
     * @param cancelable
     * @param canceledOnTouchOutside
     * @return
     */
    public static IPQCLTBAlertDialog getLtbAlertDialog(Context context, boolean cancelable, boolean canceledOnTouchOutside) {
        if (ltbAlertDialog != null&& ltbAlertDialog.isShowing()) {
            ltbAlertDialog.dismiss();
        }
        ltbAlertDialog = new IPQCLTBAlertDialog(context);
        ltbAlertDialog.setCancelable(cancelable);
        ltbAlertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        return ltbAlertDialog;
    }

    private void initView() {
        tv_dialog_title = (TextView) findViewById(R.id.tv_dialog_title);
        tv_dialog_content = (TextView) findViewById(R.id.tv_dialog_content);
        tv_dialog_cancel = (TextView) findViewById(R.id.tv_dialog_cancel);
        tv_dialog_confirm = (TextView) findViewById(R.id.tv_dialog_confirm);
        llyt_container = (LinearLayout) findViewById(R.id.llyt_container);
    }

    private void initData() {
        // 各个控件如果没有设置就将他们隐藏
        if (title != null && !"".equals(title)) {
            tv_dialog_title.setText(title);
        } else {
            tv_dialog_title.setVisibility(View.GONE);
        }
        if (message != null && !"".equals(message)) {
            tv_dialog_content.setText(message);
        } else {
            tv_dialog_content.setVisibility(View.GONE);
        }

        if (viewContainer == null) {
            llyt_container.setVisibility(View.GONE);
        } else {
            llyt_container.addView(viewContainer);
        }

        if (onPositiveClickListener == null) {
            tv_dialog_confirm.setVisibility(View.GONE);
        } else {
            tv_dialog_confirm.setText(confirmText);
            tv_dialog_confirm.setOnClickListener(onPositiveClickListener);
        }
        if (onNegaClickListener == null) {
            tv_dialog_cancel.setVisibility(View.GONE);
        } else {
            tv_dialog_cancel.setText(cancelText);
            tv_dialog_cancel.setOnClickListener(onNegaClickListener);
        }
    }

    /**
     * 确定按钮
     * @param character
     * @param onPositiveClickListener
     */
    public IPQCLTBAlertDialog setOnPositiveClickListener(String character, View.OnClickListener onPositiveClickListener) {
        confirmText = character;
        this.onPositiveClickListener = onPositiveClickListener;
        return ltbAlertDialog;
    }

    /**
     * 取消按钮
     * @param character

     */
    public IPQCLTBAlertDialog setOnNegativeClickListener(String character, View.OnClickListener onNegativeClickListener) {
        cancelText = character;
        this.onNegaClickListener = onNegativeClickListener;
        return ltbAlertDialog;
    }

    /**
     * 标题
     * @param title
     */
    public IPQCLTBAlertDialog setTitle(String title) {
        this.title = title;
        return ltbAlertDialog;
    }


    /**
     * 提示内容
     * @param message
     */
    public IPQCLTBAlertDialog setMessage(String message) {
        this.message = message;
        return ltbAlertDialog;
    }

    /**
     * 设置自定义View
     * @param viewContainer
     */
    public IPQCLTBAlertDialog setViewContainer(View viewContainer) {
        this.viewContainer = viewContainer;
//        viewContainer.setBackgroundColor(mContext.getResources().getColor(R.color.white_35));
        return ltbAlertDialog;
    }
}
