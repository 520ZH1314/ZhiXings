package com.zhixing.work.bean;

import java.util.List;

public class TaskDetailEntity {


    /**
     * ToDoListId : 001e3286-507a-49f4-8876-b859a7c3a327
     * TaskNo : C2018121020181215
     * Title : 任务交办
     * Contents : 还是睡觉
     * Source : 2
     * TaskType : null
     * CreateDate : 2018-12-10T16:50:17
     * CreateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * ToDoUserId : 000D7EA5-D9C0-4A72-B89C-CBE103258125
     * DoType : 2
     * DueDate : 2018-12-10T05:00:00
     * CompleteDate : null
     * TaskStatus : 15
     * IsRead : 0
     * EvaluateScore : 0
     * SourceId : fd883234-f8fb-4133-b4de-1eba7945d8b2
     * ToDoUserName :
     * CCUserName :
     * CreateUserName :
     * CCUserId : 22413439-0823-4E11-9B1B-D8BC3F944B45
     * CommentList : {"rows":[{"CommentID":"5318EA7E-9917-4489-B934-607C564392D0","CommentSourceID":"51e72d58-4d7f-42a1-bb19-a184f1fd733c","CommentUserID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","CommentText":"测试","CommentDate":"2018-12-04T14:29:18","ToUserID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","EnclosureUrl":null,"Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-04T14:34:36","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","CommentUserName":"许婷","ToUserName":"刘亚平"}],"total":1}
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
    private int EvaluateScore;
    private String SourceId;
    private String ToDoUserName;
    private String CCUserName;
    private String CreateUserName;
    private String CCUserId;
    private CommentListBean CommentList;

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

    public int getEvaluateScore() {
        return EvaluateScore;
    }

    public void setEvaluateScore(int EvaluateScore) {
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
