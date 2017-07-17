package com.ck.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class CheckStatus implements Parcelable {

    // 0：未提交审核 1：待审核 2:通过 3：未通过
    /**
     * user_bank_audit : 0
     * user_book_phone_audit : 0
     * user_card_audit : 0
     * user_info_audit : 0
     * user_phone_service_audit : 0
     * user_tb_audit : 0
     * user_zfb_audit : 0
     */

    private int user_bank_audit;
    private int user_book_phone_audit;
    private int user_card_audit;
    private int user_info_audit;
    private int user_phone_service_audit;
    private int user_tb_audit;
    private int user_zfb_audit;

    public int getUser_bank_audit() {
        return user_bank_audit;
    }

    public void setUser_bank_audit(int user_bank_audit) {
        this.user_bank_audit = user_bank_audit;
    }

    public int getUser_book_phone_audit() {
        return user_book_phone_audit;
    }

    public void setUser_book_phone_audit(int user_book_phone_audit) {
        this.user_book_phone_audit = user_book_phone_audit;
    }

    public int getUser_card_audit() {
        return user_card_audit;
    }

    public void setUser_card_audit(int user_card_audit) {
        this.user_card_audit = user_card_audit;
    }

    public int getUser_info_audit() {
        return user_info_audit;
    }

    public void setUser_info_audit(int user_info_audit) {
        this.user_info_audit = user_info_audit;
    }

    public int getUser_phone_service_audit() {
        return user_phone_service_audit;
    }

    public void setUser_phone_service_audit(int user_phone_service_audit) {
        this.user_phone_service_audit = user_phone_service_audit;
    }

    public int getUser_tb_audit() {
        return user_tb_audit;
    }

    public void setUser_tb_audit(int user_tb_audit) {
        this.user_tb_audit = user_tb_audit;
    }

    public int getUser_zfb_audit() {
        return user_zfb_audit;
    }

    public void setUser_zfb_audit(int user_zfb_audit) {
        this.user_zfb_audit = user_zfb_audit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_bank_audit);
        dest.writeInt(this.user_book_phone_audit);
        dest.writeInt(this.user_card_audit);
        dest.writeInt(this.user_info_audit);
        dest.writeInt(this.user_phone_service_audit);
        dest.writeInt(this.user_tb_audit);
        dest.writeInt(this.user_zfb_audit);
    }

    public CheckStatus() {
    }

    protected CheckStatus(Parcel in) {
        this.user_bank_audit = in.readInt();
        this.user_book_phone_audit = in.readInt();
        this.user_card_audit = in.readInt();
        this.user_info_audit = in.readInt();
        this.user_phone_service_audit = in.readInt();
        this.user_tb_audit = in.readInt();
        this.user_zfb_audit = in.readInt();
    }

    public static final Parcelable.Creator<CheckStatus> CREATOR = new Parcelable.Creator<CheckStatus>() {
        @Override
        public CheckStatus createFromParcel(Parcel source) {
            return new CheckStatus(source);
        }

        @Override
        public CheckStatus[] newArray(int size) {
            return new CheckStatus[size];
        }
    };
}
