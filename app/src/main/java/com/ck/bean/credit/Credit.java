package com.ck.bean.credit;

/**
 * Created by cnbs5 on 2017/7/19.
 */

public class Credit {


    /**
     * loanMoney : 1000
     * loanState : 1
     * stateDec :
     * title : 千元贷款极速到账（秒过）
     * typeId : 6
     * valid : 1
     */

    private String loanMoney;//贷款金额
    private int loanState;//当前贷款状态
    private String stateDec;//状态描述
    private String title;//标题
    private int typeId;//类型ID
    private int valid;//是否开通贷款

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public int getLoanState() {
        return loanState;
    }

    public void setLoanState(int loanState) {
        this.loanState = loanState;
    }

    public String getStateDec() {
        return stateDec;
    }

    public void setStateDec(String stateDec) {
        this.stateDec = stateDec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
