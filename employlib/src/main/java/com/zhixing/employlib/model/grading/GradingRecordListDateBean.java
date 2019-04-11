package com.zhixing.employlib.model.grading;

import java.io.Serializable;

public class GradingRecordListDateBean implements Serializable {
    public String startTime;
    public String endTime;

    public GradingRecordListDateBean(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
