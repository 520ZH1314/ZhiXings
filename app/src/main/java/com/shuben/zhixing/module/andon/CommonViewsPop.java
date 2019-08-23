package com.shuben.zhixing.module.andon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.widget.IDialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.common.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CommonViewsPop {

    private Activity context;
    private Handler handler;
    private IDialog dlg;

    private LayoutInflater inflater;
    private List<String> list;
    public CommonViewsPop(Activity context , Handler handler,List<String> list) {
        this.context = context;
        this.handler = handler;
        this.list = list;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private List<ImageView> ivList =new ArrayList<>();
    private ImagesBannerAdapter bannerAdapter;
    private void createImageViews(List<String> list) {
        for (int i = 0; i < list.size(); i++) {

            ImageView imageView1 = new ImageView(context);
           // imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView1.setLayoutParams(params);
               //网络
            MyImageLoader.load(context,list.get(i),imageView1);
            
            ivList.add(imageView1);
        }
    }
    private void setViewPagerChangeListener() {
        vp_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (ivList == null || ivList.size() == 0) return;
                int newPosition = position % bannerCount;
                for (int i = 0; i < bannerCount; i++) {
                    llIndexContainer.getChildAt(i).setEnabled(i == newPosition);
                }
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    // 添加指示图标
    private void addIndicatorImageViews() {
        llIndexContainer.removeAllViews();
        if (bannerCount < 2) return;
        for (int i = 0; i < bannerCount; i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
            lp.leftMargin = DensityUtil.dip2px(context, (i == 0) ? 0 : 7);
            iv.setLayoutParams(lp);
            iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
            iv.setEnabled(i == 0);
            llIndexContainer.addView(iv);
        }
    }
    int bannerCount = 0;
    private void dealWithTheView(List<String> list) {
        // ivList.clear();

       /* bannerCount = list.size();
        if (bannerCount == 2) {
            list.addAll(list);
        }*/
        bannerCount = list.size();
        createImageViews(list);

        bannerAdapter = new ImagesBannerAdapter(ivList,this);
        vp_banner.setAdapter(bannerAdapter);

        addIndicatorImageViews();
        setViewPagerChangeListener();

    }

    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
    private LinearLayout llIndexContainer;
    private  ViewPager vp_banner;
    public void showSheet(int CURRENT){

        //定位开始
        dos(CURRENT);
    }
    public void showSheet(){
         //默认开始
        dos(-1);
    }
    private void dos(int CURRENT){
        dlg = new IDialog(context, R.style.load_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.common_view_images, null);
       TextView down = layout.findViewById(R.id.down);
        View child = layout.findViewById(R.id.child);
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(DensityUtil.getWindowWidth(context), LinearLayout.LayoutParams.MATCH_PARENT);
        child.setLayoutParams(params);
        vp_banner = layout.findViewById(R.id.vp_banner);
        llIndexContainer = layout.findViewById(R.id.ll_index_container);
        dealWithTheView(list);
        if(CURRENT!=-1){
            vp_banner.setCurrentItem(CURRENT);
        }

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()!=0){


                }

            }
        });

        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancle();
            }
        });
        dlg.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setCanceledOnTouchOutside(true);
        dlg.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        /*Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;

        lp.y = 0;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);*/
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout);
        dlg.show();
    }



    public static  Dialog showSheet(Activity context,LayoutInflater inflater) {

          final IDialog dlg = new IDialog(context, R.style.load_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.common_view_images, null);
        View child = layout.findViewById(R.id.child);
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(DensityUtil.getWindowWidth(context),DensityUtil.getWindowHeight(context));
        child.setLayoutParams(params);
        ViewPager vp_banner = layout.findViewById(R.id.vp_banner);
        LinearLayout llIndexContainer = layout.findViewById(R.id.ll_index_container);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
        dlg.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setCanceledOnTouchOutside(true);
        dlg.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
       Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout);
        dlg.show();
      /*  dlg.full();*/
        return dlg;
    }



}
