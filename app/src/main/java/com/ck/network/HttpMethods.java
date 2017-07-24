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
     * 、
     * 支付宝、淘宝认证
     *
     * 是测试发现的问题
     * 0.找回密码短信验证的问题（好了）
     * 1.个人信息认证，“服务与隐私”缺少链接（好了）
     * 2.详情里面的（到期还款额）值没有，通过首页那个接口获取的是这个问题，通过8.6那个接口获取的没问题（好了）
     * 3.贷款记录的状态不对（取消了的申请显示是还款中state==7）  （好了）
     * 4.放贷之后手机不知道，要重新进才能看到状态变化（好了）
     * 5.客服电话，收款支付宝账号要改，现在用的李华的(先不改)
     * 6.续期加回调（好了）
     * 7.续期成功后，“我想还款”界面要变化(好了)
     *
     *
     * 8.续了之后自动切换过去
     * 9.界面底色
     *
     *
     *
     * //下面是
     * 联系人要选择
     * 各种服务链接
     * 取消申请/(状态变了之后首页的刷新)   三个地方跳转到详情(index/还款/历史记录)，
     * 取消后首页的状态需要改变，跳转到了新的主界面，还没有测试过
     * 通信认证
     * 截图
     * 苹果开发者账号：申请邓白氏码中
     */

//    public static final String BASE_URL = "http://192.168.1.116:8080/lizhixinInterface/";
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
    public void login(Subscriber<HttpResult.LoginResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.login(options);
        toSubscribe(observable, subscriber);
    }

    //发送验证码
    public void sendCode(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.sendCode(options);
        toSubscribe(observable, subscriber);
    }

    //发送验证码
    public void sendFindCode(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.sendFindCode(options);
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

    //进首页之前的判断
    public void getHomeState(Subscriber<HttpResult.IndexResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getHomeState(options);
        toSubscribe(observable, subscriber);
    }


    //获取首页列表
    public void getCreditList(Subscriber<HttpResult.CreditListResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditList(options);
        toSubscribe(observable, subscriber);
    }

    //获取贷款金额列表
    public void getCreditAmountList(Subscriber<HttpResult.GetCreditAmountResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditAmountList(options);
        toSubscribe(observable, subscriber);
    }

    //获取贷款天数列表
    public void getCreditDayList(Subscriber<HttpResult.GetCreditDayResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditDayList(options);
        toSubscribe(observable, subscriber);
    }

    //获取贷款详细信息（计算费用）
    public void getCreditDetail(Subscriber<HttpResult.GetCreditDetailResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditDetail(options);
        toSubscribe(observable, subscriber);
    }

    //申请贷款
    public void addCredit(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addCredit(options);
        toSubscribe(observable, subscriber);
    }

    /**
     * 还款
     */
    //还款详细信息
    public void getPayDetail(Subscriber<HttpResult.GetPayDetailResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getPayDetail(options);
        toSubscribe(observable, subscriber);
    }

    //续期的详细信息
    public void getLaterPayDetail(Subscriber<HttpResult.GetCreditDetailResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getLaterPayDetail(options);
        toSubscribe(observable, subscriber);
    }

    //线上转账还款
    public void payOnline(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.payOnline(options);
        toSubscribe(observable, subscriber);
    }

    //线上转账还款 续期
    public void payOnlineLater(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.payOnlineLater(options);
        toSubscribe(observable, subscriber);
    }

    //贷款记录
    public void getCreditHistory(Subscriber<HttpResult.CreditHistoryResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCreditHistory(options);
        toSubscribe(observable, subscriber);
    }

    //进入详细
    public void getDetailByRecordId(Subscriber<HttpResult.GetDetailByRecordIdResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getDetailByRecordId(options);
        toSubscribe(observable, subscriber);
    }

    //取消申请
    public void cancelOrder(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.cancelOrder(options);
        toSubscribe(observable, subscriber);
    }

    //续期的回调
    public void payLaterCallback(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.payLaterCallback(options);
        toSubscribe(observable, subscriber);
    }

}
