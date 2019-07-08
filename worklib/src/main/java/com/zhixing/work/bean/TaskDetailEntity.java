package com.zhixing.work.bean;

import java.util.List;

public class TaskDetailEntity {


    /**
     * TaskId : 65d92c0b-0d5a-41b0-9037-d8b3913a5514
     * TaskNo : C2019010220190104
     * TaskMode : 2
     * Title : null
     * TaskType : null
     * TaskDesc : 祝福大家新年好
     * AcceptanceStandard : null
     * AttachmentURL : null
     * TaskFrequency : null
     * DueDate : 2018-11-23T16:01:15
     * ReminderDate : null
     * RepeatCycle : null
     * RepeatFrequency : null
     * RepeatValue : null
     * TaskTime : null
     * CycleEndDate : null
     * CreateDate : 2019-01-02T14:35:14
     * CreateUserId : 06e78cc3-13d2-48ad-b65d-2d19b628e05e
     * CurrentStep : 10
     * Valid : 1
     * CancelDate : null
     * CancelRemark : null
     * TenantId : 00000000-0000-0000-0000-000000000001
     * IsOrigionTask : 2
     * OrigionTaskId : ad6f63d9-9149-46dd-a468-87c128605106
     * IsReplied : null
     * CurrentOperateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * Status : 0
     * Score : null
     * Comment : null
     * EndTime : null
     * ToDoUserName : 张三
     * CCUserName : 刘亚平,许婷
     * CreateUserName : 张三
     * CCUserId : 6DA5BB71-2DC8-46DA-98FF-9814069A7C7E,E7936890-6C2A-438A-BDBD-682ED5D7E912
     * ToDoUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * CommentList : {"rows":[{"CommentID":"5318EA7E-9917-4489-B934-607C564392D0","CommentSourceID":"51e72d58-4d7f-42a1-bb19-a184f1fd733c","CommentUserID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","CommentText":"测试","CommentDate":"2018-12-04T14:29:18","ToUserID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","EnclosureUrl":null,"Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-04T14:34:36","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","CommentUserName":"许婷","ToUserName":"刘亚平"}],"total":1}
     */

    private String TaskId;
    private String TaskNo;
    private int TaskMode;
    private Object Title;
    private Object TaskType;
    private String TaskDesc;
    private Object AcceptanceStandard;
    private Object AttachmentURL;
    private Object TaskFrequency;
    private String DueDate;
    private Object ReminderDate;
    private Object RepeatCycle;
    private Object RepeatFrequency;
    private Object RepeatValue;
    private Object TaskTime;
    private Object CycleEndDate;
    private String CreateDate;
    private String CreateUserId;
    private int CurrentStep;
    private String Valid;
    private Object CancelDate;
    private Object CancelRemark;
    private String TenantId;
    private String IsOrigionTask;
    private String OrigionTaskId;
    private Object IsReplied;
    private String CurrentOperateUserId;
    private int Status;
    private Object Score;
    private Object Comment;
    private Object EndTime;
    private String ToDoUserName;
    private String CCUserName;
    private String CreateUserName;
    private String CCUserId;
    private String ToDoUserId;
    private CommentListBean CommentList;

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String TaskId) {
        this.TaskId = TaskId;
    }

    public String getTaskNo() {
        return TaskNo;
    }

    public void setTaskNo(String TaskNo) {
        this.TaskNo = TaskNo;
    }

    public int getTaskMode() {
        return TaskMode;
    }

    public void setTaskMode(int TaskMode) {
        this.TaskMode = TaskMode;
    }

    public Object getTitle() {
        return Title;
    }

    public void setTitle(Object Title) {
        this.Title = Title;
    }

    public Object getTaskType() {
        return TaskType;
    }

    public void setTaskType(Object TaskType) {
        this.TaskType = TaskType;
    }

    public String getTaskDesc() {
        return TaskDesc;
    }

    public void setTaskDesc(String TaskDesc) {
        this.TaskDesc = TaskDesc;
    }

    public Object getAcceptanceStandard() {
        return AcceptanceStandard;
    }

    public void setAcceptanceStandard(Object AcceptanceStandard) {
        this.AcceptanceStandard = AcceptanceStandard;
    }

    public Object getAttachmentURL() {
        return AttachmentURL;
    }

    public void setAttachmentURL(Object AttachmentURL) {
        this.AttachmentURL = AttachmentURL;
    }

    public Object getTaskFrequency() {
        return TaskFrequency;
    }

    public void setTaskFrequency(Object TaskFrequency) {
        this.TaskFrequency = TaskFrequency;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    public Object getReminderDate() {
        return ReminderDate;
    }

    public void setReminderDate(Object ReminderDate) {
        this.ReminderDate = ReminderDate;
    }

    public Object getRepeatCycle() {
        return RepeatCycle;
    }

    public void setRepeatCycle(Object RepeatCycle) {
        this.RepeatCycle = RepeatCycle;
    }

    public Object getRepeatFrequency() {
        return RepeatFrequency;
    }

    public void setRepeatFrequency(Object RepeatFrequency) {
        this.RepeatFrequency = RepeatFrequency;
    }

    public Object getRepeatValue() {
        return RepeatValue;
    }

    public void setRepeatValue(Object RepeatValue) {
        this.RepeatValue = RepeatValue;
    }

    public Object getTaskTime() {
        return TaskTime;
    }

    public void setTaskTime(Object TaskTime) {
        this.TaskTime = TaskTime;
    }

    public Object getCycleEndDate() {
        return CycleEndDate;
    }

    public void setCycleEndDate(Object CycleEndDate) {
        this.CycleEndDate = CycleEndDate;
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

    public int getCurrentStep() {
        return CurrentStep;
    }

    public void setCurrentStep(int CurrentStep) {
        this.CurrentStep = CurrentStep;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String Valid) {
        this.Valid = Valid;
    }

    public Object getCancelDate() {
        return CancelDate;
    }

    public void setCancelDate(Object CancelDate) {
        this.CancelDate = CancelDate;
    }

    public Object getCancelRemark() {
        return CancelRemark;
    }

    public void setCancelRemark(Object CancelRemark) {
        this.CancelRemark = CancelRemark;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getIsOrigionTask() {
        return IsOrigionTask;
    }

    public void setIsOrigionTask(String IsOrigionTask) {
        this.IsOrigionTask = IsOrigionTask;
    }

    public String getOrigionTaskId() {
        return OrigionTaskId;
    }

    public void setOrigionTaskId(String OrigionTaskId) {
        this.OrigionTaskId = OrigionTaskId;
    }

    public Object getIsReplied() {
        return IsReplied;
    }

    public void setIsReplied(Object IsReplied) {
        this.IsReplied = IsReplied;
    }

    public String getCurrentOperateUserId() {
        return CurrentOperateUserId;
    }

    public void setCurrentOperateUserId(String CurrentOperateUserId) {
        this.CurrentOperateUserId = CurrentOperateUserId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Object getScore() {
        return Score;
    }

    public void setScore(Object Score) {
        this.Score = Score;
    }

    public Object getComment() {
        return Comment;
    }

    public void setComment(Object Comment) {
        this.Comment = Comment;
    }

    public Object getEndTime() {
        return EndTime;
    }

    public void setEndTime(Object EndTime) {
        this.EndTime = EndTime;
    }

    public String getToDoUserName() {
        return ToDoUserName;
    }

    public void setToDoUserName(String ToDoUserName) {
        this.ToDoUserName = ToDoUserName;
    }

    public String getCCUserName() {
        return CCUserName;
    }

    public void setCCUserName(String CCUserName) {
        this.CCUserName = CCUserName;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String CreateUserName) {
        this.CreateUserName = CreateUserName;
    }

    public String getCCUserId() {
        return CCUserId;
    }

    public void setCCUserId(String CCUserId) {
        this.CCUserId = CCUserId;
    }

    public String getToDoUserId() {
        return ToDoUserId;
    }

    public void setToDoUserId(String ToDoUserId) {
        this.ToDoUserId = ToDoUserId;
    }

    public CommentListBean getCommentList() {
        return CommentList;
    }

    public void setCommentList(CommentListBean CommentList) {
        this.CommentList = CommentList;
    }

    public static class CommentListBean {
        /**
         * rows : [{"CommentID":"5318EA7E-9917-4489-B934-607C564392D0","CommentSourceID":"51e72d58-4d7f-42a1-bb19-a184f1fd733c","CommentUserID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","CommentText":"测试","CommentDate":"2018-12-04T14:29:18","ToUserID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","EnclosureUrl":null,"Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-04T14:34:36","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","CommentUserName":"许婷","ToUserName":"刘亚平"}]
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
             * CommentID : 5318EA7E-9917-4489-B934-607C564392D0
             * CommentSourceID : 51e72d58-4d7f-42a1-bb19-a184f1fd733c
             * CommentUserID : E7936890-6C2A-438A-BDBD-682ED5D7E912
             * CommentText : 测试
             * CommentDate : 2018-12-04T14:29:18
             * ToUserID : 6DA5BB71-2DC8-46DA-98FF-9814069A7C7E
             * EnclosureUrl : null
             * Seq : 1
             * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
             * CreateTime : 2018-12-04T14:34:36
             * IsEnable : 1
             * TenantId : 00000000-0000-0000-0000-000000000001
             * CommentUserName : 许婷
             * ToUserName : 刘亚平
             */

            private String CommentID;
            private String CommentSourceID;
            private String CommentUserID;
            private String CommentText;
            private String CommentDate;
            private String ToUserID;
            private Object EnclosureUrl;
            private int Seq;
            private String CreateUserID;
            private String CreateTime;
            private String IsEnable;
            private String TenantId;
            private String CommentUserName;
            private String ToUserName;

            public String getCommentID() {
                return CommentID;
            }

            public void setCommentID(String CommentID) {
                this.CommentID = CommentID;
            }

            public String getCommentSourceID() {
                return CommentSourceID;
            }

            public void setCommentSourceID(String CommentSourceID) {
                this.CommentSourceID = CommentSourceID;
            }

            public String getCommentUserID() {
                return CommentUserID;
            }

            public void setCommentUserID(String CommentUserID) {
                this.CommentUserID = CommentUserID;
            }

            public String getCommentText() {
                return CommentText;
            }

            public void setCommentText(String CommentText) {
                this.CommentText = CommentText;
            }

            public String getCommentDate() {
                return CommentDate;
            }

            public void setCommentDate(String CommentDate) {
                this.CommentDate = CommentDate;
            }

            public String getToUserID() {
                return ToUserID;
            }

            public void setToUserID(String ToUserID) {
                this.ToUserID = ToUserID;
            }

            public Object getEnclosureUrl() {
                return EnclosureUrl;
            }

            public void setEnclosureUrl(Object EnclosureUrl) {
                this.EnclosureUrl = EnclosureUrl;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }

            public String getCreateUserID() {
                return CreateUserID;
            }

            public void setCreateUserID(String CreateUserID) {
                this.CreateUserID = CreateUserID;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getIsEnable() {
                return IsEnable;
            }

            public void setIsEnable(String IsEnable) {
                this.IsEnable = IsEnable;
            }

            public String getTenantId() {
                return TenantId;
            }

            public void setTenantId(String TenantId) {
                this.TenantId = TenantId;
            }

            public String getCommentUserName() {
                return CommentUserName;
            }

            public void setCommentUserName(String CommentUserName) {
                this.CommentUserName = CommentUserName;
            }

            public String getToUserName() {
                return ToUserName;
            }

            public void setToUserName(String ToUserName) {
                this.ToUserName = ToUserName;
            }
        }
    }
}
