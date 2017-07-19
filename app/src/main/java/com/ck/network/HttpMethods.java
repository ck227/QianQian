package com.ck.network;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

    /**
     * TODO : 乱码问题、联系人要选择、
     * <p>
     * 支付宝、淘宝认证、首页
     */

//    public static final String BASE_URL = "http://192.168.1.114:8080/lizhixinInterface/";
    public static final String BASE_URL = "http://115.28.161.246:3080/lizhixinInterface/";

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

    //找回密码
    public void findPwd(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.findPwd(options);
        toSubscribe(observable, subscriber);
    }

    //修改密码
    public void updatePwd(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.updatePwd(options);
        toSubscribe(observable, subscriber);
    }

    //认证状态
    public void checkStatus(Subscriber<HttpResult.CheckResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.checkStatus(options);
        toSubscribe(observable, subscriber);
    }

    //手机认证状态
    public void checkPhone(Subscriber<HttpResult.CheckPhoneResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.checkPhone(options);
        toSubscribe(observable, subscriber);
    }

    //添加手机认证
    public void addCheckPhone(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCheckPhone(options);
        toSubscribe(observable, subscriber);
    }

    //上传图片
    public void uploadPic(Subscriber<HttpResult.UploadPicResponse> subscriber, RequestBody body, Map<String, Object> options) {
        Observable observable = networkService.uploadPic(body, options);
        toSubscribe(observable, subscriber);
    }

    //添加身份证认证
    public void addCheckId(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCheckId(options);
        toSubscribe(observable, subscriber);
    }

    //获取银行卡列表
    public void getBanks(Subscriber<HttpResult.BanksResponse> subscriber) {
        Observable observable = networkService.getBanks();
        toSubscribe(observable, subscriber);
    }

    //添加银行卡认证
    public void addCheckBank(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCheckBank(options);
        toSubscribe(observable, subscriber);
    }

    //添加通讯录认证
    public void addCheckContact(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCheckContact(options);
        toSubscribe(observable, subscriber);
    }

    //获取各种认证信息
    public void getInfos(Subscriber<HttpResult.InfoResponse> subscriber) {
        Observable observable = networkService.getInfos();
        toSubscribe(observable, subscriber);
    }

    //添加个人信息认证
    public void addCheckInfo(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCheckInfo(options);
        toSubscribe(observable, subscriber);
    }

    //意见反馈
    public void sendFeedback(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.sendFeedback(options);
        toSubscribe(observable, subscriber);
    }

    /**
     * 下面的是首页的
     */

    //获取首页列表
    public void getCreditList(Subscriber<HttpResult.CreditListResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditList(options);
        toSubscribe(observable, subscriber);
    }




}
