package com.shuben.zhixing.www.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geyan on 2018/6/15.
 */

public class ReviewInfo implements Serializable {
    private String item01;
    private String item02;
    private String item03;
    private List <SubInfo> item04;

    public ReviewInfo(String item01, String item02, String item03, List<SubInfo> item04) {
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

    public List<SubInfo> getItem04() {
        return item04;
    }

    public void setItem04(List<SubInfo> item04) {
        this.item04 = item04;
    }
}
