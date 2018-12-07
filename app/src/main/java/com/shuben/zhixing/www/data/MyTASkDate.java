package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class MyTASkDate  implements Serializable {

    private String ToDoListId="";//任务ID
    private String CreateUserId="";//发起人ID
    private String ToDoUserId="";//责任人ID
    private String TaskNo="";//任务编号

    private String Title="";//任务内容
    private String CreateDate="";//创建时间
    private String CreateDept="";//发起部门
    private String User="";//发起人或责任人
    private String Source="";//任务来源
    private int IsRead=0;//是否已读
    private String TaskStatusName="";//任务状态

    public MyTASkDate() {
        super();
    }

    public MyTASkDate(String toDoListId, String createUserId, String toDoUserId, String taskNo, String title, String createDate, String createDept, String user, String source, int isRead, String taskStatusName) {
        ToDoListId = toDoListId;
        CreateUserId = createUserId;
        ToDoUserId = toDoUserId;
        TaskNo = taskNo;
        Title = title;
        CreateDate = createDate;
        CreateDept = createDept;
        User = user;
        Source = source;
        IsRead = isRead;
        TaskStatusName = taskStatusName;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public String getToDoListId() {
        return ToDoListId;
    }

    public void setToDoListId(String toDoListId) {
        ToDoListId = toDoListId;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String createUserId) {
        CreateUserId = createUserId;
    }

    public String getToDoUserId() {
        return ToDoUserId;
    }

    public void setToDoUserId(String toDoUserId) {
        ToDoUserId = toDoUserId;
    }

    public String getTaskNo() {
        return TaskNo;
    }

    public void setTaskNo(String taskNo) {
        TaskNo = taskNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public int isRead() {
        return IsRead;
    }

    public void setRead(int read) {
        IsRead = read;
    }

    public String getTaskStatusName() {
        return TaskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        TaskStatusName = taskStatusName;
    }
}
