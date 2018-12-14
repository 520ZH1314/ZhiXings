package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostNewMeetJoinJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditConfirmMeeting
     *  MeetingDataID  : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     * SystemCurrentUserID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     */

    private String AppCode;
    private String ApiCode;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String apiCode) {
        ApiCode = apiCode;
    }

    public String getMeetingDataID() {
        return MeetingDataID;
    }

    public void setMeetingDataID(String meetingDataID) {
        MeetingDataID = meetingDataID;
    }

    public String getSystemCurrentUserID() {
        return SystemCurrentUserID;
    }

    public void setSystemCurrentUserID(String systemCurrentUserID) {
        SystemCurrentUserID = systemCurrentUserID;
    }

    private String MeetingDataID;
    private String SystemCurrentUserID;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeString(this.MeetingDataID);
        dest.writeString(this.SystemCurrentUserID);
    }

    public PostNewMeetJoinJson() {
    }

    protected PostNewMeetJoinJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.MeetingDataID = in.readString();
        this.SystemCurrentUserID = in.readString();
    }

    public static final Parcelable.Creator<PostNewMeetJoinJson> CREATOR = new Parcelable.Creator<PostNewMeetJoinJson>() {
        @Override
        public PostNewMeetJoinJson createFromParcel(Parcel source) {
            return new PostNewMeetJoinJson(source);
        }

        @Override
        public PostNewMeetJoinJson[] newArray(int size) {
            return new PostNewMeetJoinJson[size];
        }
    };
}
