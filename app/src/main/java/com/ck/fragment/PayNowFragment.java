package com.ck.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.credit.PayNow;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.IndexActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.qianqian.credit.CreditDetailActivity;
import com.ck.qianqian.credit.CreditHistoryActivity;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;
import com.ck.widget.PayNowDialog;

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

    private int recordId;

    private PayNow payNow;

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

    public void getData() {
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
                    payNow = response.obj;
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

    @OnClick({R.id.money, R.id.pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.money:
                getRecordDetail();
                break;
            case R.id.pay:
                showDialog();
                break;
        }
    }

    private LoadingDialog dialog;

    private void getRecordDetail() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", recordId);
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
                    intent.putExtra("creditDetail", response.obj);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }

            }
        };
        HttpMethods.getInstance().getDetailByRecordId(subscriber, map);
    }

    private void showDialog() {
        final PayNowDialog dialog = new PayNowDialog(getActivity(), new PayNowDialog.ButtonListener() {
            @Override
            public void submit(int selectType) {
                if (selectType == 0) {
                    Toast.makeText(getContext(), "支付宝暂未开通", Toast.LENGTH_SHORT).show();
                } else if (selectType == 1) {
                    //请求服务器
                    showDialog2();
                } else {
                    Toast.makeText(getContext(), "微信暂未开通", Toast.LENGTH_SHORT).show();
                }
            }
        }, payNow.getRepaymentMoney());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("请将最终还款金额【" + payNow.getLoanAmount() + "元】转入还款专用指定支付宝账号，转账请备注本APP 登录账号，如已成功转账还款则点击'已转账'按钮即可提交还款申请，后台进行自动审核" +
                "\n" +
                "收款人：李华" + "\n" +
                "支付宝账号：15717174872");
        builder.setPositiveButton("已转账", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                payOnline();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private void payOnline() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("recordId", recordId);
        map.put("payType", 3);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
//                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                if(response.code > 0){
                    getHomeState();
                }else{
                    dialog.cancel();
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }

            }
        };
        HttpMethods.getInstance().payOnline(subscriber, map);
    }

    public int getRecordId() {
        return recordId;
    }

    private void getHomeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.IndexResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.IndexResponse response) {
                int code = response.code;
                if (code == 1 || code == 8) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("state", 2);//
                    intent.putExtra("code", code);
                    intent.putExtra("creditDetail", response.obj);
                    startActivity(intent);
                } else if (code == 3) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("state", 1);//还款
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("state", 0);//借款
                    startActivity(intent);
                }
                getActivity().finish();
            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }

}
