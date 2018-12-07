package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.2.17	我的组织->部门任务（列表）
 *字段说明：
 {
 "total": 1,
 "page": 1,
 "records": 1,
 "rows": [
 {
 "ToDoListId": "ef708599-504a-4bd3-a0f3-d9933c0d81df",
 "TaskNo": "O170828095355",
 "Title": "内部崔单-华为-P10手机",
 "Contents": null,
 "Source": 3,
 "TaskType": "法",
 "CreateDate": "2017-08-28T09:53:56.313",
 "CreateUserId": "01bc2252-c7ed-4908-8232-46a7952f721d",
 "ToDoUserId": "6057b52f-23ec-408c-be4b-46c891df6571",
 "DoType": 1,
 "DueDate": "2017-08-30T00:00:00",
 "CompleteDate": null,
 "TaskStatus": 0,
 "IsRead": true,
 "EvaluateScore": null,
 "ToDoUser": "陈发扬",
 "ToDoDept": "软件开发部",
 "Cycle": "2",
 "Countdown": 1,
 "TaskStatusName": "进行中"
 }
 ]
 */

public class GetOrganizeToDoList_Data {
    private  String  ToDoListId;
    private  String  TaskNo;
    private  String  Title;
    private  String  Contents;
    private  String  Source;
    private  String  TaskType;
    private  String  CreateDate;
    private  String  CreateUserId;
    private  String  ToDoUserId;
    private  String  DoType;
    private  String  DueDate;
    private  String  CompleteDate;
    private  String  TaskStatus;
    private  String  IsRead;
    private  String  EvaluateScore;
    private  String  ToDoUser;
    private  String  ToDoDept;
    private  String  Cycle;
    private  String  Countdown;
    private  String  TaskStatusName;



    public GetOrganizeToDoList_Data() {
    }

    public GetOrganizeToDoList_Data(String toDoListId, String taskNo, String title, String contents, String source, String taskType, String createDate, String createUserId, String toDoUserId, String doType, String dueDate, String completeDate, String taskStatus, String isRead, String evaluateScore, String toDoUser, String toDoDept, String cycle, String countdown, String taskStatusName) {
        ToDoListId = toDoListId;
        TaskNo = taskNo;
        Title = title;
        Contents = contents;
        Source = source;
        TaskType = taskType;
        CreateDate = createDate;
        CreateUserId = createUserId;
        ToDoUserId = toDoUserId;
        DoType = doType;
        DueDate = dueDate;
        CompleteDate = completeDate;
        TaskStatus = taskStatus;
        IsRead = isRead;
        EvaluateScore = evaluateScore;
        ToDoUser = toDoUser;
        ToDoDept = toDoDept;
        Cycle = cycle;
        Countdown = countdown;
        TaskStatusName = taskStatusName;
    }

    public String getToDoListId() {
        return ToDoListId;
    }

    public void setToDoListId(String toDoListId) {
        ToDoListId = toDoListId;
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

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getTaskType() {
        return TaskType;
    }

    public void setTaskType(String taskType) {
        TaskType = taskType;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
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

    public String getDoType() {
        return DoType;
    }

    public void setDoType(String doType) {
        DoType = doType;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getEvaluateScore() {
        return EvaluateScore;
    }

    public void setEvaluateScore(String evaluateScore) {
        EvaluateScore = evaluateScore;
    }

    public String getToDoUser() {
        return ToDoUser;
    }

    public void setToDoUser(String toDoUser) {
        ToDoUser = toDoUser;
    }

    public String getToDoDept() {
        return ToDoDept;
    }

    public void setToDoDept(String toDoDept) {
        ToDoDept = toDoDept;
    }

    public String getCycle() {
        return Cycle;
    }

    public void setCycle(String cycle) {
        Cycle = cycle;
    }

    public String getCountdown() {
        return Countdown;
    }

    public void setCountdown(String countdown) {
        Countdown = countdown;
    }

    public String getTaskStatusName() {
        return TaskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        TaskStatusName = taskStatusName;
    }
}
