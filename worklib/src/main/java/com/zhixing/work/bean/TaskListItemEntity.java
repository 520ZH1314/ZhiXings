package com.zhixing.work.bean;

import java.util.List;

public class TaskListItemEntity {


    /**
     * rows : [{"ToDoListId":"5dae39a2-c716-47f6-afd2-05749f6834e7","TaskNo":"C2018120620181213","Title":"任务交办","Contents":"测试","Source":2,"TaskType":null,"CreateDate":"2018-12-06T11:51:30","CreateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","ToDoUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","DoType":2,"DueDate":"2018-11-23T16:01:15","CompleteDate":null,"TaskStatus":0,"IsRead":"0","EvaluateScore":null,"SourceId":"cdfba646-10f0-4fd6-a87f-b06fa18e930d","ToDoUserName":"张三","CreateUserName":"张三","TaskStatusName":"进行中","DoTypeName":"参与"}]
     * total : 1
     */

    private int total;
    private List<RowsBean> rows;




    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * ToDoListId : 5dae39a2-c716-47f6-afd2-05749f6834e7
         * TaskNo : C2018120620181213
         * Title : 任务交办
         * Contents : 测试
         * Source : 2
         * TaskType : null
         * CreateDate : 2018-12-06T11:51:30
         * CreateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
         * ToDoUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
         * DoType : 2
         * DueDate : 2018-11-23T16:01:15
         * CompleteDate : null
         * TaskStatus : 0
         * IsRead : 0
         * EvaluateScore : null
         * SourceId : cdfba646-10f0-4fd6-a87f-b06fa18e930d
         * ToDoUserName : 张三
         * CreateUserName : 张三
         * TaskStatusName : 进行中
         * DoTypeName : 参与
         */
        private  int Count;
        private String ToDoListId;
        private String TaskNo;
        private String Title;
        private String Contents;
        private int Source;
        private Object TaskType;
        private String CreateDate;
        private String CreateUserId;
        private String ToDoUserId;
        private int DoType;
        private String DueDate;
        private Object CompleteDate;
        private int TaskStatus;
        private String IsRead;
        private Object EvaluateScore;
        private String SourceId;
        private String ToDoUserName;
        private String CreateUserName;
        private String TaskStatusName;
        private String DoTypeName;

        public String getToDoListId() {
            return ToDoListId;
        }

        public void setToDoListId(String ToDoListId) {
            this.ToDoListId = ToDoListId;
        }
        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }
        public String getTaskNo() {
            return TaskNo;
        }

        public void setTaskNo(String TaskNo) {
            this.TaskNo = TaskNo;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContents() {
            return Contents;
        }

        public void setContents(String Contents) {
            this.Contents = Contents;
        }

        public int getSource() {
            return Source;
        }

        public void setSource(int Source) {
            this.Source = Source;
        }

        public Object getTaskType() {
            return TaskType;
        }

        public void setTaskType(Object TaskType) {
            this.TaskType = TaskType;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getToDoUserId() {
            return ToDoUserId;
        }

        public void setToDoUserId(String ToDoUserId) {
            this.ToDoUserId = ToDoUserId;
        }

        public int getDoType() {
            return DoType;
        }

        public void setDoType(int DoType) {
            this.DoType = DoType;
        }

        public String getDueDate() {
            return DueDate;
        }

        public void setDueDate(String DueDate) {
            this.DueDate = DueDate;
        }

        public Object getCompleteDate() {
            return CompleteDate;
        }

        public void setCompleteDate(Object CompleteDate) {
            this.CompleteDate = CompleteDate;
        }

        public int getTaskStatus() {
            return TaskStatus;
        }

        public void setTaskStatus(int TaskStatus) {
            this.TaskStatus = TaskStatus;
        }

        public String getIsRead() {
            return IsRead;
        }

        public void setIsRead(String IsRead) {
            this.IsRead = IsRead;
        }

        public Object getEvaluateScore() {
            return EvaluateScore;
        }

        public void setEvaluateScore(Object EvaluateScore) {
            this.EvaluateScore = EvaluateScore;
        }

        public String getSourceId() {
            return SourceId;
        }

        public void setSourceId(String SourceId) {
            this.SourceId = SourceId;
        }

        public String getToDoUserName() {
            return ToDoUserName;
        }

        public void setToDoUserName(String ToDoUserName) {
            this.ToDoUserName = ToDoUserName;
        }

        public String getCreateUserName() {
            return CreateUserName;
        }

        public void setCreateUserName(String CreateUserName) {
            this.CreateUserName = CreateUserName;
        }

        public String getTaskStatusName() {
            return TaskStatusName;
        }

        public void setTaskStatusName(String TaskStatusName) {
            this.TaskStatusName = TaskStatusName;
        }

        public String getDoTypeName() {
            return DoTypeName;
        }

        public void setDoTypeName(String DoTypeName) {
            this.DoTypeName = DoTypeName;
        }
    }
}
