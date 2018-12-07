package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{
	//存放图片的id
	private List<ImageView> images;
	private int COUNT = 0;
	public ViewPagerAdapter(Activity activity, List<ImageView> images) {
		// TODO Auto-generated constructor stub
		
		this.images = images;
		COUNT = images.size();
	}

	@Override
	
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		// TODO Auto-generated method stub
		//view.removeView(images.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		// TODO Auto-generated method stub
		int newPosition = position % COUNT;
		// 先移除再添加，更新图片在container中的位置（把iv放至container末尾）
		ImageView iv = images.get(newPosition);
		view.removeView(iv);
		view.addView(iv);

		return iv;
	}
	
}
