package com.ck.bean;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class CheckPhone {


    /**
     * phone : 15717174871
     * phonePperator : 湖北武汉移动
     * user_phone_service_audit : 0
     */

    private String phone;
    private String phonePperator;
    private int user_phone_service_audit;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonePperator() {
        return phonePperator;
    }

    public void setPhonePperator(String phonePperator) {
        this.phonePperator = phonePperator;
    }

    public int getUser_phone_service_audit() {
        return user_phone_service_audit;
    }

    public void setUser_phone_service_audit(int user_phone_service_audit) {
        this.user_phone_service_audit = user_phone_service_audit;
    }
}
