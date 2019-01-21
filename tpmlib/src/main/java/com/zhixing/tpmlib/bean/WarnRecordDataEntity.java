package com.zhixing.tpmlib.bean;

public class WarnRecordDataEntity {


    /**
     * CreateTime : 2018-05-24 19:48:10
     * Description : 设备其他
     * Measure : 未登记处理措施
     * UserInfo :
     * RelieveTime : 无
     */

    private String CreateTime;
    private String Description;
    private String Measure;
    private String UserInfo;
    private String RelieveTime;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String Measure) {
        this.Measure = Measure;
    }

    public String getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(String UserInfo) {
        this.UserInfo = UserInfo;
    }

    public String getRelieveTime() {
        return RelieveTime;
    }

    public void setRelieveTime(String RelieveTime) {
        this.RelieveTime = RelieveTime;
    }
}
