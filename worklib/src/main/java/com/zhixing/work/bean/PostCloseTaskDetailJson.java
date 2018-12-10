package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostCloseTaskDetailJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetTaskDetails
     * TaskId :  51e72d58-4d7f-42a1-bb19-a184f1fd733c
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String TaskId;
    private String CancelRemark;//取消原因

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

    public String getCancelRemark() {
        return CancelRemark;
    }

    public void setCancelRemark(String CancelRemark) {
        this.CancelRemark = CancelRemark;
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
        dest.writeString(this.CancelRemark);
    }

    public PostCloseTaskDetailJson() {
    }

    protected PostCloseTaskDetailJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.TaskId = in.readString();
        this.CancelRemark = in.readString();
    }

    public static final Creator<PostCloseTaskDetailJson> CREATOR = new Creator<PostCloseTaskDetailJson>() {
        @Override
        public PostCloseTaskDetailJson createFromParcel(Parcel source) {
            return new PostCloseTaskDetailJson(source);
        }

        @Override
        public PostCloseTaskDetailJson[] newArray(int size) {
            return new PostCloseTaskDetailJson[size];
        }
    };
}
