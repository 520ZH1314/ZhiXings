package com.zhixing.kpilib.httpbean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostKpiBean implements Parcelable {

    /**
     * AppCode : CEOAssist
     * ApiCode : GetParameter
     * TypeCode : Stock
     * CycleCode : Day
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String TypeCode;
    private String CycleCode;
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

    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String TypeCode) {
        this.TypeCode = TypeCode;
    }

    public String getCycleCode() {
        return CycleCode;
    }

    public void setCycleCode(String CycleCode) {
        this.CycleCode = CycleCode;
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
        dest.writeString(this.TypeCode);
        dest.writeString(this.CycleCode);
        dest.writeString(this.TenantId);
    }

    public PostKpiBean() {
    }

    protected PostKpiBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.TypeCode = in.readString();
        this.CycleCode = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<PostKpiBean> CREATOR = new Parcelable.Creator<PostKpiBean>() {
        @Override
        public PostKpiBean createFromParcel(Parcel source) {
            return new PostKpiBean(source);
        }

        @Override
        public PostKpiBean[] newArray(int size) {
            return new PostKpiBean[size];
        }
    };
}
