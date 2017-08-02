package com.ck.qianqian.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.GetCreditAmountAdapter;
import com.ck.adapter.GetCreditDayAdapter;
import com.ck.bean.credit.GetCreditAmount;
import com.ck.bean.credit.GetCreditDay;
import com.ck.bean.credit.GetCreditDetail;
import com.ck.listener.MyItemClickListener;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.CheckCenterActivity;
import com.ck.qianqian.CheckContactActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.qianqian.WebViewActivity;
import com.ck.util.MyApplication;
import com.ck.widget.GridSpacingDecoration;
import com.ck.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class GetCreditActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.amountList)
    RecyclerView amountList;
    @BindView(R.id.timeList)
    RecyclerView timeList;
    @BindView(R.id.trialFee)
    TextView trialFee;
    @BindView(R.id.interestFee)
    TextView interestFee;
    @BindView(R.id.accountFee)
    TextView accountFee;
    @BindView(R.id.arrivalFee)
    TextView arrivalFee;
    @BindView(R.id.amountFee)
    TextView amountFee;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.contract)
    TextView contract;
    @BindView(R.id.submit)
    TextView submit;

    private GetCreditAmountAdapter amountAdapter;
    private ArrayList<GetCreditAmount> amounts;
    private GridLayoutManager gm, gm2;

    private GetCreditDayAdapter dayAdapter;
    private ArrayList<GetCreditDay> days;

    private int typeId;
    private Intent intent;
    private LoadingDialog dialog;

    private int amountPos = -1;
    private int dayPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_credit);
        ButterKnife.bind(this);
        titleName.setText("选择借款");
        setViews();
        getDays();
    }

    private void setViews() {
        intent = getIntent();
        typeId = intent.getIntExtra("typeId", 0);
        amounts = intent.getParcelableArrayListExtra("amounts");
        amountAdapter = new GetCreditAmountAdapter(amounts, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int pos = amountList.getChildAdapterPosition(view);
                amountAdapter.changeSelect(pos);

                amountPos = pos;
                if (amountPos >= 0 && dayPos >= 0) {
                    getData();
                }
            }
        });
        amountList.setAdapter(amountAdapter);
        amountList.setHasFixedSize(true);
        gm = new GridLayoutManager(getApplicationContext(), 2);
        amountList.setLayoutManager(gm);
        amountList.addItemDecoration(new GridSpacingDecoration(4, 32, false));

        days = new ArrayList<>();
        dayAdapter = new GetCreditDayAdapter(days, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int pos = timeList.getChildAdapterPosition(view);
                dayAdapter.changeSelect(pos);

                dayPos = pos;
                if (amountPos >= 0 && dayPos >= 0) {
                    getData();
                }
            }
        });
        timeList.setAdapter(dayAdapter);
        timeList.setHasFixedSize(true);
        gm2 = new GridLayoutManager(getApplicationContext(), 2);
        timeList.setLayoutManager(gm2);
        timeList.addItemDecoration(new GridSpacingDecoration(4, 32, false));

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    icon.setImageResource(R.mipmap.check_contact_sure2);
                    submit.setClickable(false);
                    submit.setBackgroundResource(R.drawable.shape_gray_round);
                } else {
                    icon.setImageResource(R.mipmap.check_contact_sure);
                    submit.setClickable(false);
                    submit.setBackgroundResource(R.drawable.login_btn);
                }
                isEnable = !isEnable;
            }
        });
    }

    private Boolean isEnable = true;

    private void getDays() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
//        map.put("type", typeId);
        map.put("type", "1");
        Subscriber subscriber = new Subscriber<HttpResult.GetCreditDayResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(GetCreditActivity.this, R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.GetCreditDayResponse response) {
                if (response.code == 0) {
                    days.addAll(response.list);
                    dayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(GetCreditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getCreditDayList(subscriber, map);
    }

    private void getData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("amountId", amounts.get(amountPos).getId());
        map.put("dayId", days.get(dayPos).getId());
        Subscriber subscriber = new Subscriber<HttpResult.GetCreditDetailResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(GetCreditActivity.this, R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.GetCreditDetailResponse response) {
                if (response.code == 0) {
                    GetCreditDetail getCreditDetail = response.obj;
                    trialFee.setText("快速信审费：" + getCreditDetail.getTrialFee() + "元");
                    interestFee.setText("利息：" + getCreditDetail.getInterestFee() + "元");
                    accountFee.setText("账户管理费：" + getCreditDetail.getAccountFee() + "元");
                    arrivalFee.setText(getCreditDetail.getArrivalFee() + "元");
                    amountFee.setText(getCreditDetail.getAmountFee() + "元");
                } else {
                    Toast.makeText(GetCreditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getCreditDetail(subscriber, map);
    }

    private void addCredit() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("amountId", amounts.get(amountPos).getId());
        map.put("dayId", days.get(dayPos).getId());
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
//                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(GetCreditActivity.this, R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(GetCreditActivity.this, response.msg, Toast.LENGTH_LONG).show();
                if (response.code == 0) {
                    dialog.cancel();
                    getHomeState();
                } else if (response.code == -5) {
//                    Toast.makeText(getApplicationContext(),"请完成认证",Toast.LENGTH_SHORT).show();
                    getStatus();
                } else {
                    dialog.cancel();
                }
            }
        };
        HttpMethods.getInstance().addCredit(subscriber, map);
    }

    private void getHomeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.IndexResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onNext(HttpResult.IndexResponse response) {
                int code = response.code;
                if (code == 1 || code == 8) {
                    Intent intent = new Intent(GetCreditActivity.this, MainActivity.class);
                    intent.putExtra("state", 2);//
                    intent.putExtra("code", code);
                    intent.putExtra("creditDetail", response.obj);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (code == 3) {
                    Intent intent = new Intent(GetCreditActivity.this, MainActivity.class);
                    intent.putExtra("state", 1);//还款
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GetCreditActivity.this, MainActivity.class);
                    intent.putExtra("state", 0);//借款
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }

    private void getStatus() {
//        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
//        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.CheckResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(GetCreditActivity.this, R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.CheckResponse response) {
                if (response.code == 0) {
                    Intent intent = new Intent(GetCreditActivity.this, CheckCenterActivity.class);
                    intent.putExtra("status", response.obj);
                    startActivity(intent);
                } else {
                    Toast.makeText(GetCreditActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().checkStatus(subscriber, map);
    }


    private Boolean checkValue() {
        if (amountPos < 0) {
            Toast.makeText(getApplicationContext(), "请选择金额", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayPos < 0) {
            Toast.makeText(getApplicationContext(), "请选择天数", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @OnClick({R.id.contract, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contract:
                Intent intent = new Intent(GetCreditActivity.this, WebViewActivity.class);
                intent.putExtra("title", "借款服务合同");
                intent.putExtra("url", HttpMethods.BASE_URL + "service/service.html?key=LOAN_SERVICE" + "&loginName=" + MyApplication.getInstance().getUserName());
                startActivity(intent);
                break;
            case R.id.submit:
                if (checkValue()) {
                    addCredit();
                }
                break;
        }
    }
}
