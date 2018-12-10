package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostTaskListJsonBean implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetCompleted
     * ToDoUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String ToDoUserId;
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

    public String getToDoUserId() {
        return ToDoUserId;
    }

    public void setToDoUserId(String ToDoUserId) {
        this.ToDoUserId = ToDoUserId;
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
        dest.writeString(this.ToDoUserId);
        dest.writeString(this.TenantId);
    }

    public PostTaskListJsonBean() {
    }

    protected PostTaskListJsonBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.ToDoUserId = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<PostTaskListJsonBean> CREATOR = new Parcelable.Creator<PostTaskListJsonBean>() {
        @Override
        public PostTaskListJsonBean createFromParcel(Parcel source) {
            return new PostTaskListJsonBean(source);
        }

        @Override
        public PostTaskListJsonBean[] newArray(int size) {
            return new PostTaskListJsonBean[size];
        }
    };
}
