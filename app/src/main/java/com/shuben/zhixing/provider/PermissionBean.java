package com.shuben.zhixing.provider;

public class PermissionBean {

    /**
     * AppCode : EPS
     * PermissionCode : EPS
     * PermissionName : EPS平台
     * ParentCode : 0
     * Seq : 1
     */

    private String AppCode;
    private String PermissionCode;
    private String PermissionName;
    private String ParentCode;
    private int Seq;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getPermissionCode() {
        return PermissionCode;
    }

    public void setPermissionCode(String PermissionCode) {
        this.PermissionCode = PermissionCode;
    }

    public String getPermissionName() {
        return PermissionName;
    }

    public void setPermissionName(String PermissionName) {
        this.PermissionName = PermissionName;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String ParentCode) {
        this.ParentCode = ParentCode;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int Seq) {
        this.Seq = Seq;
    }
}
