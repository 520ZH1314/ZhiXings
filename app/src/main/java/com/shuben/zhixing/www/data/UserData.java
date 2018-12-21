package com.shuben.zhixing.www.data;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserId);
        dest.writeString(this.TenantId);
        dest.writeString(this.UserCode);
        dest.writeString(this.UserName);
        dest.writeString(this.Sex);
        dest.writeString(this.PhoneNumber);
        dest.writeString(this.Email);
        dest.writeString(this.HeadShip);
        dest.writeString(this.PhotoURL);
        dest.writeString(this.DeptName);
        dest.writeString(this.TenantName);
    }

    public UserData() {
    }

    protected UserData(Parcel in) {
        this.UserId = in.readString();
        this.TenantId = in.readString();
        this.UserCode = in.readString();
        this.UserName = in.readString();
        this.Sex = in.readString();
        this.PhoneNumber = in.readString();
        this.Email = in.readString();
        this.HeadShip = in.readString();
        this.PhotoURL = in.readString();
        this.DeptName = in.readString();
        this.TenantName = in.readString();
    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
