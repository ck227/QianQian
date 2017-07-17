package com.ck.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class InfoType implements Parcelable {

    private int id;

    private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.typeName);
    }

    public InfoType() {
    }

    protected InfoType(Parcel in) {
        this.id = in.readInt();
        this.typeName = in.readString();
    }

    public static final Creator<InfoType> CREATOR = new Creator<InfoType>() {
        @Override
        public InfoType createFromParcel(Parcel source) {
            return new InfoType(source);
        }

        @Override
        public InfoType[] newArray(int size) {
            return new InfoType[size];
        }
    };
}
