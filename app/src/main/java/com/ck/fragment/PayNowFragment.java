package com.ck.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.credit.PayNow;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.LoginActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayNowFragment extends Fragment {


    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.pay)
    TextView pay;
    Unbinder unbinder;

    private LoadingDialog dialog;

    private int recordId;

    public PayNowFragment() {
        // Required empty public constructor
    }

    public static PayNowFragment newInstance() {

        Bundle args = new Bundle();

        PayNowFragment fragment = new PayNowFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_now, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.GetPayDetailResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.GetPayDetailResponse response) {
                if (response.code == 0) {
                    PayNow payNow = response.obj;
                    day.setText(payNow.getRepaymentDay() + "天");
                    time.setText(payNow.getRepaymentTime());
                    money.setText(payNow.getRepaymentMoney() + "元");

                    recordId = payNow.getRecordId();
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getPayDetail(subscriber, map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.pay)
    public void onViewClicked() {
    }

    public int getRecordId() {
        return recordId;
    }
}
