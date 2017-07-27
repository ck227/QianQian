package com.ck.bean.credit;

/**
 * Created by cnbs5 on 2017/7/20.
 */

public class GetCreditDetail {


    /**
     * accountFee : 22
     * amountFee : 1000
     * arrivalFee : 950
     * interestFee : 2
     * trialFee : 26
     */

    private String accountFee;
    private String amountFee;
    private String arrivalFee;
    private String interestFee;
    private String trialFee;
    private String serviceFee;

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

    public String getArrivalFee() {
        return arrivalFee;
    }

    public void setArrivalFee(String arrivalFee) {
        this.arrivalFee = arrivalFee;
    }

    public String getInterestFee() {
        return interestFee;
    }

    public void setInterestFee(String interestFee) {
        this.interestFee = interestFee;
    }

    public String getTrialFee() {
        return trialFee;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public void setTrialFee(String trialFee) {
        this.trialFee = trialFee;
    }
}
