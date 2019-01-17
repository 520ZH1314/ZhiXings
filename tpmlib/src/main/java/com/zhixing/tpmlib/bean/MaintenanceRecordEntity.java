package com.zhixing.tpmlib.bean;

import java.util.List;

public class MaintenanceRecordEntity {


    private String CompleteTime;
    private String UserCode;
    private String GradeName;
    private String MaintananceDate;
    private int delayday;

    public String getCompleteTime() {
        return CompleteTime;
    }

    public void setCompleteTime(String CompleteTime) {
        this.CompleteTime = CompleteTime;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String GradeName) {
        this.GradeName = GradeName;
    }

    public String getMaintananceDate() {
        return MaintananceDate;
    }

    public void setMaintananceDate(String MaintananceDate) {
        this.MaintananceDate = MaintananceDate;
    }

    public int getDelayday() {
        return delayday;
    }

    public void setDelayday(int delayday) {
        this.delayday = delayday;
    }
}

