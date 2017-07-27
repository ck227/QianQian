package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.CityAdapter;
import com.ck.adapter.ProvinceAdapter;
import com.ck.bean.City;
import com.ck.bean.Province;
import com.ck.listener.MyItemClickListener;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.widget.DividerItemDecoration;
import com.ck.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ChooseCityActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.provinceList)
    RecyclerView provinceList;
    @BindView(R.id.cityList)
    RecyclerView cityList;

    private ArrayList<Province> provinces;
    private ProvinceAdapter provinceAdapter;

    private ArrayList<City> cities;
    private CityAdapter cityAdapter;

    private String provinceName;
    private int provincePos = -1;
    private int provinceId = -1;
    private int cityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        ButterKnife.bind(this);
        titleName.setText("开户地址");
        provinces = getIntent().getParcelableArrayListExtra("provinces");
        provinceList.setLayoutManager(new LinearLayoutManager(this));
        provinceList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        provinceAdapter = new ProvinceAdapter(provinces, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                provincePos = provinceList.getChildAdapterPosition(view);
                provinceId = provinces.get(provincePos).getProvinceid();
                provinceName = provinces.get(provincePos).getProvince();
                getCities(provinceId);
            }
        });
        provinceList.setAdapter(provinceAdapter);

        cities = new ArrayList<>();
        cityList.setLayoutManager(new LinearLayoutManager(this));
//        cityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        cityAdapter = new CityAdapter(cities, new MyItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int pos = cityList.getChildAdapterPosition(view);
                int cityId = cities.get(pos).getId();
                Intent intent = new Intent();
                intent.putExtra("provinceId", provinces.get(provincePos).getId());
                intent.putExtra("provinceName",provinceName);
                intent.putExtra("cityId", cityId);
                intent.putExtra("cityName",cities.get(pos).getCity());
                setResult(0, intent);
                finish();
            }
        });
        cityList.setAdapter(cityAdapter);
    }

    private LoadingDialog dialog;

    private void getCities(int provinceId) {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("provinceId", provinceId);
        Subscriber subscriber = new Subscriber<HttpResult.CityResponse>() {
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
            public void onNext(HttpResult.CityResponse response) {
                if (response.code == 0) {
                    cities.clear();
                    cities.addAll(response.list);
                    cityAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }

            }
        };
        HttpMethods.getInstance().getCity(subscriber, map);
    }


}
