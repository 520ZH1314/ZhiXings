package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/8/6.
 */

public class AnalyInfo implements Serializable{
    private String tx_calss;
    private String tx_item01;
    private String tx_item02;
    private String tx_item03;
    private String tx_item04;

    public AnalyInfo(String tx_calss, String tx_item01, String tx_item02, String tx_item03, String tx_item04) {
        this.tx_calss = tx_calss;
        this.tx_item01 = tx_item01;
        this.tx_item02 = tx_item02;
        this.tx_item03 = tx_item03;
        this.tx_item04 = tx_item04;
    }

    public String getTx_calss() {
        return tx_calss;
    }

    public void setTx_calss(String tx_calss) {
        this.tx_calss = tx_calss;
    }

    public String getTx_item01() {
        return tx_item01;
    }

    public void setTx_item01(String tx_item01) {
        this.tx_item01 = tx_item01;
    }

    public String getTx_item02() {
        return tx_item02;
    }

    public void setTx_item02(String tx_item02) {
        this.tx_item02 = tx_item02;
    }

    public String getTx_item03() {
        return tx_item03;
    }

    public void setTx_item03(String tx_item03) {
        this.tx_item03 = tx_item03;
    }

    public String getTx_item04() {
        return tx_item04;
    }

    public void setTx_item04(String tx_item04) {
        this.tx_item04 = tx_item04;
    }
}
