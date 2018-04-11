package com.ck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ck.fragment.PayLaterFragment;
import com.ck.fragment.PayNowFragment;

/**
 * Created by cnbs5 on 2017/7/21.
 */

public class PayAdapter extends FragmentPagerAdapter {

    private PayNowFragment payNowFragment;
    private PayLaterFragment payLaterFragment;

    public PayAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (payNowFragment == null) {
                    payNowFragment = PayNowFragment.newInstance();
                    return payNowFragment;
                } else {
                    return payNowFragment;
                }
            case 1:
                if (payLaterFragment == null) {
                    payLaterFragment = PayLaterFragment.newInstance();
                    return payLaterFragment;
                } else {
                    return payLaterFragment;
                }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

//    public PayLaterFragment getFragment(){
//        return  payLaterFragment;
//    }
}
