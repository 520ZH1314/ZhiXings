package com.zhixing.work.bean;

import java.util.List;

public class ResponseMeetingEntity {


    /**
     * rows : [{"MeetingDataID":"4f1facda-eb44-4986-8358-e1ced57f4470","MeetingID":"0d3d90ec-acff-472e-8047-504713e2c0da","MeetingContent":"测试内容","MeetingStatus":1,"MeetingPlace":"测试地址","MeetingReminder":1,"ParticipantsID":"2430147E-A451-4841-B027-717C6C8F27A8","StartDate":"2018-11-12T16:48:00","EndDate":"2018-11-12T16:48:00","Seq":1,"CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateTime":"2018-12-08T16:31:25","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","Count":0,"HostCount":1,"ParticipantCount":2,"RecorderCount":1,"CreateUserName":"张三"}]
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
         * MeetingDataID : 4f1facda-eb44-4986-8358-e1ced57f4470
         * MeetingID : 0d3d90ec-acff-472e-8047-504713e2c0da
         * MeetingContent : 测试内容
         * MeetingStatus : 1
         * MeetingPlace : 测试地址
         * MeetingReminder : 1
         * ParticipantsID : 2430147E-A451-4841-B027-717C6C8F27A8
         * StartDate : 2018-11-12T16:48:00
         * EndDate : 2018-11-12T16:48:00
         * Seq : 1
         * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
         * CreateTime : 2018-12-08T16:31:25
         * IsEnable : 1
         * TenantId : 00000000-0000-0000-0000-000000000001
         * Count : 0
         * HostCount : 1
         * ParticipantCount : 2
         * RecorderCount : 1
         * CreateUserName : 张三
         */

        private String MeetingDataID;
        private String MeetingID;
        private String MeetingContent;
        private int MeetingStatus;
        private String MeetingPlace;
        private int MeetingReminder;
        private String ParticipantsID;
        private String StartDate;
        private String EndDate;
        private int Seq;
        private String CreateUserID;
        private String CreateTime;
        private String IsEnable;
        private String TenantId;
        private int Count;

        private int ParticipantsCount;

        private String CreateUserName;

        public String getMeetingDataID() {
            return MeetingDataID;
        }

        public void setMeetingDataID(String MeetingDataID) {
            this.MeetingDataID = MeetingDataID;
        }

        public String getMeetingID() {
            return MeetingID;
        }

        public void setMeetingID(String MeetingID) {
            this.MeetingID = MeetingID;
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

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }



        public int getParticipantsCount() {
            return ParticipantsCount;
        }

        public void setParticipantsCount(int ParticipantsCount) {
            this.ParticipantsCount = ParticipantsCount;
        }
           public String getCreateUserName() {
            return CreateUserName;
        }

        public void setCreateUserName(String CreateUserName) {
            this.CreateUserName = CreateUserName;
        }
    }
}
