package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostTaskCreateJsonBean implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetCompleted
     * ToDoUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String CreateUserId;
    private String TenantId;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String ApiCode) {
        this.ApiCode = ApiCode;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeString(this.CreateUserId);
        dest.writeString(this.TenantId);
    }

    public PostTaskCreateJsonBean() {
    }

    protected PostTaskCreateJsonBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.CreateUserId = in.readString();
        this.TenantId = in.readString();
    }

    public static final Creator<PostTaskCreateJsonBean> CREATOR = new Creator<PostTaskCreateJsonBean>() {
        @Override
        public PostTaskCreateJsonBean createFromParcel(Parcel source) {
            return new PostTaskCreateJsonBean(source);
        }

        @Override
        public PostTaskCreateJsonBean[] newArray(int size) {
            return new PostTaskCreateJsonBean[size];
        }
    };
}
