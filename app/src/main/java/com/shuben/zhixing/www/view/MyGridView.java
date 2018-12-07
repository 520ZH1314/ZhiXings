package com.shuben.zhixing.www.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author ������
 */
public class MyGridView extends GridView {

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

//	// ͨ������dispatchTouchEvent��������ֹ����
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//			Log.e("jj", "����");
//			return false;// ��ֹGridview���л���
//		}
//		return super.dispatchTouchEvent(ev);
//	}

//	/** touch �¼����� **/
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		if (ev.getAction()!= MotionEvent.ACTION_DOWN) {
//			return false;
//		}
//		return super.onTouchEvent(ev);
//	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
