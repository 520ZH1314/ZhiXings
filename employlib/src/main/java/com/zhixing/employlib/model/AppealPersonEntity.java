package com.zhixing.employlib.model;

import java.io.Serializable;

public class AppealPersonEntity   implements Serializable {
    /**
     * Copyright 2019 bejson.com
     */

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTeamLeaderId() {
        return TeamLeaderId;
    }

    public void setTeamLeaderId(String teamLeaderId) {
        TeamLeaderId = teamLeaderId;
    }

    public String getTeamLeaderName() {
        return TeamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        TeamLeaderName = teamLeaderName;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getAttributer() {
        return Attributer;
    }

    public void setAttributer(String attributer) {
        Attributer = attributer;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getShiftId() {
        return ShiftId;
    }

    public void setShiftId(String shiftId) {
        ShiftId = shiftId;
    }

    public String getShiftName() {
        return ShiftName;
    }

    public void setShiftName(String shiftName) {
        ShiftName = shiftName;
    }

    public String getShiftDate() {
        return ShiftDate;
    }

    public void setShiftDate(String shiftDate) {
        ShiftDate = shiftDate;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    /**
     * Auto-generated: 2019-03-28 20:5:36
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
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

}
