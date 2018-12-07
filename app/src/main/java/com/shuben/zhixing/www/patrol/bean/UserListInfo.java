package com.shuben.zhixing.www.patrol.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geyan on 2018/5/17.
 */

public class UserListInfo implements Serializable {
    private String item01;
    private String item02;
    private List<UserInfo> item03;

    public UserListInfo(String item01, String item02,  List<UserInfo> item03) {
        this.item01 = item01;
        this.item02 = item02;
        this.item03 = item03;
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

    public  List<UserInfo> getItem03() {
        return item03;
    }

    public void setItem03( List<UserInfo> item03) {
        this.item03 = item03;
    }
}

