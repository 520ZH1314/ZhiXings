package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostCompeteMeetJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditConcelMeeting
     * MeetingID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     */

    private String AppCode;
    private String ApiCode;
    private String MeetingID;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeString(this.MeetingID);
    }

    public PostCompeteMeetJson() {
    }

    protected PostCompeteMeetJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.MeetingID = in.readString();
    }

    public static final Parcelable.Creator<PostCompeteMeetJson> CREATOR = new Parcelable.Creator<PostCompeteMeetJson>() {
        @Override
        public PostCompeteMeetJson createFromParcel(Parcel source) {
            return new PostCompeteMeetJson(source);
        }

        @Override
        public PostCompeteMeetJson[] newArray(int size) {
            return new PostCompeteMeetJson[size];
        }
    };
}
