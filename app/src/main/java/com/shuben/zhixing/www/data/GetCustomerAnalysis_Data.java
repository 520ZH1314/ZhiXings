package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.3.27	内部紧急催单->统计分析->催单客户统计
 *
 * {
 "total": -2147483648,
 "page": 1,
 "records": 3,
 "rows": [
 {
 "CustomerName": "华为",
 "Analysis": 2,
 "Demao": ""
 },
 {
 "CustomerName": "华为（中国）有限公司",
 "Analysis": 1,
 "Demao": ""
 },
 {
 "CustomerName": "深圳建滔",
 "Analysis": 1,
 "Demao": ""
 }
 ]
 }
 */

public class GetCustomerAnalysis_Data {

    private  String  CustomerName;
    private  String  Analysis;
    private  String  Demao;


    public GetCustomerAnalysis_Data() {
    }

    public GetCustomerAnalysis_Data(String customerName, String analysis, String demao) {
        CustomerName = customerName;
        Analysis = analysis;
        Demao = demao;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAnalysis() {
        return Analysis;
    }

    public void setAnalysis(String analysis) {
        Analysis = analysis;
    }

    public String getDemao() {
        return Demao;
    }

    public void setDemao(String demao) {
        Demao = demao;
    }
}
