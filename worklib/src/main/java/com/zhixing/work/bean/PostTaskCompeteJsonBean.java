package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostTaskCompeteJsonBean implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetCompleted
     * ToDoUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String SystemCurrentUserID;
    private String ToDoListId ;

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

    public String getSystemCurrentUserID() {
        return SystemCurrentUserID;
    }

    public void setSystemCurrentUserID(String SystemCurrentUserID) {
        this.SystemCurrentUserID = SystemCurrentUserID;
    }

    public String getToDoListId () {
        return ToDoListId ;
    }

    public void setToDoListId (String ToDoListId ) {
        this.ToDoListId  = ToDoListId ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeString(this.SystemCurrentUserID);
        dest.writeString(this.ToDoListId );
    }

    public PostTaskCompeteJsonBean() {
    }

    protected PostTaskCompeteJsonBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.SystemCurrentUserID = in.readString();
        this.ToDoListId  = in.readString();
    }

    public static final Creator<PostTaskCompeteJsonBean> CREATOR = new Creator<PostTaskCompeteJsonBean>() {
        @Override
        public PostTaskCompeteJsonBean createFromParcel(Parcel source) {
            return new PostTaskCompeteJsonBean(source);
        }

        @Override
        public PostTaskCompeteJsonBean[] newArray(int size) {
            return new PostTaskCompeteJsonBean[size];
        }
    };
}
