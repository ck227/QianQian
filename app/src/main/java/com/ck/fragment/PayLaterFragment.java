package com.ck.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.GetCreditDayAdapter;
import com.ck.bean.PayLater;
import com.ck.bean.credit.GetCreditDay;
import com.ck.bean.credit.GetCreditDetail;
import com.ck.listener.MyItemClickListener;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.IndexActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.util.MyApplication;
import com.ck.widget.GridSpacingDecoration;
import com.ck.widget.LoadingDialog;
import com.ck.widget.PayNowDialog;

import java.util.ArrayList;
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
public class PayLaterFragment extends Fragment {


    @BindView(R.id.timeList)
    RecyclerView timeList;
    Unbinder unbinder;
    @BindView(R.id.trialFee)
    TextView trialFee;
    @BindView(R.id.interestFee)
    TextView interestFee;
    @BindView(R.id.accountFee)
    TextView accountFee;
    @BindView(R.id.arrivalFee)
    TextView arrivalFee;
    @BindView(R.id.serviceFee)
    TextView serviceFee;

    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.dayNumber)
    TextView dayNumber;
    @BindView(R.id.applyRenewalTime)
    TextView applyRenewalTime;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.trialFee2)
    TextView trialFee2;

    @BindView(R.id.interestFee2)
    TextView interestFee2;
    @BindView(R.id.accountFee2)
    TextView accountFee2;
    @BindView(R.id.amountFee)
    TextView amountFee;
    @BindView(R.id.serviceFee2)
    TextView serviceFee2;
    @BindView(R.id.overdueSpot)
    TextView overdueSpot;

    @BindView(R.id.renewalNumber)
    TextView renewalNumber;

    @BindView(R.id.renewalFee)
    TextView renewalFee;

    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.cancel)
    TextView cancel;

    private GetCreditDayAdapter dayAdapter;
    private ArrayList<GetCreditDay> days;
    private int dayPos = -1;
    private GridLayoutManager gm;

    private LoadingDialog dialog;

    GetCreditDetail getCreditDetail;

    private int recordId;

    public PayLaterFragment() {
        // Required empty public constructor
    }

    public static PayLaterFragment newInstance() {

        Bundle args = new Bundle();

        PayLaterFragment fragment = new PayLaterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_later, container, false);
        unbinder = ButterKnife.bind(this, view);
        setViews();
        getDays();
        getState();

        swipeRefreshLayout.setColorSchemeResources(R.color.text_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeState();
            }
        });
        return view;
    }

    private void getHomeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.IndexResponse>() {
            @Override
            public void onCompleted() {
                swipeRefreshLayout.setRefreshing(false);
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
                    intent.putExtra("state", 2);
                    intent.putExtra("code", code);
                    intent.putExtra("creditDetail", response.obj);
                } else if (code == 3) {
                    //状态没有变化，不用动

                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("state", 0);//借款
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }

    private void setViews() {
        days = new ArrayList<>();
        dayAdapter = new GetCreditDayAdapter(days, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int pos = timeList.getChildAdapterPosition(view);
                dayAdapter.changeSelect(pos);

                dayPos = pos;
                getData();
            }
        });
        timeList.setAdapter(dayAdapter);
        timeList.setHasFixedSize(true);
        gm = new GridLayoutManager(getActivity(), 2);
        timeList.setLayoutManager(gm);
        timeList.addItemDecoration(new GridSpacingDecoration(4, 32, false));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPayLater();
            }
        });
    }

    private void getDays() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "2");
        Subscriber subscriber = new Subscriber<HttpResult.GetCreditDayResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.GetCreditDayResponse response) {
                if (response.code == 0) {
                    days.addAll(response.list);
                    dayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getCreditDayList(subscriber, map);
    }

    private void getState() {
//        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
//        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.PayLaterState>() {
            @Override
            public void onCompleted() {
//                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
//                dialog.cancel();
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.PayLaterState response) {
                if (response.code == 0) {
                    if (response.obj == null) {
                        //什么事都不用做，可以正常续期
                    } else {
                        //
                        submit.setEnabled(false);
                        submit.setBackgroundResource(R.drawable.shape_gray_round);
                        cardView.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);

                        //下面的内容显示出来
                        PayLater payLater = response.obj;
                        recordId = payLater.getId();
                        dayNumber.setText("续期天数：" + payLater.getDayNumber() + "天");
                        applyRenewalTime.setText("续期申请时间：" + payLater.getApplyRenewalTime());
                        state.setText("续期状态：" + "申请中");
                        trialFee2.setText("快速信审费：" + payLater.getTrialFee() + "元");


                        interestFee2.setText("利息：" + payLater.getInterestFee() + "元");
                        accountFee2.setText("账户管理费：" + payLater.getAccountFee() + "元");
                        amountFee.setText("贷款金额：" + payLater.getAmountFee() + "元");
                        serviceFee2.setText("服务费：" + payLater.getServiceFee() + "元");

                        renewalFee.setText("续期金额：" + payLater.getRenewalFee() + "元");

                        renewalNumber.setText(payLater.getRenewalNumber() + "期");
                    }
                } else {
//                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getPayLaterState(subscriber, map);
    }

    private void getData() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("dayId", days.get(dayPos).getId());
        map.put("recordId", ((PayFragment) getParentFragment()).getReordId());

        Subscriber subscriber = new Subscriber<HttpResult.GetCreditDetailResponse>() {
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
            public void onNext(HttpResult.GetCreditDetailResponse response) {
                if (response.code == 0) {
                    getCreditDetail = response.obj;
                    trialFee.setText("快速信审费：" + getCreditDetail.getTrialFee() + "元");
                    interestFee.setText("利息：" + getCreditDetail.getInterestFee() + "元");
                    accountFee.setText("账户管理费：" + getCreditDetail.getAccountFee() + "元");
                    serviceFee.setText("服务费：" + getCreditDetail.getServiceFee() + "元");
                    arrivalFee.setText(getCreditDetail.getArrivalFee() + "元");
                    overdueSpot.setText("逾期费用：" + getCreditDetail.getOverdueSpot() + "元");
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getLaterPayDetail(subscriber, map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (dayPos < 0) {
            Toast.makeText(getActivity(), "请选择天数", Toast.LENGTH_SHORT).show();
            return;
        }
        payLater();
    }

    //请求服务器延期
    private void payLater() {
        showDialog2();
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
        }, getCreditDetail.getArrivalFee());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("请将最终还款金额【" + (Double.valueOf(getCreditDetail.getArrivalFee())+Double.valueOf(getCreditDetail.getOverdueSpot())) + "元】转入还款专用指定支付宝账号，转账请备注本APP 登录账号，如已成功转账还款则点击'已转账'按钮即可提交还款申请，后台进行自动审核" +
                "\n" +
                "收款人：李佳" + "\n" +
                "支付宝账号：13628691614");
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
        map.put("recordId", ((PayFragment) getParentFragment()).getReordId());
        map.put("payType", 3);
        map.put("dayId", days.get(dayPos).getId());
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {
                if (response.code > 0) {
//                    payLaterCallback(response.code);
                    getState();
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
//
            }
        };
        HttpMethods.getInstance().payOnlineLater(subscriber, map);
    }


    private void payLaterCallback(int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("renewalId", code);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    ((PayFragment) getParentFragment()).setContent(0);
                    ((PayNowFragment) ((PayFragment) getParentFragment()).getPayAdapter().getItem(0)).getData();
                } else {

                }
            }
        };
        HttpMethods.getInstance().payLaterCallback(subscriber, map);
    }

    private void cancelPayLater() {
        dialog = new LoadingDialog(getActivity(), R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("id", recordId);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {

                if (response.code == 0) {
                    cardView.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);

                    submit.setEnabled(true);
                    submit.setBackgroundResource(R.drawable.login_btn);
                } else {
                    Toast.makeText(getActivity(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().cancelPayLater(subscriber, map);
    }
}
