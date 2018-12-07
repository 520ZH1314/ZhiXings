package com.shuben.zhixing.module.mass.bean;

import java.io.Serializable;

public class WxBean implements Serializable {
    private String name;
    private int count;
    private float val;

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
