package com.ck.qianqian.credit;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ck.adapter.CreditHistoryAdapter;
import com.ck.bean.CreditHistory;
import com.ck.listener.MyItemClickListener;
import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.R;
import com.ck.widget.DividerItemDecoration;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreditHistoryActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.recyclerView)
    SuperRecyclerView recyclerView;

    private List<CreditHistory> data;
    private CreditHistoryAdapter adapter;

    private Boolean needClear = true;
    private Boolean isEnd = false;

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
        setAdapter();
    }

    private void setAdapter() {
        adapter = new CreditHistoryAdapter(data, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
//                int pos = recyclerView.getRecyclerView().getChildAdapterPosition(view);
//                getOrderDetail(data.get(pos).getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
