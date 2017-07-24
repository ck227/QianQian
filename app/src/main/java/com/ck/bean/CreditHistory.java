package com.ck.bean;

/**
 * Created by cnbs5 on 2017/7/14.
 */

public class CreditHistory {


    /**
     * amountMoney : 1000
     * day : 7
     * loanNumber : LZX20170723030538234
     * loanState : 3
     * loanTypeName : 千元贷款极速到账（秒过）
     * recordId : 7
     * repaymentTime : 2017-07-30
     */

    private String amountMoney;
    private int day;
    private String loanNumber;
    private int loanState;
    private String loanTypeName;
    private int recordId;
    private String repaymentTime;

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public int getLoanState() {
        return loanState;
    }

    public void setLoanState(int loanState) {
        this.loanState = loanState;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }
}
