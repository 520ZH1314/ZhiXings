package com.zhixing.employlib.model.grading;

public class SelectUserData {

   public  String date;
    public String useCode;
    public String endTime;

    public String getDate() {
        return date;
    }

    public SelectUserData(String date, String useCode) {
        this.date = date;
        this.useCode = useCode;
    }

    public SelectUserData(String date, String useCode, String endTime) {
        this.date = date;
        this.useCode = useCode;
        this.endTime = endTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUseCode() {
        return useCode;
    }

    public void setUseCode(String useCode) {
        this.useCode = useCode;
    }
}
