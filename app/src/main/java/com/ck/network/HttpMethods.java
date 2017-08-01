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
     * <p>
     * 混淆/
     * <p>
     * 是测试发现的问题
     * 0.找回密码短信验证的问题（好了）
     * 1.个人信息认证，“服务与隐私”缺少链接（好了）
     * 2.详情里面的（到期还款额）值没有，通过首页那个接口获取的是这个问题，通过8.6那个接口获取的没问题（好了）
     * 3.贷款记录的状态不对（取消了的申请显示是还款中state==7）  （好了）
     * 4.放贷之后手机不知道，要重新进才能看到状态变化（好了）
     * 5.客服电话，收款支付宝账号要改，现在用的李华的(好了)
     * 6.续期加回调（好了）
     * 7.续期成功后，“我想还款”界面要变化(好了)
     * 8.续了之后自动切换过去（好了）
     * 9.界面底色（好了）
     * 10.修改logo（好了）
     * 11.认证的界面还有功能优化（好了）
     * 12.支付宝、淘宝认证（好了要调试）
     * 13.紧急联系人留3个(好了)
     * 14.银行卡认证，添加省市（好了）
     * 15.续费利息加服务费（好了）
     * 16.续期的状态界面(好了)
     * 17.信息中心认证修改界面（好了）
     * 18.支付直接弹出框(好了)
     * 19.修改接口地址(好了)
     * 20.通讯录的两个网页去掉（好了）
     * 21.logo／短信的开通／(好了)
     * 22.所有权限的问题(好了)
     * 23.客服改成弹窗(好了)
     * 24.贷款记录空的加显示(好了)
     * 25.录屏取消的话，界面返回(好了)
     *
     * 26.7.0相机
     * 27.版本更新（好了）
     * 28.通讯录选择问题 （很痛苦）
     * 29.引导页
     * 30.签名
     * 31.勾勾要可以点(好了)
     * 32.银行认证加手机号（好了）

     * //下面是
     * 联系人要选择
     * 各种服务链接
     * 取消申请/(状态变了之后首页的刷新)   三个地方跳转到详情(index/还款/历史记录)，
     * 取消后首页的状态需要改变，跳转到了新的主界面
     * 通信认证
     * 截图
     * 苹果开发者账号：申请邓白氏码中
     */

//    public static final String BASE_URL = "http://192.168.1.138:8080/lizhixinInterface/";
//    public static final String BASE_URL = "http://115.28.161.246:3080/lizhixinInterface/";
    public static final String BASE_URL = "http://39.108.82.199/lizhixinInterface/";
//  public static final String BASE_URL = "http://www.lizhixin.cn/lizhixinInterface/";

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

    //上传视频
    public void uploadVideo(Subscriber<HttpResult.UploadPicResponse> subscriber, RequestBody body, Map<String, Object> options) {
        Observable observable = networkService.uploadVideo(body, options);
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

    //支付宝认证
    public void addAlipay(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addAlipay(options);
        toSubscribe(observable, subscriber);
    }

    //淘宝认证
    public void addTaobao(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.addTaobao(options);
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

    //获取省份
    public void getProvince(Subscriber<HttpResult.ProvinceResponse> subscriber) {
        Observable observable = networkService.getProvince();
        toSubscribe(observable, subscriber);
    }

    //获取城市
    public void getCity(Subscriber<HttpResult.CityResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCity(options);
        toSubscribe(observable, subscriber);
    }

    //获取续期状态
    public void getPayLaterState(Subscriber<HttpResult.PayLaterState> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getPayLaterState(options);
        toSubscribe(observable, subscriber);
    }

    //取消续期
    public void cancelPayLater(Subscriber<HttpResult.BaseResponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.cancelPayLater(options);
        toSubscribe(observable, subscriber);
    }

    //版本更新
    public void getVersion(Subscriber<HttpResult.VersionReponse> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getVersion(options);
        toSubscribe(observable, subscriber);
    }

}
