package com.ck.bean;

/**
 * Created by chendaye on 2017/7/27.
 */

public class PayLater {

    private int id;

    private int dayNumber;
    /**
     * 续期申请时间
     */
    private String applyRenewalTime;
    /**
     * 续期状态
     */
    private int state;

    private String trialFee;//快速信审费

    private String interestFee;//利息

    private String accountFee;//账户管理费

    private String amountFee;//贷款金额

    private String serviceFee;//服务费

    private String renewalNumber;//续期期数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getApplyRenewalTime() {
        return applyRenewalTime;
    }

    public void setApplyRenewalTime(String applyRenewalTime) {
        this.applyRenewalTime = applyRenewalTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTrialFee() {
        return trialFee;
    }

    public void setTrialFee(String trialFee) {
        this.trialFee = trialFee;
    }

    public String getInterestFee() {
        return interestFee;
    }

    public void setInterestFee(String interestFee) {
        this.interestFee = interestFee;
    }

    public String getAccountFee() {
        return accountFee;
    }

    public void setAccountFee(String accountFee) {
        this.accountFee = accountFee;
    }

    public String getAmountFee() {
        return amountFee;
    }

    public void setAmountFee(String amountFee) {
        this.amountFee = amountFee;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getRenewalNumber() {
        return renewalNumber;
    }

    public void setRenewalNumber(String renewalNumber) {
        this.renewalNumber = renewalNumber;
    }
}
