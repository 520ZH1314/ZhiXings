package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PostDateJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditSchedule
     * rows : {"list":{"inserted":[{"ScheduleContent":"测试日程安排内容","ScheduleRemark":"测试日程安排备注","ExecutorID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","StartDate":"2018-11-23 13:56:05","EndDate":"2018-11-22 13:56:05","ScheduleReminder":1,"IsRepeat":1,"TenantId":"00000000-0000-0000-0000-000000000001"}]}}
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
         * list : {"inserted":[{"ScheduleContent":"测试日程安排内容","ScheduleRemark":"测试日程安排备注","ExecutorID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","StartDate":"2018-11-23 13:56:05","EndDate":"2018-11-22 13:56:05","ScheduleReminder":1,"IsRepeat":1,"TenantId":"00000000-0000-0000-0000-000000000001"}]}
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
                 * ScheduleContent : 测试日程安排内容
                 * ScheduleRemark : 测试日程安排备注
                 * ExecutorID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * StartDate : 2018-11-23 13:56:05
                 * EndDate : 2018-11-22 13:56:05
                 * ScheduleReminder : 1
                 * IsRepeat : 1
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 */

                private String ScheduleContent;
                private String ScheduleRemark;
                private String ExecutorID;
                private String CreateUserID;
                private String StartDate;
                private String EndDate;
                private int ScheduleReminder;
                private int IsRepeat;
                private String TenantId;

                public String getScheduleContent() {
                    return ScheduleContent;
                }

                public void setScheduleContent(String ScheduleContent) {
                    this.ScheduleContent = ScheduleContent;
                }

                public String getScheduleRemark() {
                    return ScheduleRemark;
                }

                public void setScheduleRemark(String ScheduleRemark) {
                    this.ScheduleRemark = ScheduleRemark;
                }

                public String getExecutorID() {
                    return ExecutorID;
                }

                public void setExecutorID(String ExecutorID) {
                    this.ExecutorID = ExecutorID;
                }

                public String getCreateUserID() {
                    return CreateUserID;
                }

                public void setCreateUserID(String CreateUserID) {
                    this.CreateUserID = CreateUserID;
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

                public int getScheduleReminder() {
                    return ScheduleReminder;
                }

                public void setScheduleReminder(int ScheduleReminder) {
                    this.ScheduleReminder = ScheduleReminder;
                }

                public int getIsRepeat() {
                    return IsRepeat;
                }

                public void setIsRepeat(int IsRepeat) {
                    this.IsRepeat = IsRepeat;
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
                    dest.writeString(this.ScheduleContent);
                    dest.writeString(this.ScheduleRemark);
                    dest.writeString(this.ExecutorID);
                    dest.writeString(this.CreateUserID);
                    dest.writeString(this.StartDate);
                    dest.writeString(this.EndDate);
                    dest.writeInt(this.ScheduleReminder);
                    dest.writeInt(this.IsRepeat);
                    dest.writeString(this.TenantId);
                }

                public InsertedBean() {
                }

                protected InsertedBean(Parcel in) {
                    this.ScheduleContent = in.readString();
                    this.ScheduleRemark = in.readString();
                    this.ExecutorID = in.readString();
                    this.CreateUserID = in.readString();
                    this.StartDate = in.readString();
                    this.EndDate = in.readString();
                    this.ScheduleReminder = in.readInt();
                    this.IsRepeat = in.readInt();
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
                dest.writeList(this.inserted);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.inserted = new ArrayList<InsertedBean>();
                in.readList(this.inserted, InsertedBean.class.getClassLoader());
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

    public PostDateJson() {
    }

    protected PostDateJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PostDateJson> CREATOR = new Parcelable.Creator<PostDateJson>() {
        @Override
        public PostDateJson createFromParcel(Parcel source) {
            return new PostDateJson(source);
        }

        @Override
        public PostDateJson[] newArray(int size) {
            return new PostDateJson[size];
        }
    };
}
