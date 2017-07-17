package com.ck.bean;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class Contact {

    private String bookName;

    private String bookPhone;

    public Contact(String bookName, String bookPhone) {
        this.bookName = bookName;
        this.bookPhone = bookPhone;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPhone() {
        return bookPhone;
    }

    public void setBookPhone(String bookPhone) {
        this.bookPhone = bookPhone;
    }
}
