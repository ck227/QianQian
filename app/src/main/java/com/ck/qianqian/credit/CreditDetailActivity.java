package com.ck.qianqian.credit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.CreditHistoryAdapter;
import com.ck.bean.credit.CreditDetail;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.IndexActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.qianqian.WebViewActivity;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CreditDetailActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.loanTypeName)
    TextView loanTypeName;
    @BindView(R.id.applyRecordTime)
    TextView applyRecordTime;
    @BindView(R.id.loanNumber)
    TextView loanNumber;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.dayNumber)
    TextView dayNumber;
    @BindView(R.id.trialFee)
    TextView trialFee;
    @BindView(R.id.accountFee)
    TextView accountFee;
    @BindView(R.id.interestFee)
    TextView interestFee;
    @BindView(R.id.arrivalFee)
    TextView arrivalFee;
    @BindView(R.id.repaymentMoney)
    TextView repaymentMoney;
    @BindView(R.id.playMoneytime)
    TextView playMoneytime;
    @BindView(R.id.repaymentTime)
    TextView repaymentTime;
    @BindView(R.id.actualRepaymentTime)
    TextView actualRepaymentTime;
    @BindView(R.id.loanState)
    TextView loanState;


    private Intent intent;
    private int code;
    private CreditDetail creditDetail;
//    private Boolean fromHistory;

    public void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_detail);
        ButterKnife.bind(this);
        titleName.setText("借款详情");
        intent = getIntent();
        code = intent.getIntExtra("code", 0);
        creditDetail = intent.getParcelableExtra("creditDetail");
//        fromHistory = intent.getBooleanExtra("fromHistory",false);
        setViews();
    }

    private void setViews() {
        if (code == 99) {
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
        }
        loanTypeName.setText(creditDetail.getLoanTypeName());
        applyRecordTime.setText(creditDetail.getApplyRecordTime());
        loanNumber.setText(creditDetail.getLoanNumber());
        amount.setText(creditDetail.getAmount() + "元");
        dayNumber.setText(creditDetail.getDayNumber() + "天");
        trialFee.setText(creditDetail.getTrialFee() + "元");
        accountFee.setText(creditDetail.getAccountFee() + "元");
        interestFee.setText(creditDetail.getInterestFee() + "元");
        arrivalFee.setText(creditDetail.getArrivalFee()+"元");
        repaymentMoney.setText(creditDetail.getRepaymentMoney() + "元");
        playMoneytime.setText(creditDetail.getPlayMoneytime());
        repaymentTime.setText(creditDetail.getRepaymentTime());
        actualRepaymentTime.setText(creditDetail.getActualRepaymentTime());
        String states = creditDetail.getLoanState();
        int state = Integer.valueOf(states);
//        if (state == 1) {
//            loanState.setText("申请打款");
//        } else if (state == 2) {
//            loanState.setText("取消放款");
//        } else if (state == 3) {
//            loanState.setText("已放款");
//        } else if (state == 4) {
//            loanState.setText("已还款");
//        } else if (state == 5) {
//            loanState.setText("已逾期");
//        } else if (state == 6) {
//            loanState.setText("已续期");
//        }

        if(state == 0){
            loanState.setText("无贷款记录 ");
        }else if(state == 1){//0：无贷款记录 1：申请贷款 2：取消放款 3：已放款 4：已还款 5：已逾期 6：已续期
            loanState.setText("申请贷款中");
        }else if(state == 2){
            loanState.setText("取消放款");
        }else if(state == 3){
            loanState.setText("已放款");
        }else if(state == 4){
            loanState.setText("已还款");
        }else if(state == 5){
            loanState.setText("已逾期");
        }else if(state == 6){
            loanState.setText("已续期");
        }else if(state == 7){
            loanState.setText("取消贷款");
        }else if(state == 8){
            loanState.setText("申请还款中");
        }else{
            loanState.setText("未知状态");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreditDetailActivity.this, WebViewActivity.class);
                intent.putExtra("title", "贷款合同");
                intent.putExtra("url",HttpMethods.BASE_URL + "service/service.html?key=CONTRACT_SERVICE&recordId="+creditDetail.getRecordId()+"&loginName="+MyApplication.getInstance().getUserName());
                startActivity(intent);
//                http://localhost:8080/lizhixinInterface/service/service.html?key=LOAN_SERVICE
            }
        });
    }

    private LoadingDialog dialog;

    private void cancel() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("recordId", creditDetail.getRecordId());
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    //成功之后要获取首页数据并跳转到主界面才行
                    getHomeState();
                }
            }
        };
        HttpMethods.getInstance().cancelOrder(subscriber, map);
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
                    Intent intent = new Intent(CreditDetailActivity.this, MainActivity.class);
                    intent.putExtra("state", 2);//
                    intent.putExtra("code", code);
                    intent.putExtra("creditDetail", response.obj);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (code == 3) {
                    Intent intent = new Intent(CreditDetailActivity.this, MainActivity.class);
                    intent.putExtra("state", 1);//还款
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CreditDetailActivity.this, MainActivity.class);
                    intent.putExtra("state", 0);//借款
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                finish();
            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }
}
