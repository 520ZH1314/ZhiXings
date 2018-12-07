package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.SysUtils;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

public class TestGridViewAdapter extends BaseAdapter {

	Activity context;
	List<String> list;
	public Bitmap bitmaps[];
	private FinalBitmap finalImageLoader;
	private int wh;
	
	public TestGridViewAdapter(Activity context,List<String> data) {
		this.context=context;
		this.wh=(SysUtils.getScreenWidth(context)-SysUtils.Dp2Px(context, 99))/5;
		this.list=data;
		this.finalImageLoader=FinalBitmap.create(context);
		this.finalImageLoader.configLoadingImage(R.drawable.ic_empty);//ͼƬ�������ǰ��ʾ��ͼƬ
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		Holder holder;
		if (view==null) {
			view=LayoutInflater.from(context).inflate(R.layout.item_image, null);
			holder=new Holder();
			holder.imageView=(ImageView) view.findViewById(R.id.imageView);
			view.setTag(holder);
		}else {
			holder= (Holder) view.getTag();
		}
		finalImageLoader.display(holder.imageView, list.get(position));
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(wh,wh);
        view.setLayoutParams(param);
		return view;
	}
	
	class Holder{
		ImageView imageView;
	}
	
	
}
