package com.zhixing.work.bean;

import java.util.List;

public class ResponseMeetDetailEntity {

    /**
     * MeetingID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     * MeetingTitle : null
     * MeetingContent : 123456
     * MeetingStatus : 3
     * MeetingPlace : 深圳南山科技园
     * MeetingReminder : 1
     * ParticipantsID : 2430147E-A451-4841-B027-717C6C8F27A8
     * StartDate : 2018-12-07T08:00:00
     * EndDate : 2018-12-07T21:00:00
     * Seq : null
     * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * CreateTime : 2018-12-07T09:47:44
     * IsEnable : 1
     * TenantId : 00000000-0000-0000-0000-000000000001
     * HostName : 毕丽芷
     * ParticipantName : 陈凤岭,布建利,毕丽芷
     * RecorderName : 党红星
     * HostID : F4D8CD26-2617-4579-A1EA-6ABC31C8D85E
     * ParticipantID : 4D2D5DF9-2929-45AC-9FC4-6F1D7F26A645
     * RecorderID : F4D8CD26-2617-4579-A1EA-6ABC31C8D85E
     * CommentList : {"rows":[{"CommentID":"5318EA7E-9917-4489-B934-607C564392D0","CommentSourceID":"3a7da71e-c12a-4c01-a945-5c78c3f7decb","CommentUserID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","CommentText":"测试","CommentDate":"2018-12-04T14:29:18","ToUserID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","EnclosureUrl":null,"Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-04T14:34:36","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","CommentUserName":"许婷","ToUserName":"刘亚平"}],"total":1}
     * MeetingDetailsList : {"rows":[{"MeetingDetailsID":"5bb1df42-4771-4c82-9c38-a557525881a0","MeetingID":"3a7da71e-c12a-4c01-a945-5c78c3f7decb","MeetingDes":"会议纪要2","RecorderID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-10T16:31:18","UpdateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","UpdateTime":"2018-12-10T16:31:18","TenantId":"00000000-0000-0000-0000-000000000001","RecorderName":"张三"}],"total":1}
     */

    private String MeetingID;
    private Object MeetingTitle;
    private String MeetingContent;
    private int MeetingStatus;
    private String MeetingPlace;
    private int MeetingReminder;
    private String ParticipantsID;
    private String StartDate;
    private String EndDate;
    private Object Seq;
    private String CreateUserID;
    private String CreateTime;
    private String IsEnable;
    private String TenantId;
    private String HostName;
    private String ParticipantName;
    private String RecorderName;
    private String HostID;
    private String ParticipantID;
    private String RecorderID;
    private CommentListBean CommentList;
    private MeetingDetailsListBean MeetingDetailsList;

    public String getMeetingID() {
        return MeetingID;
    }

    public void setMeetingID(String MeetingID) {
        this.MeetingID = MeetingID;
    }

    public Object getMeetingTitle() {
        return MeetingTitle;
    }

    public void setMeetingTitle(Object MeetingTitle) {
        this.MeetingTitle = MeetingTitle;
    }

    public String getMeetingContent() {
        return MeetingContent;
    }

    public void setMeetingContent(String MeetingContent) {
        this.MeetingContent = MeetingContent;
    }

    public int getMeetingStatus() {
        return MeetingStatus;
    }

    public void setMeetingStatus(int MeetingStatus) {
        this.MeetingStatus = MeetingStatus;
    }

    public String getMeetingPlace() {
        return MeetingPlace;
    }

    public void setMeetingPlace(String MeetingPlace) {
        this.MeetingPlace = MeetingPlace;
    }

    public int getMeetingReminder() {
        return MeetingReminder;
    }

    public void setMeetingReminder(int MeetingReminder) {
        this.MeetingReminder = MeetingReminder;
    }

    public String getParticipantsID() {
        return ParticipantsID;
    }

    public void setParticipantsID(String ParticipantsID) {
        this.ParticipantsID = ParticipantsID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public Object getSeq() {
        return Seq;
    }

    public void setSeq(Object Seq) {
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

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String HostName) {
        this.HostName = HostName;
    }

    public String getParticipantName() {
        return ParticipantName;
    }

    public void setParticipantName(String ParticipantName) {
        this.ParticipantName = ParticipantName;
    }

    public String getRecorderName() {
        return RecorderName;
    }

    public void setRecorderName(String RecorderName) {
        this.RecorderName = RecorderName;
    }

    public String getHostID() {
        return HostID;
    }

    public void setHostID(String HostID) {
        this.HostID = HostID;
    }

    public String getParticipantID() {
        return ParticipantID;
    }

    public void setParticipantID(String ParticipantID) {
        this.ParticipantID = ParticipantID;
    }

    public String getRecorderID() {
        return RecorderID;
    }

    public void setRecorderID(String RecorderID) {
        this.RecorderID = RecorderID;
    }

    public CommentListBean getCommentList() {
        return CommentList;
    }

    public void setCommentList(CommentListBean CommentList) {
        this.CommentList = CommentList;
    }

    public MeetingDetailsListBean getMeetingDetailsList() {
        return MeetingDetailsList;
    }

    public void setMeetingDetailsList(MeetingDetailsListBean MeetingDetailsList) {
        this.MeetingDetailsList = MeetingDetailsList;
    }

    public static class CommentListBean {
        /**
         * rows : [{"CommentID":"5318EA7E-9917-4489-B934-607C564392D0","CommentSourceID":"3a7da71e-c12a-4c01-a945-5c78c3f7decb","CommentUserID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","CommentText":"测试","CommentDate":"2018-12-04T14:29:18","ToUserID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","EnclosureUrl":null,"Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-04T14:34:36","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","CommentUserName":"许婷","ToUserName":"刘亚平"}]
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
             * CommentSourceID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
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

    public static class MeetingDetailsListBean {
        /**
         * rows : [{"MeetingDetailsID":"5bb1df42-4771-4c82-9c38-a557525881a0","MeetingID":"3a7da71e-c12a-4c01-a945-5c78c3f7decb","MeetingDes":"会议纪要2","RecorderID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-10T16:31:18","UpdateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","UpdateTime":"2018-12-10T16:31:18","TenantId":"00000000-0000-0000-0000-000000000001","RecorderName":"张三"}]
         * total : 1
         */

        private int total;
        private List<RowsBeanX> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBeanX> getRows() {
            return rows;
        }

        public void setRows(List<RowsBeanX> rows) {
            this.rows = rows;
        }

        public static class RowsBeanX {
            /**
             * MeetingDetailsID : 5bb1df42-4771-4c82-9c38-a557525881a0
             * MeetingID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
             * MeetingDes : 会议纪要2
             * RecorderID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
             * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
             * CreateTime : 2018-12-10T16:31:18
             * UpdateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
             * UpdateTime : 2018-12-10T16:31:18
             * TenantId : 00000000-0000-0000-0000-000000000001
             * RecorderName : 张三
             */

            private String MeetingDetailsID;
            private String MeetingID;
            private String MeetingDes;
            private String RecorderID;
            private String CreateUserID;
            private String CreateTime;
            private String UpdateUserID;
            private String UpdateTime;
            private String TenantId;
            private String RecorderName;

            public String getMeetingDetailsID() {
                return MeetingDetailsID;
            }

            public void setMeetingDetailsID(String MeetingDetailsID) {
                this.MeetingDetailsID = MeetingDetailsID;
            }

            public String getMeetingID() {
                return MeetingID;
            }

            public void setMeetingID(String MeetingID) {
                this.MeetingID = MeetingID;
            }

            public String getMeetingDes() {
                return MeetingDes;
            }

            public void setMeetingDes(String MeetingDes) {
                this.MeetingDes = MeetingDes;
            }

            public String getRecorderID() {
                return RecorderID;
            }

            public void setRecorderID(String RecorderID) {
                this.RecorderID = RecorderID;
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

            public String getUpdateUserID() {
                return UpdateUserID;
            }

            public void setUpdateUserID(String UpdateUserID) {
                this.UpdateUserID = UpdateUserID;
            }

            public String getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(String UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public String getTenantId() {
                return TenantId;
            }

            public void setTenantId(String TenantId) {
                this.TenantId = TenantId;
            }

            public String getRecorderName() {
                return RecorderName;
            }

            public void setRecorderName(String RecorderName) {
                this.RecorderName = RecorderName;
            }
        }
    }
}
