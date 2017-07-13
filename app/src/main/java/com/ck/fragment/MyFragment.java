package com.ck.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ck.qianqian.CheckCenterActivity;
import com.ck.qianqian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    @BindView(R.id.titleName)
    TextView titleName;

    Unbinder unbinder;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.check_rel)
    RelativeLayout checkRel;
    @BindView(R.id.credit_rel)
    RelativeLayout creditRel;
    @BindView(R.id.feedback_rel)
    RelativeLayout feedbackRel;
    @BindView(R.id.setting_rel)
    RelativeLayout settingRel;
    @BindView(R.id.logout_rel)
    RelativeLayout logoutRel;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleName.setText("个人中心");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.check_rel, R.id.credit_rel, R.id.feedback_rel, R.id.setting_rel, R.id.logout_rel})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.check_rel:
                intent = new Intent(getActivity(), CheckCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.credit_rel:
                break;
            case R.id.feedback_rel:
                break;
            case R.id.setting_rel:
                break;
            case R.id.logout_rel:
                break;
        }
    }
}
