package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PostCreateMeetRecordJson implements Parcelable {

    /**
     * AppCode : CEOAssist
     * ApiCode : EditMeetingDetails
     * rows : {"list":{"inserted":[{"MeetingID":"e7274ea7-d651-4e2b-bbad-effc4e600c01","MeetingDes":"测试会议纪要","RecorderID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","UpdateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001"}]}}
     */

    private String AppCode;
    private String ApiCode;
    private RowsBean rows;

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

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Parcelable {
        /**
         * list : {"inserted":[{"MeetingID":"e7274ea7-d651-4e2b-bbad-effc4e600c01","MeetingDes":"测试会议纪要","RecorderID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","UpdateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001"}]}
         */

        private ListBean list;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            private List<InsertedBean> inserted;

            public List<InsertedBean> getInserted() {
                return inserted;
            }

            public void setInserted(List<InsertedBean> inserted) {
                this.inserted = inserted;
            }

            public static class InsertedBean implements Parcelable {
                /**
                 * MeetingID : e7274ea7-d651-4e2b-bbad-effc4e600c01
                 * MeetingDes : 测试会议纪要
                 * RecorderID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * UpdateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 */

                private String MeetingID;
                private String MeetingDes;
                private String RecorderID;
                private String CreateUserID;
                private String UpdateUserID;
                private String TenantId;

                public InsertedBean(String meetingID, String meetingDes, String recorderID, String createUserID, String updateUserID, String tenantId) {
                    MeetingID = meetingID;
                    MeetingDes = meetingDes;
                    RecorderID = recorderID;
                    CreateUserID = createUserID;
                    UpdateUserID = updateUserID;
                    TenantId = tenantId;
                }

                public String getMeetingID() {
                    return MeetingID;
                }

                public void setMeetingID(String MeetingID) {
                    this.MeetingID = MeetingID;
                }

                public String getMeetingDes() {
                    return MeetingDes;
                }

                public void setMeetingDes(String MeetingDes) {
                    this.MeetingDes = MeetingDes;
                }

                public String getRecorderID() {
                    return RecorderID;
                }

                public void setRecorderID(String RecorderID) {
                    this.RecorderID = RecorderID;
                }

                public String getCreateUserID() {
                    return CreateUserID;
                }

                public void setCreateUserID(String CreateUserID) {
                    this.CreateUserID = CreateUserID;
                }

                public String getUpdateUserID() {
                    return UpdateUserID;
                }

                public void setUpdateUserID(String UpdateUserID) {
                    this.UpdateUserID = UpdateUserID;
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
                    dest.writeString(this.MeetingID);
                    dest.writeString(this.MeetingDes);
                    dest.writeString(this.RecorderID);
                    dest.writeString(this.CreateUserID);
                    dest.writeString(this.UpdateUserID);
                    dest.writeString(this.TenantId);
                }

                protected InsertedBean(Parcel in) {
                    this.MeetingID = in.readString();
                    this.MeetingDes = in.readString();
                    this.RecorderID = in.readString();
                    this.CreateUserID = in.readString();
                    this.UpdateUserID = in.readString();
                    this.TenantId = in.readString();
                }

                public static final Creator<InsertedBean> CREATOR = new Creator<InsertedBean>() {
                    @Override
                    public InsertedBean createFromParcel(Parcel source) {
                        return new InsertedBean(source);
                    }

                    @Override
                    public InsertedBean[] newArray(int size) {
                        return new InsertedBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeTypedList(this.inserted);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.inserted = in.createTypedArrayList(InsertedBean.CREATOR);
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.list, flags);
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.list = in.readParcelable(ListBean.class.getClassLoader());
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel source) {
                return new RowsBean(source);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AppCode);
        dest.writeString(this.ApiCode);
        dest.writeParcelable(this.rows, flags);
    }

    public PostCreateMeetRecordJson() {
    }

    protected PostCreateMeetRecordJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PostCreateMeetRecordJson> CREATOR = new Parcelable.Creator<PostCreateMeetRecordJson>() {
        @Override
        public PostCreateMeetRecordJson createFromParcel(Parcel source) {
            return new PostCreateMeetRecordJson(source);
        }

        @Override
        public PostCreateMeetRecordJson[] newArray(int size) {
            return new PostCreateMeetRecordJson[size];
        }
    };
}
