package com.ck.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.credit.CreditDetail;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.R;
import com.ck.qianqian.credit.CreditDetailActivity;
import com.ck.qianqian.credit.CreditHistoryActivity;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * Created by chendaye on 2017/7/23.
 */

public class CreditStateFragment extends Fragment {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.credit_text)
    TextView credit_text;
    @BindView(R.id.credit_amount)
    TextView credit_amount;
    @BindView(R.id.credit_state)
    TextView credit_state;
    @BindView(R.id.card)
    CardView cardView;
    Unbinder unbinder;

    CreditDetail creditDetail;
    private int otherCode = 0;

    public static CreditStateFragment newInstance(int code, CreditDetail creditDetail) {

        Bundle args = new Bundle();
        args.putInt("code", code);
        args.putParcelable("creditDetail", creditDetail);
        CreditStateFragment fragment = new CreditStateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_state, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleName.setText("借贷中心");
        Bundle bundle = getArguments();
        otherCode = bundle.getInt("code");
        creditDetail = bundle.getParcelable("creditDetail");

        name.setText("贷款天数：" + creditDetail.getDayNumber());

        credit_text.setText("贷款金额");

        credit_amount.setText(creditDetail.getAmount() + "元");

        if (otherCode == 1) {
            credit_state.setText("申请贷款中");
        } else {
            credit_state.setText("还款中");
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CreditDetailActivity.class);
//                if (code == 1) {
//                    intent.putExtra("code", 99);
//                }
//                intent.putExtra("creditDetail", creditDetail);
//                startActivity(intent);
                getRecordDetail();
            }
        });

        return view;
    }

    private LoadingDialog dialog;

    private void getRecordDetail() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", creditDetail.getRecordId());
        Subscriber subscriber = new Subscriber<HttpResult.GetDetailByRecordIdResponse>() {
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
            public void onNext(HttpResult.GetDetailByRecordIdResponse response) {
                int code = response.code;
                if (code == 0) {
                    Intent intent = new Intent(getActivity(), CreditDetailActivity.class);
                    if (otherCode == 1) {
                        intent.putExtra("code", 99);
                    }
                    intent.putExtra("creditDetail", response.obj);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getDetailByRecordId(subscriber, map);
    }
}
