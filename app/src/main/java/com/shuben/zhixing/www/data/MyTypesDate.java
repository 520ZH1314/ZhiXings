package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class MyTypesDate implements Serializable{
    private String id;//员工
    private String name;//员工
    private String tenantId;

    public MyTypesDate(String id, String name,String tenantId) {
        this.id = id;
        this.name = name;
        this.tenantId=tenantId;
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

    public String getTnantId() {
        return tenantId;
    }

    public void setTnantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
