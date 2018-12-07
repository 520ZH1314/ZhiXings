package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.2.18	我的组织->统计分析->任务关闭率->部门发起的任务
 *
 \"ClassName\": \"合计\",
 \"TotalCount\": 8,
 \"OnTimeCloseCount\": 4,
 \"DelayCloseCount\": 0,
 \"OnGoingCount\": 4,
 \"DelayOnGoingCount\": 0
 */

public class GetClosedRateChart_Data {
    private  String  ClassName;
    private  String  TotalCount;
    private  String  OnTimeCloseCount;
    private  String  DelayCloseCount;
    private  String  OnGoingCount;
    private  String  DelayOnGoingCount;


    public GetClosedRateChart_Data() {
    }

    public GetClosedRateChart_Data(String className, String totalCount, String onTimeCloseCount, String delayCloseCount, String onGoingCount, String delayOnGoingCount) {
        ClassName = className;
        TotalCount = totalCount;
        OnTimeCloseCount = onTimeCloseCount;
        DelayCloseCount = delayCloseCount;
        OnGoingCount = onGoingCount;
        DelayOnGoingCount = delayOnGoingCount;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getOnTimeCloseCount() {
        return OnTimeCloseCount;
    }

    public void setOnTimeCloseCount(String onTimeCloseCount) {
        OnTimeCloseCount = onTimeCloseCount;
    }

    public String getDelayCloseCount() {
        return DelayCloseCount;
    }

    public void setDelayCloseCount(String delayCloseCount) {
        DelayCloseCount = delayCloseCount;
    }

    public String getOnGoingCount() {
        return OnGoingCount;
    }

    public void setOnGoingCount(String onGoingCount) {
        OnGoingCount = onGoingCount;
    }

    public String getDelayOnGoingCount() {
        return DelayOnGoingCount;
    }

    public void setDelayOnGoingCount(String delayOnGoingCount) {
        DelayOnGoingCount = delayOnGoingCount;
    }
}
