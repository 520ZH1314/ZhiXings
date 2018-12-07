package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PostCreateMeetJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditMeeting
     * user : {"HostID":"E7936890-6C2A-438A-BDBD-682ED5D7E912","ParticipantID":"6DA5BB71-2DC8-46DA-98FF-9814069A7C7E, 2430147E-A451-4841-B027-717C6C8F27A8","RecorderID":"5318EA7E-9917-4489-B934-607C564392D0"}
     * rows : {"list":{"inserted":[{"MeetingContent":"测试内容","MeetingPlace":"测试地址","StartDate":"2018/11/12 16:48","EndDate":"2018/11/12 16:48","MeetingReminder":1,"CreateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001"}]}}
     */

    private String AppCode;
    private String ApiCode;
    private UserBean user;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class UserBean implements Parcelable {
        /**
         * HostID : E7936890-6C2A-438A-BDBD-682ED5D7E912
         * ParticipantID : 6DA5BB71-2DC8-46DA-98FF-9814069A7C7E, 2430147E-A451-4841-B027-717C6C8F27A8
         * RecorderID : 5318EA7E-9917-4489-B934-607C564392D0
         */

        private String HostID;
        private String ParticipantID;
        private String RecorderID;

        public String getHostID() {
            return HostID;
        }

        public void setHostID(String HostID) {
            this.HostID = HostID;
        }

        public String getParticipantID() {
            return ParticipantID;
        }

        public void setParticipantID(String ParticipantID) {
            this.ParticipantID = ParticipantID;
        }

        public String getRecorderID() {
            return RecorderID;
        }

        public void setRecorderID(String RecorderID) {
            this.RecorderID = RecorderID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.HostID);
            dest.writeString(this.ParticipantID);
            dest.writeString(this.RecorderID);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.HostID = in.readString();
            this.ParticipantID = in.readString();
            this.RecorderID = in.readString();
        }

        public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
            @Override
            public UserBean createFromParcel(Parcel source) {
                return new UserBean(source);
            }

            @Override
            public UserBean[] newArray(int size) {
                return new UserBean[size];
            }
        };
    }

    public static class RowsBean implements Parcelable {
        /**
         * list : {"inserted":[{"MeetingContent":"测试内容","MeetingPlace":"测试地址","StartDate":"2018/11/12 16:48","EndDate":"2018/11/12 16:48","MeetingReminder":1,"CreateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001"}]}
         */

        private ListBean list;

        public RowsBean(ListBean list) {
            this.list = list;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            private List<InsertedBean> inserted;

            public ListBean(List<InsertedBean> inserted) {
                this.inserted = inserted;
            }

            public List<InsertedBean> getInserted() {
                return inserted;
            }

            public void setInserted(List<InsertedBean> inserted) {
                this.inserted = inserted;
            }

            public static class InsertedBean implements Parcelable {
                /**
                 * MeetingContent : 测试内容
                 * MeetingPlace : 测试地址
                 * StartDate : 2018/11/12 16:48
                 * EndDate : 2018/11/12 16:48
                 * MeetingReminder : 1
                 * CreateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 */

                private String MeetingContent;
                private String MeetingPlace;
                private String StartDate;
                private String EndDate;
                private int MeetingReminder;
                private String CreateUserId;
                private String TenantId;

                public String getMeetingContent() {
                    return MeetingContent;
                }

                public void setMeetingContent(String MeetingContent) {
                    this.MeetingContent = MeetingContent;
                }

                public String getMeetingPlace() {
                    return MeetingPlace;
                }

                public void setMeetingPlace(String MeetingPlace) {
                    this.MeetingPlace = MeetingPlace;
                }

                public String getStartDate() {
                    return StartDate;
                }

                public void setStartDate(String StartDate) {
                    this.StartDate = StartDate;
                }

                public String getEndDate() {
                    return EndDate;
                }

                public void setEndDate(String EndDate) {
                    this.EndDate = EndDate;
                }

                public int getMeetingReminder() {
                    return MeetingReminder;
                }

                public void setMeetingReminder(int MeetingReminder) {
                    this.MeetingReminder = MeetingReminder;
                }

                public String getCreateUserId() {
                    return CreateUserId;
                }

                public void setCreateUserId(String CreateUserId) {
                    this.CreateUserId = CreateUserId;
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
                    dest.writeString(this.MeetingContent);
                    dest.writeString(this.MeetingPlace);
                    dest.writeString(this.StartDate);
                    dest.writeString(this.EndDate);
                    dest.writeInt(this.MeetingReminder);
                    dest.writeString(this.CreateUserId);
                    dest.writeString(this.TenantId);
                }

                public InsertedBean() {
                }

                protected InsertedBean(Parcel in) {
                    this.MeetingContent = in.readString();
                    this.MeetingPlace = in.readString();
                    this.StartDate = in.readString();
                    this.EndDate = in.readString();
                    this.MeetingReminder = in.readInt();
                    this.CreateUserId = in.readString();
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
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.rows, flags);
    }

    public PostCreateMeetJson() {
    }

    protected PostCreateMeetJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.user = in.readParcelable(UserBean.class.getClassLoader());
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PostCreateMeetJson> CREATOR = new Parcelable.Creator<PostCreateMeetJson>() {
        @Override
        public PostCreateMeetJson createFromParcel(Parcel source) {
            return new PostCreateMeetJson(source);
        }

        @Override
        public PostCreateMeetJson[] newArray(int size) {
            return new PostCreateMeetJson[size];
        }
    };
}
