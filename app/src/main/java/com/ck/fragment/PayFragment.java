package com.ck.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ck.adapter.PayAdapter;
import com.ck.qianqian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.service)
    TextView service;
    @BindView(R.id.payNow)
    TextView payNow;
    @BindView(R.id.payLater)
    TextView payLater;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private PayAdapter payAdapter;

    public PayFragment() {
        // Required empty public constructor
    }

    public static PayFragment newInstance() {

        Bundle args = new Bundle();

        PayFragment fragment = new PayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        unbinder = ButterKnife.bind(this, view);

        payAdapter = new PayAdapter(getChildFragmentManager());
        viewPager.setAdapter(payAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    payNow.setTextColor(getResources().getColor(R.color.white));
                    payNow.setBackgroundResource(R.drawable.pay_shape_left_blue);
                    payLater.setTextColor(getResources().getColor(R.color.text_blue));
                    payLater.setBackgroundResource(R.drawable.pay_shape_right_white);
                } else {
                    payNow.setTextColor(getResources().getColor(R.color.text_blue));
                    payNow.setBackgroundResource(R.drawable.pay_shape_left_white);
                    payLater.setTextColor(getResources().getColor(R.color.white));
                    payLater.setBackgroundResource(R.drawable.pay_shape_right_blue);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.service, R.id.payNow, R.id.payLater})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.service:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:15717174872"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.payNow:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.payLater:
                viewPager.setCurrentItem(1, true);
                break;
        }
    }

    public int getReordId(){
        return ((PayNowFragment)payAdapter.getItem(0)).getRecordId();
    }

    public PayAdapter getPayAdapter(){
        return payAdapter;
    }
}
