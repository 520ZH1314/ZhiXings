package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.2.23	我的组织->统计分析->评价管理分析->公司龙虎榜
 \"Ranking\": \"1\",
 \"UserName\": \"钟立祥\",
 \"DeptName\": \"深圳市谋事精益信息咨询有限公司\",
 \"AvgScore\": 4.0
 */

public class GetCompanyTaskCloseRateDataByTop_Data {
    private  String  Ranking;
    private  String  UserName;
    private  String  DeptName;
    private  String  AvgScore;



    public GetCompanyTaskCloseRateDataByTop_Data() {
    }

    public GetCompanyTaskCloseRateDataByTop_Data(String ranking, String userName, String deptName, String avgScore) {
        Ranking = ranking;
        UserName = userName;
        DeptName = deptName;
        AvgScore = avgScore;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getAvgScore() {
        return AvgScore;
    }

    public void setAvgScore(String avgScore) {
        AvgScore = avgScore;
    }
}
