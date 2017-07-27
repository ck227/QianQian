package com.ck.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chendaye on 2017/7/27.
 */

public class Province implements Parcelable {


    /**
     * id : 1
     * province : 北京市
     * provinceid : 10001
     * valid : 1
     */

    private int id;
    private String province;
    private int provinceid;
    private int valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.province);
        dest.writeInt(this.provinceid);
        dest.writeInt(this.valid);
    }

    public Province() {
    }

    protected Province(Parcel in) {
        this.id = in.readInt();
        this.province = in.readString();
        this.provinceid = in.readInt();
        this.valid = in.readInt();
    }

    public static final Parcelable.Creator<Province> CREATOR = new Parcelable.Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}
