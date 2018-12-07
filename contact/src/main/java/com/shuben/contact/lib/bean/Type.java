package com.shuben.contact.lib.bean;

import java.io.Serializable;

public class Type implements Serializable {
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
    private boolean isSingle;
    private String oid;
    private String id;

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    private String pys;
    private int type;
    private boolean check;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    private String name;
    private String img;
    private String phone;
    private String zhiwei;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getPys() {
        return pys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPys(String pys) {
        this.pys = pys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZhiwei() {
        return zhiwei;
    }

    public void setZhiwei(String zhiwei) {
        this.zhiwei = zhiwei;
    }
}
