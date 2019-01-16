package com.zhixing.tpmlib.bean;

public class ColumnarBean {
    public String Title;
    public String Date;
    public String Total;
    public String OnTimeNum;
    public String Percent;
    public String CycleCode;
    public String getCycleCode() {
        return CycleCode;
    }

    public void setCycleCode(String cycleCode) {
        CycleCode = cycleCode;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getOnTimeNum() {
        return OnTimeNum;
    }

    public void setOnTimeNum(String onTimeNum) {
        OnTimeNum = onTimeNum;
    }

    public String getPercent() {
        return Percent;
    }

    public void setPercent(String percent) {
        Percent = percent;
    }

    public ColumnarBean(String title, String date, String total, String onTimeNum, String percent, String cycleCode) {
        Title = title;
        Date = date;
        Total = total;
        OnTimeNum = onTimeNum;
        Percent = percent;
        CycleCode = cycleCode;
    }
}
