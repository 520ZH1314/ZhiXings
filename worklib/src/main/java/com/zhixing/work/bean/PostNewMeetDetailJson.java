package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PostNewMeetDetailJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : GetMeetingInfo
     * MeetingID : 81a5541f-b2aa-423d-8fc9-c72043c750b0
     * MeetingDataID : 81a5541f-b2aa-423d-8fc9-c72043c750b0
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String MeetingID;
    private String MeetingDataID;
    private String TenantId;

    public PostNewMeetDetailJson(String appCode, String apiCode, String meetingID, String meetingDataID, String tenantId) {
        AppCode = appCode;
        ApiCode = apiCode;
        MeetingID = meetingID;
        MeetingDataID = meetingDataID;
        TenantId = tenantId;
    }

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

    public String getMeetingDataID() {
        return MeetingDataID;
    }

    public void setMeetingDataID(String MeetingDataID) {
        this.MeetingDataID = MeetingDataID;
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
        dest.writeString(this.MeetingID);
        dest.writeString(this.MeetingDataID);
        dest.writeString(this.TenantId);
    }

    public PostNewMeetDetailJson() {
    }

    protected PostNewMeetDetailJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.MeetingID = in.readString();
        this.MeetingDataID = in.readString();
        this.TenantId = in.readString();
    }

    public static final Parcelable.Creator<PostNewMeetDetailJson> CREATOR = new Parcelable.Creator<PostNewMeetDetailJson>() {
        @Override
        public PostNewMeetDetailJson createFromParcel(Parcel source) {
            return new PostNewMeetDetailJson(source);
        }

        @Override
        public PostNewMeetDetailJson[] newArray(int size) {
            return new PostNewMeetDetailJson[size];
        }
    };
}
