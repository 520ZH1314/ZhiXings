package com.zhixing.work.adapt;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.base.zhixing.www.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.ResponseMeetDetailEntity;


import java.util.List;

public  class CreateMeetRecordAdapt extends BaseQuickAdapter<ResponseMeetDetailEntity.MeetingDetailsListBean.RowsBeanX, BaseViewHolder> {
        public CreateMeetRecordAdapt(int layoutResId, List<ResponseMeetDetailEntity.MeetingDetailsListBean.RowsBeanX> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder,ResponseMeetDetailEntity.MeetingDetailsListBean.RowsBeanX menuItem) {
            baseViewHolder.setText(R.id.tv_create_meet_record,menuItem.getMeetingDes());
            baseViewHolder.setText(R.id.tv_record_people,"记录人:"+menuItem.getRecorderName());
            String[] ts = menuItem.getCreateTime().split("T");
            String time=ts[0]+" "+ts[1];
            baseViewHolder.setText(R.id.tv_task_crate_time,TimeUtil.getFormatData(time));



        }
    }

