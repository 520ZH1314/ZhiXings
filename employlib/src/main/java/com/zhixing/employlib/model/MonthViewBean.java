package com.zhixing.employlib.model;

import java.util.List;

public class MonthViewBean {


    /**
     * EventDate : 2019-03-27
     * Score : 9
     * EventCount : 2
     * GrapeName : 差
     * GrapeColor : white
     * EventInfo : [{"EventId":"2c2086cf-6a3e-49c2-9327-6d313f9077bb","UserCode":"00228","UserName":"侯玉林","TeamId":"2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0","TeamName":"","Description":"","TeamLeaderId":"7565acc5-8025-11e8-b8e8-507b9d9a63b9","TeamLeaderName":"钟志友","ItemId":"5ef48955-c854-4612-9803-c92c55748299","ItemName":"5S","Attributer":"正面","Score":3,"ShiftId":"","ShiftName":"","ShiftDate":"2019-03-27T20:00:00","TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2018-07-26T16:35:22"}]
     */

    private String EventDate;
    private int Score;
    private int EventCount;
    private String GrapeName;
    private String GrapeColor;
    private List<EventInfoBean> EventInfo;

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String EventDate) {
        this.EventDate = EventDate;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getEventCount() {
        return EventCount;
    }

    public void setEventCount(int EventCount) {
        this.EventCount = EventCount;
    }

    public String getGrapeName() {
        return GrapeName;
    }

    public void setGrapeName(String GrapeName) {
        this.GrapeName = GrapeName;
    }

    public String getGrapeColor() {
        return GrapeColor;
    }

    public void setGrapeColor(String GrapeColor) {
        this.GrapeColor = GrapeColor;
    }

    public List<EventInfoBean> getEventInfo() {
        return EventInfo;
    }

    public void setEventInfo(List<EventInfoBean> EventInfo) {
        this.EventInfo = EventInfo;
    }

    public static class EventInfoBean {
        /**
         * EventId : 2c2086cf-6a3e-49c2-9327-6d313f9077bb
         * UserCode : 00228
         * UserName : 侯玉林
         * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
         * TeamName :
         * Description :
         * TeamLeaderId : 7565acc5-8025-11e8-b8e8-507b9d9a63b9
         * TeamLeaderName : 钟志友
         * ItemId : 5ef48955-c854-4612-9803-c92c55748299
         * ItemName : 5S
         * Attributer : 正面
         * Score : 3
         * ShiftId :
         * ShiftName :
         * ShiftDate : 2019-03-27T20:00:00
         * TenantId : 00000000-0000-0000-0000-000000000001
         * CreateTime : 2018-07-26T16:35:22
         */

        private String EventId;
        private String UserCode;
        private String UserName;
        private String TeamId;
        private String TeamName;
        private String Description;
        private String TeamLeaderId;
        private String TeamLeaderName;
        private String ItemId;
        private String ItemName;
        private String Attributer;
        private int Score;
        private String ShiftId;
        private String ShiftName;
        private String ShiftDate;
        private String TenantId;
        private String CreateTime;

        public String getEventId() {
            return EventId;
        }

        public void setEventId(String EventId) {
            this.EventId = EventId;
        }

        public String getUserCode() {
            return UserCode;
        }

        public void setUserCode(String UserCode) {
            this.UserCode = UserCode;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getTeamId() {
            return TeamId;
        }

        public void setTeamId(String TeamId) {
            this.TeamId = TeamId;
        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String TeamName) {
            this.TeamName = TeamName;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getTeamLeaderId() {
            return TeamLeaderId;
        }

        public void setTeamLeaderId(String TeamLeaderId) {
            this.TeamLeaderId = TeamLeaderId;
        }

        public String getTeamLeaderName() {
            return TeamLeaderName;
        }

        public void setTeamLeaderName(String TeamLeaderName) {
            this.TeamLeaderName = TeamLeaderName;
        }

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String ItemId) {
            this.ItemId = ItemId;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getAttributer() {
            return Attributer;
        }

        public void setAttributer(String Attributer) {
            this.Attributer = Attributer;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public String getShiftId() {
            return ShiftId;
        }

        public void setShiftId(String ShiftId) {
            this.ShiftId = ShiftId;
        }

        public String getShiftName() {
            return ShiftName;
        }

        public void setShiftName(String ShiftName) {
            this.ShiftName = ShiftName;
        }

        public String getShiftDate() {
            return ShiftDate;
        }

        public void setShiftDate(String ShiftDate) {
            this.ShiftDate = ShiftDate;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
