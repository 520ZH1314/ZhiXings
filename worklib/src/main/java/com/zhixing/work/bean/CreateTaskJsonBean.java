package com.zhixing.work.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CreateTaskJsonBean implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditTask
     * user : {"CCUserId":"E7936890-6C2A-438A-BDBD-682ED5D7E912,6DA5BB71-2DC8-46DA-98FF-9814069A7C7E","ToDoUserId":"2430147E-A451-4841-B027-717C6C8F27A8"}
     * rows : {"list":{"inserted":[{"TaskDesc":"测试","DueDate":"2018-11-23 16:01:15","CreateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001","CurrentOperateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E"}]}}
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
         * CCUserId : E7936890-6C2A-438A-BDBD-682ED5D7E912,6DA5BB71-2DC8-46DA-98FF-9814069A7C7E
         * ToDoUserId : 2430147E-A451-4841-B027-717C6C8F27A8
         */

        private String CCUserId;
        private String ToDoUserId;

        public UserBean(String CCUserId, String toDoUserId) {
            this.CCUserId = CCUserId;
            ToDoUserId = toDoUserId;
        }

        public String getCCUserId() {
            return CCUserId;
        }

        public void setCCUserId(String CCUserId) {
            this.CCUserId = CCUserId;
        }

        public String getToDoUserId() {
            return ToDoUserId;
        }

        public void setToDoUserId(String ToDoUserId) {
            this.ToDoUserId = ToDoUserId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.CCUserId);
            dest.writeString(this.ToDoUserId);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.CCUserId = in.readString();
            this.ToDoUserId = in.readString();
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
         * list : {"inserted":[{"TaskDesc":"测试","DueDate":"2018-11-23 16:01:15","CreateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","TenantId":"00000000-0000-0000-0000-000000000001","CurrentOperateUserId":"06E78CC3-13D2-48AD-B65D-2D19B628E05E"}]}
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

            protected ListBean(Parcel in) {
                inserted = in.createTypedArrayList(InsertedBean.CREATOR);
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public ListBean(List<InsertedBean> inserted) {
                this.inserted = inserted;
            }

            public List<InsertedBean> getInserted() {
                return inserted;
            }

            public void setInserted(List<InsertedBean> inserted) {
                this.inserted = inserted;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeTypedList(inserted);
            }

            public static class InsertedBean implements Parcelable {
                /**
                 * TaskDesc : 测试
                 * DueDate : 2018-11-23 16:01:15
                 * CreateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 * CurrentOperateUserId : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 */

                private String TaskDesc;
                private String DueDate;
                private String CreateUserId;
                private String TenantId;
                private String CurrentOperateUserId;

                public String getTaskDesc() {
                    return TaskDesc;
                }

                public void setTaskDesc(String TaskDesc) {
                    this.TaskDesc = TaskDesc;
                }

                public String getDueDate() {
                    return DueDate;
                }

                public void setDueDate(String DueDate) {
                    this.DueDate = DueDate;
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

                public String getCurrentOperateUserId() {
                    return CurrentOperateUserId;
                }

                public void setCurrentOperateUserId(String CurrentOperateUserId) {
                    this.CurrentOperateUserId = CurrentOperateUserId;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.TaskDesc);
                    dest.writeString(this.DueDate);
                    dest.writeString(this.CreateUserId);
                    dest.writeString(this.TenantId);
                    dest.writeString(this.CurrentOperateUserId);
                }

                public InsertedBean() {
                }

                protected InsertedBean(Parcel in) {
                    this.TaskDesc = in.readString();
                    this.DueDate = in.readString();
                    this.CreateUserId = in.readString();
                    this.TenantId = in.readString();
                    this.CurrentOperateUserId = in.readString();
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

    public CreateTaskJsonBean() {
    }

    protected CreateTaskJsonBean(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.user = in.readParcelable(UserBean.class.getClassLoader());
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CreateTaskJsonBean> CREATOR = new Parcelable.Creator<CreateTaskJsonBean>() {
        @Override
        public CreateTaskJsonBean createFromParcel(Parcel source) {
            return new CreateTaskJsonBean(source);
        }

        @Override
        public CreateTaskJsonBean[] newArray(int size) {
            return new CreateTaskJsonBean[size];
        }
    };
}
