package com.shuben.zhixing.www.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Geyan on 2018/5/31.
 */

public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        /**
         * 使用expandSpec代替heightMeasureSpec。很容易理解，就是我们改变了的GridView的高度获取方式。
         *
         * int size:表示父布局提供给你的大小参考
         * int mode:表示规格，有EXACTLY、AT_MOST、UNSPECIFIED三种。
         * 代码中填的两个值的作用
         * Integer.MAX_VALUE >> 2：表示父布局给的参考的大小无限大。（listview无边界）
         * MeasureSpec.AT_MOST：表示根据布局的大小来确定listview最终的高度，也就是有多少内容就显示多高。
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}