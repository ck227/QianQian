package com.ck.bean.credit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chendaye on 2017/7/19.
 */

public class GetCreditAmount implements Parcelable {

    private int id;

    private String amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.amount);
    }

    public GetCreditAmount() {
    }

    protected GetCreditAmount(Parcel in) {
        this.id = in.readInt();
        this.amount = in.readString();
    }

    public static final Parcelable.Creator<GetCreditAmount> CREATOR = new Parcelable.Creator<GetCreditAmount>() {
        @Override
        public GetCreditAmount createFromParcel(Parcel source) {
            return new GetCreditAmount(source);
        }

        @Override
        public GetCreditAmount[] newArray(int size) {
            return new GetCreditAmount[size];
        }
    };
}
