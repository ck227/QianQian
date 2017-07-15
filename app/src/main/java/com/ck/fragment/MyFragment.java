package com.ck.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ck.qianqian.CheckCenterActivity;
import com.ck.qianqian.FeedbackActivity;
import com.ck.qianqian.R;
import com.ck.qianqian.UpdatePwdActivity;
import com.ck.qianqian.credit.CreditHistoryActivity;

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
                intent = new Intent(getActivity(), CreditHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback_rel:
                intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rel:
                intent = new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_rel:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("是否确认退出?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();//将dialog显示出来
    }
}
