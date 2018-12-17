package com.zhixing.work.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MySendWorkEntity implements MultiItemEntity {
    public static final int TYPE_MEET = 1;
    public static final int TYPE_TASK = 2;
    private int itemType;
    public ResponseMeetingEntity responseMeetingEntity;

    public ResponseMeetingEntity getResponseMeetingEntity() {
        return responseMeetingEntity;
    }

    public void setResponseMeetingEntity(ResponseMeetingEntity responseMeetingEntity) {
        this.responseMeetingEntity = responseMeetingEntity;
    }

    public TaskListItemEntity getTaskListItemEntity() {
        return taskListItemEntity;
    }

    public void setTaskListItemEntity(TaskListItemEntity taskListItemEntity) {
        this.taskListItemEntity = taskListItemEntity;
    }

    public TaskListItemEntity taskListItemEntity;


    @Override
    public int getItemType() {
        return this.itemType;
    }
}
