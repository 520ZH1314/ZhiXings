package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.3.32	采购紧急催单->统计分析->催单分类统计
 *
 {
 "total": 5,
 "page": 1,
 "records": 5,
 "rows": [
 {
 "type": "人",
 "Total": null,
 "Value": null,
 "Seq": "1"
 },
 {
 "type": "机",
 "Total": null,
 "Value": null,
 "Seq": "2"
 },
 {
 "type": "料",
 "Total": null,
 "Value": null,
 "Seq": "3"
 },
 {
 "type": "法",
 "Total": null,
 "Value": null,
 "Seq": "4"
 },
 {
 "type": "环",
 "Total": null,
 "Value": null,
 "Seq": "5"
 }
 ]
 }
 */

public class GetTypeAnalysis_Data {

    private  String  total;
    private  String  Total;
    private  String  Value;
    private  String  Seq;


    public GetTypeAnalysis_Data() {

    }

    public GetTypeAnalysis_Data(String total, String total1, String value, String seq) {
        this.total = total;
        Total = total1;
        Value = value;
        Seq = seq;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getSeq() {
        return Seq;
    }

    public void setSeq(String seq) {
        Seq = seq;
    }
}
