package com.shuben.zhixing.www.mes.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/6/18.
 */

public class LossInfo implements Serializable{
    private  String item01;
    private  String item02;
    private  String item03;
    private  String item04;
    private  String item05;

    public LossInfo(String item01, String item02, String item03, String item04, String item05) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
        this.item04 = item04;
        this.item05 = item05;
    }

    public String getItem01() {
        return item01;
    }

    public void setItem01(String item01) {
        this.item01 = item01;
    }

    public String getItem02() {
        return item02;
    }

    public void setItem02(String item02) {
        this.item02 = item02;
    }

    public String getItem03() {
        return item03;
    }

    public void setItem03(String item03) {
        this.item03 = item03;
    }

    public String getItem04() {
        return item04;
    }

    public void setItem04(String item04) {
        this.item04 = item04;
    }

    public String getItem05() {
        return item05;
    }

    public void setItem05(String item05) {
        this.item05 = item05;
    }
}
