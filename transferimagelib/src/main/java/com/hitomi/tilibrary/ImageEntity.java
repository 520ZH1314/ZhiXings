package com.hitomi.tilibrary;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片对象
 */
public class ImageEntity implements Parcelable{
    /**图片地址*/
    private String imageUrl;
    /**是否是本地图片,true是*/
    private boolean isLocal;

    public ImageEntity(String imageUrl, boolean isLocal) {
        this.imageUrl = imageUrl;
        this.isLocal = isLocal;
    }

    public ImageEntity() {
    }

    protected ImageEntity(Parcel in) {
        imageUrl = in.readString();
        isLocal = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageEntity> CREATOR = new Creator<ImageEntity>() {
        @Override
        public ImageEntity createFromParcel(Parcel in) {
            return new ImageEntity(in);
        }

        @Override
        public ImageEntity[] newArray(int size) {
            return new ImageEntity[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
