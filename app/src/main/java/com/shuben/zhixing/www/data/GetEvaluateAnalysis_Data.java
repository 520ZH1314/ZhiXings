package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.3.33	采购紧急催单->统计分析->获取平均分
 *
 返回值	{"UserScore":9,"CompanyScore":3}
 分别为用户平均分和公司平均分，最大值为5 。

 */

public class GetEvaluateAnalysis_Data {

    private  String  UserScore;
    private  String  CompanyScore;


    public GetEvaluateAnalysis_Data() {

    }

    public GetEvaluateAnalysis_Data(String userScore, String companyScore) {
        UserScore = userScore;
        CompanyScore = companyScore;
    }

    public String getUserScore() {
        return UserScore;
    }

    public void setUserScore(String userScore) {
        UserScore = userScore;
    }

    public String getCompanyScore() {
        return CompanyScore;
    }

    public void setCompanyScore(String companyScore) {
        CompanyScore = companyScore;
    }
}
