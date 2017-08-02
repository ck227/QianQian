package com.ck.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ck.bean.credit.CreditDetail;
import com.ck.fragment.CreditStateFragment;
import com.ck.fragment.HomeFragment;
import com.ck.fragment.MyFragment;
import com.ck.fragment.PayFragment;
import com.ck.qianqian.R;

/**
 * Created by cnbs5 on 2017/7/13.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private Context context;
    private String tabTitles[];
    private int[] imageResId;

    private HomeFragment homeFragment;
    private PayFragment payFragment;
    private CreditStateFragment creditStateFragment;
    private MyFragment myFragment;

    private int state;

    private int code;
    private CreditDetail creditDetail;


    public MainAdapter(FragmentManager fm, Context context, int state) {
        super(fm);
        this.context = context;
        this.state = state;
        tabTitles = new String[]{"首页", "个人中心"};
        imageResId = new int[]{R.drawable.main_menu0_selector, R.drawable.main_menu1_selector};
    }

    public MainAdapter(FragmentManager fm, Context context, int state, int code, CreditDetail creditDetail) {
        super(fm);
        this.context = context;
        this.state = state;
        this.code = code;
        this.creditDetail = creditDetail;
        tabTitles = new String[]{"首页", "个人中心"};
        imageResId = new int[]{R.drawable.main_menu0_selector, R.drawable.main_menu1_selector};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (state == 1) {
                    if (payFragment == null) {
                        payFragment = PayFragment.newInstance();
                    }
                    return payFragment;
                } else if (state == 0) {
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance();
                    }
                    return homeFragment;
                } else {
                    if (creditStateFragment == null) {
                        creditStateFragment = CreditStateFragment.newInstance(code, creditDetail);
                    }
                    return creditStateFragment;
                }
            case 1:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }
                return myFragment;
        }
        return null;
    }

    public void refreshData() {
        creditStateFragment.refresh();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        img.setImageResource(imageResId[position]);
        return v;
    }
}
