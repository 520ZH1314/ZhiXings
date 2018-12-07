package com.shuben.zhixing.www.patrol.bean;

/**
 * Created by Geyan on 2018/5/21.
 */

public class GroupTaskInfo {
    private String item01;//问题记录ID
    private String item02;//巡线任务记录ID
    private String item03;//问题描述
    private String item04;//严重程度
    private String item05;//责任部门ID
    private String item06;//责任部门名称
    private String item07;//责任人ID
    private String item08;//责任人姓名
    private String item09;//责任部门ID
    private String item10;//状态

    public GroupTaskInfo(String item01, String item02, String item03, String item04, String item05, String item06, String item07, String item08, String item09, String item10) {
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
}
