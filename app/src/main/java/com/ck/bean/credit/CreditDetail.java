package com.ck.bean.credit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chendaye on 2017/7/23.
 */

public class CreditDetail implements Parcelable {

    /**
     * accountFee : 44
     * actualRepaymentMoney : 0
     * actualRepaymentTime :
     * amount : 2000
     * applyRecordTime : 2017-07-21
     * arrivalFee : 1900
     * dayNumber : 14
     * interesNumber : 0.02
     * interestFee : 4
     * loanNumber : LZX20170721115126600
     * loanState : 3
     * loginName : 18507104251
     * overdueSpot : 0
     * playMoneytime : 2017-07-21
     * recordId : 5
     * repaymentDay : 6
     * repaymentMoney : 2000
     * repaymentTime : 2017-07-28
     * trialFee : 52
     * userName :
     */

    private String loanTypeName;
    private String accountFee;
    private String actualRepaymentMoney;
    private String actualRepaymentTime;
    private String amount;
    private String applyRecordTime;
    private String arrivalFee;
    private String dayNumber;
    private String interesNumber;
    private String interestFee;
    private String loanNumber;
    private String loanState;
    private String loginName;
    private String overdueSpot;
    private String playMoneytime;
    private String recordId;
    private String repaymentDay;
    private String repaymentMoney;
    private String repaymentTime;
    private String trialFee;
    private String userName;

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getAccountFee() {
        return accountFee;
    }

    public void setAccountFee(String accountFee) {
        this.accountFee = accountFee;
    }

    public String getActualRepaymentMoney() {
        return actualRepaymentMoney;
    }

    public void setActualRepaymentMoney(String actualRepaymentMoney) {
        this.actualRepaymentMoney = actualRepaymentMoney;
    }

    public String getActualRepaymentTime() {
        return actualRepaymentTime;
    }

    public void setActualRepaymentTime(String actualRepaymentTime) {
        this.actualRepaymentTime = actualRepaymentTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplyRecordTime() {
        return applyRecordTime;
    }

    public void setApplyRecordTime(String applyRecordTime) {
        this.applyRecordTime = applyRecordTime;
    }

    public String getArrivalFee() {
        return arrivalFee;
    }

    public void setArrivalFee(String arrivalFee) {
        this.arrivalFee = arrivalFee;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getInteresNumber() {
        return interesNumber;
    }

    public void setInteresNumber(String interesNumber) {
        this.interesNumber = interesNumber;
    }

    public String getInterestFee() {
        return interestFee;
    }

    public void setInterestFee(String interestFee) {
        this.interestFee = interestFee;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOverdueSpot() {
        return overdueSpot;
    }

    public void setOverdueSpot(String overdueSpot) {
        this.overdueSpot = overdueSpot;
    }

    public String getPlayMoneytime() {
        return playMoneytime;
    }

    public void setPlayMoneytime(String playMoneytime) {
        this.playMoneytime = playMoneytime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(String repaymentDay) {
        this.repaymentDay = repaymentDay;
    }

    public String getRepaymentMoney() {
        return repaymentMoney;
    }

    public void setRepaymentMoney(String repaymentMoney) {
        this.repaymentMoney = repaymentMoney;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getTrialFee() {
        return trialFee;
    }

    public void setTrialFee(String trialFee) {
        this.trialFee = trialFee;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loanTypeName);
        dest.writeString(this.accountFee);
        dest.writeString(this.actualRepaymentMoney);
        dest.writeString(this.actualRepaymentTime);
        dest.writeString(this.amount);
        dest.writeString(this.applyRecordTime);
        dest.writeString(this.arrivalFee);
        dest.writeString(this.dayNumber);
        dest.writeString(this.interesNumber);
        dest.writeString(this.interestFee);
        dest.writeString(this.loanNumber);
        dest.writeString(this.loanState);
        dest.writeString(this.loginName);
        dest.writeString(this.overdueSpot);
        dest.writeString(this.playMoneytime);
        dest.writeString(this.recordId);
        dest.writeString(this.repaymentDay);
        dest.writeString(this.repaymentMoney);
        dest.writeString(this.repaymentTime);
        dest.writeString(this.trialFee);
        dest.writeString(this.userName);
    }

    public CreditDetail() {
    }

    protected CreditDetail(Parcel in) {
        this.loanTypeName = in.readString();
        this.accountFee = in.readString();
        this.actualRepaymentMoney = in.readString();
        this.actualRepaymentTime = in.readString();
        this.amount = in.readString();
        this.applyRecordTime = in.readString();
        this.arrivalFee = in.readString();
        this.dayNumber = in.readString();
        this.interesNumber = in.readString();
        this.interestFee = in.readString();
        this.loanNumber = in.readString();
        this.loanState = in.readString();
        this.loginName = in.readString();
        this.overdueSpot = in.readString();
        this.playMoneytime = in.readString();
        this.recordId = in.readString();
        this.repaymentDay = in.readString();
        this.repaymentMoney = in.readString();
        this.repaymentTime = in.readString();
        this.trialFee = in.readString();
        this.userName = in.readString();
    }

    public static final Creator<CreditDetail> CREATOR = new Creator<CreditDetail>() {
        @Override
        public CreditDetail createFromParcel(Parcel source) {
            return new CreditDetail(source);
        }

        @Override
        public CreditDetail[] newArray(int size) {
            return new CreditDetail[size];
        }
    };
}
