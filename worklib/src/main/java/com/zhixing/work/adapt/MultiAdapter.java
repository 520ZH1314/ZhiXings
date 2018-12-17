package com.zhixing.work.adapt;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.MySendWorkEntity;
import com.zhixing.work.bean.ResponseMeetingEntity;
import com.zhixing.work.bean.TaskListItemEntity;

import java.util.List;


public class MultiAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(MySendWorkEntity.TYPE_MEET, R.layout.item_meet_message);
        addItemType(MySendWorkEntity.TYPE_TASK, R.layout.item_task_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        MySendWorkEntity Entity = (MySendWorkEntity) item;
        switch (Entity.getItemType()) {
            case MySendWorkEntity.TYPE_MEET:
                ResponseMeetingEntity responseMeetingEntity = Entity.getResponseMeetingEntity();
                List<ResponseMeetingEntity.RowsBean> rows = responseMeetingEntity.getRows();
                for (ResponseMeetingEntity.RowsBean bean : rows) {
                    String[] ts = bean.getCreateTime().split("T");
                    Logger.d(ts[0]+ts[1]);
                    String createTime=ts[0]+" "+ts[1];
                    helper.setText(R.id.tv_item_meet_message_time,createTime);//会议创建时间
                    helper.setText(R.id.tv_meet_message_content, bean.getMeetingContent());//会议内容
                    String startDate[] = bean.getStartDate().split("T");
                    String time1=startDate[0];
                    String time3=startDate[1];
                    String endDate[]=bean.getEndDate().split("T");
                    String time2=endDate[1];
                    String time=time1+" "+time3+"-"+time2;
                    helper.setText(R.id.tv_item_meet_message_count_down,getMeetStatus(bean.getMeetingStatus()));//会议状态
                    helper.setText(R.id.tv_item_meet_message_open_time, time);//会议开始时间
                    helper.setText(R.id.tv_item_meet_message_address, bean.getMeetingPlace());//会议地址
                    helper.setText(R.id.tv_item_meet_message_originator, bean.getCreateUserName());//会议创建人
                    helper.setText(R.id.tv_item_meet_message_dynamic, bean.getCount()+"");//会议动态
                    helper.setText(R.id.tv_item_meet_message_total, bean.getHostCount()+bean.getParticipantCount()+bean.getRecorderCount()+"");//会议参与人
                }



            case MySendWorkEntity.TYPE_TASK:
                TaskListItemEntity taskListItemEntity = Entity.getTaskListItemEntity();
                List<TaskListItemEntity.RowsBean> rows1 = taskListItemEntity.getRows();
                for (TaskListItemEntity.RowsBean taskBean: rows1) {
                    helper.setText(R.id.tv_meet_title, taskBean.getTitle());//任务标题
                    String[] createtime = taskBean.getCreateDate().split("T");
                    String time1=createtime[0]+" "+createtime[1];
                    helper.setText(R.id.tv_item_task_message_time, time1);//任务创建时间
                    helper.setText(R.id.tv_task_message_content, taskBean.getContents());//任务内容
                    String[] createtime1 = taskBean.getDueDate().split("T");
                    String time2=createtime[0]+" "+createtime[1];
                    helper.setText(R.id.tv_item_task_message_open_time, time2);//任务开始时间
                    helper.setText(R.id.tv_item_task_message_originator, taskBean.getCreateUserName());//任务责任人
                    helper.setText(R.id.tv_item_task_message_status, taskBean.getTaskStatusName());//任务状态
                    helper.setText(R.id.tv_item_task_message_dynamic,taskBean.getCount()+"");//任务动态
                }
        }
    }
    public String getMeetStatus(int i){
        String name="";
        switch (i){
            case 1:
                name="已发起";
                break;

            case 2:
                name="已确认";
                break;

            case 3:
                name="已结束";
                break;

            case 4:
                name="已取消";
                break;

        }

        return  name;

    }

    }

