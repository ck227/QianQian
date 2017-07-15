package com.ck.network;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public class HttpMethods {

    public static final String BASE_URL = "http://192.168.100.222:8080/lizhixinInterface/";

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private NetworkService networkService;

    private HttpMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        networkService = retrofit.create(NetworkService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(s);
    }

    //登录
    public void login(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.login(options);
        toSubscribe(observable, subscriber);
    }

    //发送验证码
    public void sendCode(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.sendCode(options);
        toSubscribe(observable, subscriber);
    }

    //注册
    public void register(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.register(options);
        toSubscribe(observable, subscriber);
    }

}
