package com.ck.bean.credit;

/**
 * Created by cnbs5 on 2017/7/21.
 */

public class PayNow {
    /**
     * loanAmount : 1000
     * overdueSpot : 0
     * recordId : 2
     * repaymentDay : 6
     * repaymentMoney : 0
     * repaymentTime : 2017-07-25
     */

    private int loanAmount;
    private int overdueSpot;
    private int recordId;
    private int repaymentDay;
    private String repaymentMoney;
    private String repaymentTime;

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getOverdueSpot() {
        return overdueSpot;
    }

    public void setOverdueSpot(int overdueSpot) {
        this.overdueSpot = overdueSpot;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(int repaymentDay) {
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
}
