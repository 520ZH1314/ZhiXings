package com.shuben.zhixing.www.patrol.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/5/20.
 */

public class GroupInfo implements Serializable{
    private String item01;
    private String item02;
    private String item03;
    private String item04;

    public GroupInfo(String item01, String item02, String item03) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
    }

    public GroupInfo(String item01, String item02, String item03, String item04) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
        this.item04 = item04;
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
}
