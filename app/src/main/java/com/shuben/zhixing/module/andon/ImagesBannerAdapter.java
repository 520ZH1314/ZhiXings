package com.shuben.zhixing.module.andon;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class ImagesBannerAdapter extends PagerAdapter {

    private List<ImageView> ivList; // ImageView的集合
    private int count; // 广告的数量
    private CommonViewsPop iDialog;
    public ImagesBannerAdapter(List<ImageView> ivList,CommonViewsPop iDialog) {
        super();
        this.ivList = ivList;
        this.iDialog = iDialog;
        if(ivList != null){
            count = ivList.size();
        }
    }

    /**
     * 更新数据
     */
    public void updata(List<ImageView> ivList){
        this.ivList = ivList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(count==0){
            return container;
        }
        int newPosition = position ;
        // 先移除再添加，更新图片在container中的位置（把iv放至container末尾）
        ImageView iv = ivList.get(newPosition);
        container.removeView(iv);
        container.addView(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDialog.cancle();
            }
        });
        return iv;
    }
}
