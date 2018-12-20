package com.shuben.zhixing.www.data;

public class UserData {
    private String UserId;
    private String TenantId;
    private String UserCode;
    private String UserName;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHeadShip() {
        return HeadShip;
    }

    public void setHeadShip(String headShip) {
        HeadShip = headShip;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getTenantName() {
        return TenantName;
    }

    public void setTenantName(String tenantName) {
        TenantName = tenantName;
    }

    private String Sex;
    private String PhoneNumber;
    private String Email;
    private String HeadShip;
    private String PhotoURL;
    private String DeptName;
    private String TenantName;
}
