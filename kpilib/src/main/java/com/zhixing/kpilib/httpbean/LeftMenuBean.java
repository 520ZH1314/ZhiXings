package com.zhixing.kpilib.httpbean;

import android.os.Parcel;
import android.os.Parcelable;

public class LeftMenuBean implements Parcelable {

    /**
     * AppCode : CEOAssist
     * ApiCode : GetType
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
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
        dest.writeString(this.TenantId);
    }

    public LeftMenuBean() {
    }

    protected LeftMenuBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<LeftMenuBean> CREATOR = new Parcelable.Creator<LeftMenuBean>() {
        @Override
        public LeftMenuBean createFromParcel(Parcel source) {
            return new LeftMenuBean(source);
        }

        @Override
        public LeftMenuBean[] newArray(int size) {
            return new LeftMenuBean[size];
        }
    };
}
