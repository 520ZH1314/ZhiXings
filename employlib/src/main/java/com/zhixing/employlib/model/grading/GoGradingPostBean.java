package com.zhixing.employlib.model.grading;

import java.util.List;

public class GoGradingPostBean {


    /**
     * AppCode : EMS
     * ApiCode : EditAPPKeyEvent
     * TenantId : 00000000-0000-0000-0000-000000000001
     * TeamLeadeCode : 06157
     * TeamLeadeTeamId : c08aeff2-9754-4e54-ad37-655a33390492
     * UserInfo : [{"UserCode":"06153","UserName":"彭敏"},{"UserCode":"06152","UserName":"刘奇炜"}]
     * EventInfo : [{"ItemId":"3ef7b2fd-ea70-4301-89f4-c886e32df6f9","ShiftDate":"2019-06-01 09:13:20"},{"ItemId":"3ef7b2fd-ea70-4301-89f4-c886e32df6f9","ShiftDate":"2019-06-01 09:13:20"}]
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String TeamLeadeCode;
    private String TeamLeadeTeamId;
    private List<UserInfoBean> UserInfo;
    private List<EventInfoBean> EventInfo;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String ApiCode) {
        this.ApiCode = ApiCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getTeamLeadeCode() {
        return TeamLeadeCode;
    }

    public void setTeamLeadeCode(String TeamLeadeCode) {
        this.TeamLeadeCode = TeamLeadeCode;
    }

    public String getTeamLeadeTeamId() {
        return TeamLeadeTeamId;
    }

    public void setTeamLeadeTeamId(String TeamLeadeTeamId) {
        this.TeamLeadeTeamId = TeamLeadeTeamId;
    }

    public List<UserInfoBean> getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(List<UserInfoBean> UserInfo) {
        this.UserInfo = UserInfo;
    }

    public List<EventInfoBean> getEventInfo() {
        return EventInfo;
    }

    public void setEventInfo(List<EventInfoBean> EventInfo) {
        this.EventInfo = EventInfo;
    }

    public static class UserInfoBean {
        /**
         * UserCode : 06153
         * UserName : 彭敏
         */

        private String UserCode;
        private String UserName;

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
    }

    public static class EventInfoBean {
        /**
         * ItemId : 3ef7b2fd-ea70-4301-89f4-c886e32df6f9
         * ShiftDate : 2019-06-01 09:13:20
         */

        private String ItemId;
        private String ShiftDate;

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String ItemId) {
            this.ItemId = ItemId;
        }

        public String getShiftDate() {
            return ShiftDate;
        }

        public void setShiftDate(String ShiftDate) {
            this.ShiftDate = ShiftDate;
        }
    }
}
