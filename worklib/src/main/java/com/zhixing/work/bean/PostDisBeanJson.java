package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostDisBeanJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditConcelMeeting
     * MeetingID : 3a7da71e-c12a-4c01-a945-5c78c3f7decb
     * MeetingRemark : 测试
     */

    private String AppCode;
    private String ApiCode;
    private String MeetingID;
    private String MeetingRemark;

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

    public String getMeetingRemark() {
        return MeetingRemark;
    }

    public void setMeetingRemark(String MeetingRemark) {
        this.MeetingRemark = MeetingRemark;
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
        dest.writeString(this.MeetingRemark);
    }

    public PostDisBeanJson() {
    }

    protected PostDisBeanJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.MeetingID = in.readString();
        this.MeetingRemark = in.readString();
    }

    public static final Parcelable.Creator<PostDisBeanJson> CREATOR = new Parcelable.Creator<PostDisBeanJson>() {
        @Override
        public PostDisBeanJson createFromParcel(Parcel source) {
            return new PostDisBeanJson(source);
        }

        @Override
        public PostDisBeanJson[] newArray(int size) {
            return new PostDisBeanJson[size];
        }
    };
}
