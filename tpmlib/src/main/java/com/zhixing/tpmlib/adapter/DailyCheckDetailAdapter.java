package com.zhixing.tpmlib.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.PictureListActivity;
import com.zhixing.tpmlib.bean.CheckItemEntity;

import java.util.List;
/*
 * @Author smart
 * @Date 2018/12/25
 * @Des 日常点检项的适配器
 */
public class DailyCheckDetailAdapter extends BaseQuickAdapter<CheckItemEntity,BaseViewHolder> {

    public DailyCheckDetailAdapter(List<CheckItemEntity> data) {
        super(R.layout.item_check_item,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CheckItemEntity entity) {
        helper.setText(R.id.tv_check_item, entity.getMacheName());
        if(!TextUtils.isEmpty(entity.getPicNum())){
            helper.setText(R.id.tv_pic_num, "1"+"/"+entity.getPicNum());
        }else{

            helper.setText(R.id.tv_pic_num, "0"+"/"+0);
        }

//        上传图片的点击事件
        helper.setOnClickListener(R.id.btn_upload_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,PictureListActivity.class));

            }
        });
//        查看更多的点击事件
        helper.setOnClickListener(R.id.btn_look_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,PictureListActivity.class));
            }
        });
//        查看缩略图的点击事件
//点击OK的点击事件
        helper.setOnClickListener(R.id.btn_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        helper.setText(R.id.et_hint_result,"OK");
        notifyDataSetChanged();
            }
        });
    }

}
