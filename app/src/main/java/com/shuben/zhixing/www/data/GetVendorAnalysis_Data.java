package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.3.31	采购紧急催单->统计分析->催单供应商统计
 *
 {"total":-2147483648,
 "page":1,
 "records":1,
 "rows":[
 {"VendorName":"深圳市桑格尔科技股份有限公司",
 "Analysis":2,
 "Demao":""}
 ]
 }
 */

public class GetVendorAnalysis_Data {

    private  String  VendorName;
    private  String  Analysis;
    private  String  Demao;


    public GetVendorAnalysis_Data() {
    }

    public GetVendorAnalysis_Data(String vendorName, String analysis, String demao) {
        VendorName = vendorName;
        Analysis = analysis;
        Demao = demao;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
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
