package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ck.adapter.GuideAdapter;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by chendaye on 2017/8/1.
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> views;
    private GuideAdapter adapter;
    //    private ImageView[] dots;
    private int currentIndex;
    private static final int[] pics = {R.layout.guide_one, R.layout.guide_two, R.layout.guide_three};

    private ImageView dot1, dot2, dot3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < pics.length; i++) {
            views.add(inflater.inflate(pics[i], null));
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new GuideAdapter(views);
        viewPager.setAdapter(adapter);
        views.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().getUserName().length() > 0) {
                    Intent i = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(GuideActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        dot3 = (ImageView) findViewById(R.id.dot3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    dot1.setImageResource(R.drawable.shape_dot_on);
                    dot2.setImageResource(R.drawable.shape_dot_off);
                    dot3.setImageResource(R.drawable.shape_dot_off);
                } else if (position == 1) {
                    dot1.setImageResource(R.drawable.shape_dot_off);
                    dot2.setImageResource(R.drawable.shape_dot_on);
                    dot3.setImageResource(R.drawable.shape_dot_off);
                } else {
                    dot1.setImageResource(R.drawable.shape_dot_off);
                    dot2.setImageResource(R.drawable.shape_dot_off);
                    dot3.setImageResource(R.drawable.shape_dot_on);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        initBottomDots();
    }

//    private void initBottomDots() {
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
//        dots = new ImageView[pics.length];
//        for (int i = 0; i < pics.length; i++) {
//            dots[i] = (ImageView) linearLayout.getChildAt(i);
//        }
//        currentIndex = 0;
//        dots[currentIndex].setEnabled(false);
//    }

}
