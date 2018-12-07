package com.shuben.zhixing.www.patrol.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/5/16.
 */

public class FindInfo implements Serializable {
    private String item01;
    private String item02;
    private String item03;
    private double item04;
    private String item05;
    private String item06;
    private String item07;
    private String item08;
    private int item09;
    private String item10;


    public FindInfo(String item01, String item02, String item03, double item04, String item05, String item06, String item07, String item08, int item09, String item10) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
        this.item04 = item04;
        this.item05 = item05;
        this.item06 = item06;
        this.item07 = item07;
        this.item08 = item08;
        this.item09 = item09;
        this.item10 = item10;
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

    public double getItem04() {
        return item04;
    }

    public void setItem04(double item04) {
        this.item04 = item04;
    }

    public String getItem05() {
        return item05;
    }

    public void setItem05(String item05) {
        this.item05 = item05;
    }

    public String getItem06() {
        return item06;
    }

    public void setItem06(String item06) {
        this.item06 = item06;
    }

    public String getItem07() {
        return item07;
    }

    public void setItem07(String item07) {
        this.item07 = item07;
    }

    public String getItem08() {
        return item08;
    }

    public void setItem08(String item08) {
        this.item08 = item08;
    }

    public int getItem09() {
        return item09;
    }

    public void setItem09(int item09) {
        this.item09 = item09;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }
}
