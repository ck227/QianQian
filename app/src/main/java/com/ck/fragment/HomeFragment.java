package com.ck.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.CreditListAdapter;
import com.ck.bean.credit.Credit;
import com.ck.listener.MyItemClickListener;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.qianqian.R;
import com.ck.util.MyApplication;
import com.ck.widget.DividerItemDecoration;
import com.ck.widget.LoadingDialog;
import com.ck.widget.RecycleViewDivider;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.titleName)
    TextView titleName;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    SuperRecyclerView recyclerView;

    private Boolean needClear = true;
    private Boolean isEnd = false;
    private List<Credit> data;
    private CreditListAdapter adapter;

    private LoadingDialog dialog;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleName.setText("借贷中心");
        setViews();
        getData();
        return view;
    }

    private void setViews() {
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),DividerItemDecoration.VERTICAL_LIST,36,android.R.color.transparent));
        recyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.text_blue);
//        recyclerView.setupMoreListener(new OnMoreListener() {
//            @Override
//            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
//                if (!isEnd) {
//                    needClear = false;
//                    getData();
//                } else {
//                    recyclerView.hideMoreProgress();
//                }
//            }
//        }, 1);
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
        Subscriber subscriber = new Subscriber<HttpResult.CreditListResponse>() {
            @Override
            public void onCompleted() {//这里要改成空值的界面

            }

            @Override
            public void onError(Throwable e) {//这里要改成空值的界面
                Toast.makeText(getActivity(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.CreditListResponse response) {
                if (response.code == 0) {
                    if (needClear) {
                        data.clear();
                        isEnd = false;
                    }
                    data.addAll(response.list);
                } else {
                    if (needClear) {
                        data.clear();
                    }
                    isEnd = true;
                }
//                recyclerView.hideMoreProgress();
//                if (needClear) {
//                    setAdapter();
//                } else {
//                    adapter.notifyDataSetChanged();
//                }
                setAdapter();
            }
        };
        HttpMethods.getInstance().getCreditList(subscriber, map);
    }

    private void setAdapter() {
        adapter = new CreditListAdapter(data, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
