package com.shuben.zhixing.www.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userdata")
public class UseEntity {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * UserId : 6334b1b5-10f4-483a-b2dd-a13054458c03
     * TenantId : 2248f8eb-2d6b-4676-815f-494e9b2ac5ff
     * UserCode : huangtao
     * UserName : 黄涛
     * Sex : 男
     * PhoneNumber : 15936424949
     * Email : huangtao@m3lean.com
     * HeadShip : 高级软件工程师
     * PhotoURL : /upload/UserPhoto/201708/9f38f50c-ace7-41a4-83b7-1cc06063a8b5.jpg
     * DeptName : 软件开发部
     * TenantName : 深圳市谋事精益信息咨询有限公司
     */
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private String UserId;
    private String TenantId;
    private String UserCode;
    private String UserName;
    private String Sex;
    private String PhoneNumber;
    private String Email;
    private String HeadShip;
    private String PhotoURL;
    private String DeptName;
    private String TenantName;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getHeadShip() {
        return HeadShip;
    }

    public void setHeadShip(String HeadShip) {
        this.HeadShip = HeadShip;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String PhotoURL) {
        this.PhotoURL = PhotoURL;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    public String getTenantName() {
        return TenantName;
    }

    public void setTenantName(String TenantName) {
        this.TenantName = TenantName;
    }
}
