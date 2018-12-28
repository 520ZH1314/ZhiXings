package com.zhixing.tpmlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/*
 * @Author smart
 * @Date 2018/12/25
 * @Des 自定义GridView防止出现滚动条
 */
public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
