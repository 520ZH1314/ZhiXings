package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostMeetJoinJson implements Parcelable {

    /**
     * AppCode : CEOAssist
     * ApiCode : EditConfirmMeeting
     * MeetingID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     * SystemCurrentUserID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     */

    private String AppCode;
    private String ApiCode;
    private String MeetingID;
    private String SystemCurrentUserID;

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

    public String getMeetingID() {
        return MeetingID;
    }

    public void setMeetingID(String MeetingID) {
        this.MeetingID = MeetingID;
    }

    public String getSystemCurrentUserID() {
        return SystemCurrentUserID;
    }

    public void setSystemCurrentUserID(String SystemCurrentUserID) {
        this.SystemCurrentUserID = SystemCurrentUserID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeString(this.MeetingID);
        dest.writeString(this.SystemCurrentUserID);
    }

    public PostMeetJoinJson() {
    }

    protected PostMeetJoinJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.MeetingID = in.readString();
        this.SystemCurrentUserID = in.readString();
    }

    public static final Parcelable.Creator<PostMeetJoinJson> CREATOR = new Parcelable.Creator<PostMeetJoinJson>() {
        @Override
        public PostMeetJoinJson createFromParcel(Parcel source) {
            return new PostMeetJoinJson(source);
        }

        @Override
        public PostMeetJoinJson[] newArray(int size) {
            return new PostMeetJoinJson[size];
        }
    };
}
