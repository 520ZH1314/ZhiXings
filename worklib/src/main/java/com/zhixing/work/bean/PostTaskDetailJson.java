package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostTaskDetailJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetTaskDetails
     * TaskId :  51e72d58-4d7f-42a1-bb19-a184f1fd733c
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String TaskId;
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

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String TaskId) {
        this.TaskId = TaskId;
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
        dest.writeString(this.TaskId);
        dest.writeString(this.TenantId);
    }

    public PostTaskDetailJson() {
    }

    protected PostTaskDetailJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.TaskId = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<PostTaskDetailJson> CREATOR = new Parcelable.Creator<PostTaskDetailJson>() {
        @Override
        public PostTaskDetailJson createFromParcel(Parcel source) {
            return new PostTaskDetailJson(source);
        }

        @Override
        public PostTaskDetailJson[] newArray(int size) {
            return new PostTaskDetailJson[size];
        }
    };
}
