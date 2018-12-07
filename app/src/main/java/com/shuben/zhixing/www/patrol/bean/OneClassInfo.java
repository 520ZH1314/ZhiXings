package com.shuben.zhixing.www.patrol.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/3/15.
 */

public class OneClassInfo implements Serializable{
    private String item01;
    private String item02;
    private String item03;
    private String item04;
    private String item05;
    private String item06;
    private String item07;//巡线人
    private String item08;//责任人
    private String item09;//巡线类型
    private String item10;//车间ID
    private String item11;//区域ID
    private String item12;//线体ID
    private String item13;//巡线级别ID
    private String item14;//巡线级别ID
    private String item15;//巡线级别ID
    private String item16;//巡线级别ID
    private String item17;//巡线级别ID


    public OneClassInfo(String item01, String item02, String item03, String item04, String item05, String item06) {
        this.item01 = item01;//问题编号
        this.item02 = item02;//车间
        this.item03 = item03;//区域
        this.item04 = item04;//巡线倒计时
        this.item05 = item05;//巡线时间
        this.item06 = item06;//状态
    }

    public OneClassInfo(String item01, String item02, String item03, String item04, String item05, String item06, String item07, String item08) {
        this.item01 = item01;//问题编号
        this.item02 = item02;//车间
        this.item03 = item03;//区域
        this.item04 = item04;//巡线倒计时
        this.item05 = item05;//巡线时间
        this.item06 = item06;//状态
        this.item07 = item07;//巡线人
        this.item08 = item08;//责任人
    }

    public OneClassInfo(String item01, String item02, String item03, String item04, String item05, String item06, String item07, String item08, String item09) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
        this.item04 = item04;
        this.item05 = item05;
        this.item06 = item06;
        this.item07 = item07;
        this.item08 = item08;
        this.item09 = item09;
    }

    public OneClassInfo(String item01, String item02, String item03, String item04, String item05, String item06, String item07, String item08, String item09, String item10, String item11, String item12, String item13, String item14, String item15, String item16, String item17) {
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
        this.item11 = item11;
        this.item12 = item12;
        this.item13 = item13;
        this.item14 = item14;
        this.item15 = item15;
        this.item16 = item16;
        this.item17 = item17;
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

    public String getItem09() {
        return item09;
    }

    public void setItem09(String item09) {
        this.item09 = item09;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }

    public String getItem11() {
        return item11;
    }

    public void setItem11(String item11) {
        this.item11 = item11;
    }

    public String getItem12() {
        return item12;
    }

    public void setItem12(String item12) {
        this.item12 = item12;
    }

    public String getItem13() {
        return item13;
    }

    public void setItem13(String item13) {
        this.item13 = item13;
    }

    public String getItem14() {
        return item14;
    }

    public void setItem14(String item14) {
        this.item14 = item14;
    }

    public String getItem15() {
        return item15;
    }

    public void setItem15(String item15) {
        this.item15 = item15;
    }

    public String getItem16() {
        return item16;
    }

    public void setItem16(String item16) {
        this.item16 = item16;
    }

    public String getItem17() {
        return item17;
    }

    public void setItem17(String item17) {
        this.item17 = item17;
    }
}
