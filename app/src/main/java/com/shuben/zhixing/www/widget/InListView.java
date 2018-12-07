package com.shuben.zhixing.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class InListView extends ListView {
    public InListView(Context context) {
        super(context);
    }

    public InListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
