package com.ck.qianqian.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.CreditHistoryAdapter;
import com.ck.bean.CreditHistory;
import com.ck.listener.MyItemClickListener;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.IndexActivity;
import com.ck.qianqian.MainActivity;
import com.ck.qianqian.R;
import com.ck.util.MyApplication;
import com.ck.widget.DividerItemDecoration;
import com.ck.widget.LoadingDialog;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CreditHistoryActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.recyclerView)
    SuperRecyclerView recyclerView;

    private List<CreditHistory> data;
    private CreditHistoryAdapter adapter;

    private Boolean needClear = true;
    private Boolean isEnd = false;
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_history);
        ButterKnife.bind(this);
        titleName.setText("借贷记录");
        setViews();
        getData();
    }

    private void setViews() {
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.text_blue);
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                if (!isEnd) {
                    needClear = false;
                    getData();
                } else {
                    recyclerView.hideMoreProgress();
                }
            }
        }, 1);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                needClear = true;
                getData();
            }
        });
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        if (needClear) {
            map.put("pageNo", 1);
        } else {
            map.put("pageNo", pageNo);
        }
        Subscriber subscriber = new Subscriber<HttpResult.CreditHistoryResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.CreditHistoryResponse response) {
                if (response.code == 0) {
                    if (needClear) {
                        data.clear();
                        isEnd = false;
                        pageNo = 1;
                    }
                    data.addAll(response.list);
                    pageNo++;
                } else {
                    if (needClear) {
                        data.clear();
                    }
                    isEnd = true;
                }
                recyclerView.hideMoreProgress();
                if (needClear) {
                    if (adapter == null) {
                        setAdapter();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    setAdapter();
                }
            }
        };
        HttpMethods.getInstance().getCreditHistory(subscriber, map);
    }

    private int pos;

    private void setAdapter() {
        adapter = new CreditHistoryAdapter(data, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                pos = recyclerView.getRecyclerView().getChildAdapterPosition(view);
//                getOrderDetail(data.get(pos).getId());

                //
                getRecordDetail(data.get(pos).getRecordId());
//                Intent intent = new Intent(CreditHistoryActivity.this, CreditDetailActivity.class);
//                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private LoadingDialog dialog;

    private void getRecordDetail(int recordId) {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
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
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.GetDetailByRecordIdResponse response) {
                int code = response.code;
                if (code == 0) {
                    Intent intent = new Intent(CreditHistoryActivity.this, CreditDetailActivity.class);
//                    if (data.get(pos).getLoanState() == 1) {
//                        intent.putExtra("code", 99);
//                        intent.putExtra("fromHistory",true);
//                    }
                    intent.putExtra("creditDetail", response.obj);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getDetailByRecordId(subscriber, map);
    }
}
