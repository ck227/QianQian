package com.ck.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chendaye on 2017/7/27.
 */

public class City implements Parcelable {


    /**
     * city : 北京三环
     * cityid : 11001
     * fatherid : 10001
     * id : 1
     * valid : 1
     */

    private String city;
    private int cityid;
    private int fatherid;
    private int id;
    private int valid;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getFatherid() {
        return fatherid;
    }

    public void setFatherid(int fatherid) {
        this.fatherid = fatherid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        dest.writeString(this.city);
        dest.writeInt(this.cityid);
        dest.writeInt(this.fatherid);
        dest.writeInt(this.id);
        dest.writeInt(this.valid);
    }

    public City() {
    }

    protected City(Parcel in) {
        this.city = in.readString();
        this.cityid = in.readInt();
        this.fatherid = in.readInt();
        this.id = in.readInt();
        this.valid = in.readInt();
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
