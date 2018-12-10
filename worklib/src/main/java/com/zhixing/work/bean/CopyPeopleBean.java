package com.zhixing.work.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CopyPeopleBean implements Parcelable {
    public CopyPeopleBean(String copyName) {
        this.copyName = copyName;
    }

    public String getCopyName() {
        return copyName;
    }

    public void setCopyName(String copyName) {
        this.copyName = copyName;
    }

    public String copyName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.copyName);
    }

    public CopyPeopleBean() {
    }

    protected CopyPeopleBean(Parcel in) {
        this.copyName = in.readString();
    }

    public static final Parcelable.Creator<CopyPeopleBean> CREATOR = new Parcelable.Creator<CopyPeopleBean>() {
        @Override
        public CopyPeopleBean createFromParcel(Parcel source) {
            return new CopyPeopleBean(source);
        }

        @Override
        public CopyPeopleBean[] newArray(int size) {
            return new CopyPeopleBean[size];
        }
    };
}
