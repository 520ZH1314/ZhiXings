package com.shuben.zhixing.module.mass.bean;

import java.io.Serializable;

public class QC_Reason implements Serializable {
    private String id;
    private String name;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
