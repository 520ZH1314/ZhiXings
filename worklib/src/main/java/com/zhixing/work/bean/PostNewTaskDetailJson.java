package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostNewTaskDetailJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetTaskDetails
     * ToDoListId :  51e72d58-4d7f-42a1-bb19-a184f1fd733c
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String ToDoListId;
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

    public String getToDoListId() {
        return ToDoListId;
    }

    public void setToDoListId(String ToDoListId) {
        this.ToDoListId = ToDoListId;
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
        dest.writeString(this.ToDoListId);
        dest.writeString(this.TenantId);
    }

    public PostNewTaskDetailJson() {
    }

    protected PostNewTaskDetailJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.ToDoListId = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<PostNewTaskDetailJson> CREATOR = new Parcelable.Creator<PostNewTaskDetailJson>() {
        @Override
        public PostNewTaskDetailJson createFromParcel(Parcel source) {
            return new PostNewTaskDetailJson(source);
        }

        @Override
        public PostNewTaskDetailJson[] newArray(int size) {
            return new PostNewTaskDetailJson[size];
        }
    };
}