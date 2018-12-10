package com.zhixing.work.bean;

import java.util.List;

public class ResponseCompeteBean {


    /**
     * rows : [{"ToDoListId":"5e874fcb-16e0-4b8f-a777-18ff5e3c40f9","TaskNo":"C2018120620181215","Title":"任务交办","Contents":"今天下午准备开会","Source":2,"TaskType":null,"CreateDate":"2018-12-06T14:12:50","CreateUserId":"31971032-a433-4edc-affc-76d7fb39480e","ToDoUserId":"E7936890-6C2A-438A-BDBD-682ED5D7E912","DoType":1,"DueDate":"2018-12-06T03:00:00","CompleteDate":null,"TaskStatus":10,"IsRead":"0","EvaluateScore":null,"SourceId":"51e72d58-4d7f-42a1-bb19-a184f1fd733c","ToDoUserName":"许婷"}]
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
         * ToDoListId : 5e874fcb-16e0-4b8f-a777-18ff5e3c40f9
         * TaskNo : C2018120620181215
         * Title : 任务交办
         * Contents : 今天下午准备开会
         * Source : 2
         * TaskType : null
         * CreateDate : 2018-12-06T14:12:50
         * CreateUserId : 31971032-a433-4edc-affc-76d7fb39480e
         * ToDoUserId : E7936890-6C2A-438A-BDBD-682ED5D7E912
         * DoType : 1
         * DueDate : 2018-12-06T03:00:00
         * CompleteDate : null
         * TaskStatus : 10
         * IsRead : 0
         * EvaluateScore : null
         * SourceId : 51e72d58-4d7f-42a1-bb19-a184f1fd733c
         * ToDoUserName : 许婷
         */

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

        public String getToDoListId() {
            return ToDoListId;
        }

        public void setToDoListId(String ToDoListId) {
            this.ToDoListId = ToDoListId;
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
    }
}
