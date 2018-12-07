package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class NotificationData implements Serializable{
    private String NoticeId="";//通知ID
    private String Title="";//任务内容
    private String TaskNo="";//任务编号
    private String CreateDate="";//创建时间
    private String CreateDept="";//发起部门
    private String CreateUser="";//发起人
    private int Source=0;//任务来源
    private String IsRead="";//是否已读

    public NotificationData() {
    }

    public NotificationData(String noticeId, String title, String taskNo, String createDate, String createDept, String createUser, int source, String isRead) {
        NoticeId = noticeId;
        Title = title;
        TaskNo = taskNo;
        CreateDate = createDate;
        CreateDept = createDept;
        CreateUser = createUser;
        Source = source;
        IsRead = isRead;
    }

    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String noticeId) {
        NoticeId = noticeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTaskNo() {
        return TaskNo;
    }

    public void setTaskNo(String taskNo) {
        TaskNo = taskNo;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateDept() {
        return CreateDept;
    }

    public void setCreateDept(String createDept) {
        CreateDept = createDept;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public int getSource() {
        return Source;
    }

    public void setSource(int source) {
        Source = source;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }
}
