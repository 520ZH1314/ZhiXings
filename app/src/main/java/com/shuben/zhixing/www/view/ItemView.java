package com.shuben.zhixing.www.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.StandardInfo;
import com.shuben.zhixing.www.patrol.oneclass.AddLightActivity;
import com.shuben.zhixing.www.patrol.oneclass.AddQuestionActivity;
import com.base.zhixing.www.util.UrlUtil;


/**
 * Created by cmm on 15/10/31.
 */
public class ItemView extends LinearLayout implements View.OnClickListener {

    private int mPosition;
    private View mMoreView;

    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;

    private OnCommentListener mCommentListener;
    private StandardInfo mInfo;
    private  Activity mActivity;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface OnCommentListener {
        void onComment(int position);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mMoreView = findViewById(R.id.bnt_more);
        mMoreView.setOnClickListener(this);
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setCommentListener(OnCommentListener l) {
        this.mCommentListener = l;
    }

    public void setData(StandardInfo info,Activity activity) {
        this.mInfo=info;
        this.mActivity=activity;
        mMoreView.setOnClickListener(this);
    }

    /**
     * 弹出点赞和评论框
     *
     * @param moreBtnView
     */
    private void showMore(View moreBtnView) {

        if (mMorePopupWindow == null) {

            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(R.layout.layout_more, null, false);

            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);

            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();

            View parent = mMorePopupWindow.getContentView();

            TextView tx_question = (TextView) parent.findViewById(R.id.tx_question);
            TextView tx_light = (TextView) parent.findViewById(R.id.tx_light);

            // 点赞的监听器
            tx_light.setOnClickListener(this);
            tx_question.setOnClickListener(this);

        }

        if (mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
        } else {
            int heightMoreBtnView = moreBtnView.getHeight();

            mMorePopupWindow.showAsDropDown(moreBtnView, -mShowMorePopupWindowWidth,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bnt_more) {
            showMore(v);
        } else if (id == R.id.tx_light) {
            Intent intent = new Intent();
            intent.setClass(getContext(), AddLightActivity.class);
            intent.putExtra("RecordId",  mInfo.getItem01());//车间
            intent.putExtra("StandardItemId", mInfo.getItem02());//
            intent.putExtra("ClassId", mInfo.getItem09());//
            intent.putExtra("WorkShopId", mInfo.getItem08());//
            intent.putExtra("ItemType", mInfo.getItem06());//
            mActivity.startActivityForResult(intent,UrlUtil.TwoFindActivity_RequstCode02);
            Toast.makeText(getContext(),"发现亮点",Toast.LENGTH_LONG).show();
            if (mCommentListener != null) {
                mCommentListener.onComment(mPosition);

                if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                    mMorePopupWindow.dismiss();
                }
            }
        }else if (id == R.id.tx_question) {
            Intent intent = new Intent();
            intent.setClass(getContext(), AddQuestionActivity.class);
            intent.putExtra("RecordId",  mInfo.getItem01());//车间
            intent.putExtra("StandardItemId", mInfo.getItem02());//
            intent.putExtra("WorkShopId", mInfo.getItem08());//
            intent.putExtra("ClassId", mInfo.getItem09());//
            intent.putExtra("ItemType", mInfo.getItem06());//

            mActivity.startActivityForResult(intent, UrlUtil.TwoFindActivity_RequstCode01);
            Toast.makeText(getContext(), "发现问题", Toast.LENGTH_LONG).show();
        }
    }

    public int getPosition() {
        return mPosition;
    }

}
