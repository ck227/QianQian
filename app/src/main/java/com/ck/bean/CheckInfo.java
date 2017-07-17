package com.ck.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class CheckInfo implements Parcelable {

    private ArrayList<InfoType> listContacts;
    private ArrayList<InfoType> listEducation;
    private ArrayList<InfoType> listIncome;
    private ArrayList<InfoType> listMarriage;

    public ArrayList<InfoType> getListContacts() {
        return listContacts;
    }

    public void setListContacts(ArrayList<InfoType> listContacts) {
        this.listContacts = listContacts;
    }

    public ArrayList<InfoType> getListEducation() {
        return listEducation;
    }

    public void setListEducation(ArrayList<InfoType> listEducation) {
        this.listEducation = listEducation;
    }

    public ArrayList<InfoType> getListIncome() {
        return listIncome;
    }

    public void setListIncome(ArrayList<InfoType> listIncome) {
        this.listIncome = listIncome;
    }

    public ArrayList<InfoType> getListMarriage() {
        return listMarriage;
    }

    public void setListMarriage(ArrayList<InfoType> listMarriage) {
        this.listMarriage = listMarriage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listContacts);
        dest.writeTypedList(this.listEducation);
        dest.writeTypedList(this.listIncome);
        dest.writeTypedList(this.listMarriage);
    }

    public CheckInfo() {
    }

    protected CheckInfo(Parcel in) {
        this.listContacts = in.createTypedArrayList(InfoType.CREATOR);
        this.listEducation = in.createTypedArrayList(InfoType.CREATOR);
        this.listIncome = in.createTypedArrayList(InfoType.CREATOR);
        this.listMarriage = in.createTypedArrayList(InfoType.CREATOR);
    }

    public static final Parcelable.Creator<CheckInfo> CREATOR = new Parcelable.Creator<CheckInfo>() {
        @Override
        public CheckInfo createFromParcel(Parcel source) {
            return new CheckInfo(source);
        }

        @Override
        public CheckInfo[] newArray(int size) {
            return new CheckInfo[size];
        }
    };
}
