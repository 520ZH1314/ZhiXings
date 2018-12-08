package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PostTaskReplyJson implements Parcelable {


    /**
     * AppCode : CEOAssist
     * ApiCode : EditComment
     * rows : {"list":{"inserted":[{"CommentSourceID":"3211a50d-319b-4bc0-8ce8-49b0c857692a","CommentUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CommentText":"测试评论","ToUserID":"B8D9CECC-6E9A-B710-6F4D-E9D7EFF7089E","TenantId":"00000000-0000-0000-0000-000000000001"}]}}
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
         * list : {"inserted":[{"CommentSourceID":"3211a50d-319b-4bc0-8ce8-49b0c857692a","CommentUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CreateUserID":"06E78CC3-13D2-48AD-B65D-2D19B628E05E","CommentText":"测试评论","ToUserID":"B8D9CECC-6E9A-B710-6F4D-E9D7EFF7089E","TenantId":"00000000-0000-0000-0000-000000000001"}]}
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
                 * CommentSourceID : 3211a50d-319b-4bc0-8ce8-49b0c857692a
                 * CommentUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * CreateUserID : 06E78CC3-13D2-48AD-B65D-2D19B628E05E
                 * CommentText : 测试评论
                 * ToUserID : B8D9CECC-6E9A-B710-6F4D-E9D7EFF7089E
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 */

                private String CommentSourceID;
                private String CommentUserID;
                private String CreateUserID;
                private String CommentText;
                private String ToUserID;
                private String TenantId;

                public String getCommentSourceID() {
                    return CommentSourceID;
                }

                public void setCommentSourceID(String CommentSourceID) {
                    this.CommentSourceID = CommentSourceID;
                }

                public String getCommentUserID() {
                    return CommentUserID;
                }

                public void setCommentUserID(String CommentUserID) {
                    this.CommentUserID = CommentUserID;
                }

                public String getCreateUserID() {
                    return CreateUserID;
                }

                public void setCreateUserID(String CreateUserID) {
                    this.CreateUserID = CreateUserID;
                }

                public String getCommentText() {
                    return CommentText;
                }

                public void setCommentText(String CommentText) {
                    this.CommentText = CommentText;
                }

                public String getToUserID() {
                    return ToUserID;
                }

                public void setToUserID(String ToUserID) {
                    this.ToUserID = ToUserID;
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
                    dest.writeString(this.CommentSourceID);
                    dest.writeString(this.CommentUserID);
                    dest.writeString(this.CreateUserID);
                    dest.writeString(this.CommentText);
                    dest.writeString(this.ToUserID);
                    dest.writeString(this.TenantId);
                }

                public InsertedBean() {
                }

                protected InsertedBean(Parcel in) {
                    this.CommentSourceID = in.readString();
                    this.CommentUserID = in.readString();
                    this.CreateUserID = in.readString();
                    this.CommentText = in.readString();
                    this.ToUserID = in.readString();
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

    public PostTaskReplyJson() {
    }

    protected PostTaskReplyJson(Parcel in) {
        this.AppCode = in.readString();
        this.ApiCode = in.readString();
        this.rows = in.readParcelable(RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PostTaskReplyJson> CREATOR = new Parcelable.Creator<PostTaskReplyJson>() {
        @Override
        public PostTaskReplyJson createFromParcel(Parcel source) {
            return new PostTaskReplyJson(source);
        }

        @Override
        public PostTaskReplyJson[] newArray(int size) {
            return new PostTaskReplyJson[size];
        }
    };
}
