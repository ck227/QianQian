package com.ck.network;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public interface NetworkService {

    //登录
    @GET("user/login.html")
    Observable<HttpResult.LoginResponse> login(@QueryMap Map<String, Object> options);

    //发送验证码
    @GET("smsverify/send.html")
    Observable<HttpResult.BaseResponse> sendCode(@QueryMap Map<String, Object> options);

    //注册
    @GET("user/registere.html")
    Observable<HttpResult.BaseResponse> register(@QueryMap Map<String, Object> options);

    //找回密码
    @GET("user/retrievePassWord.html")
    Observable<HttpResult.BaseResponse> findPwd(@QueryMap Map<String, Object> options);

    //修改密码
    @GET("user/updatePassWord.html.html")
    Observable<HttpResult.BaseResponse> updatePwd(@QueryMap Map<String, Object> options);


    //认证状态
    @GET("userAuthentication/authentication.html")
    Observable<HttpResult.CheckResponse> checkStatus(@QueryMap Map<String, Object> options);

    //手机认证状态
    @GET("userPhoneService/user.html")
    Observable<HttpResult.CheckPhoneResponse> checkPhone(@QueryMap Map<String, Object> options);

    //添加手机认证
    @GET("userPhoneService/add.html")
    Observable<HttpResult.BaseResponse> addCheckPhone(@QueryMap Map<String, Object> options);

    //上传图片
    @Multipart
    @POST("upload/upload.html")
    Observable<HttpResult.UploadPicResponse> uploadPic(@Part("file\"; filename=\"ck.png") RequestBody body, @QueryMap Map<String, Object> options);

    //添加身份证认证
    @GET("userCard/add.html")
    Observable<HttpResult.BaseResponse> addCheckId(@QueryMap Map<String, Object> options);

    //获取银行列表
    @GET("systemType/userbank.html")
    Observable<HttpResult.BanksResponse> getBanks();

    //添加银行卡认证
    @GET("userBank/add.html")
    Observable<HttpResult.BaseResponse> addCheckBank(@QueryMap Map<String, Object> options);

    //添加通讯录
    @POST("userBookPhone/add.html")
    Observable<HttpResult.BaseResponse> addCheckContact(@QueryMap Map<String, Object> options);

    //获取信息认证的各种信息
    @GET("systemType/userinfo.html")
    Observable<HttpResult.InfoResponse> getInfos();

    //添加个人信息认证
    @GET("userInfo/add.html")
    Observable<HttpResult.BaseResponse> addCheckInfo(@QueryMap Map<String, Object> options);

    //意见反馈
    @GET("feedBack/add.html")
    Observable<HttpResult.BaseResponse> sendFeedback(@QueryMap Map<String, Object> options);


    /**
     * 下面的是首页的
     */

    //进首页之前的判断
    @GET("loan/homePage.html")
    Observable<HttpResult.BaseResponse> getHomeState(@QueryMap Map<String, Object> options);

    //首页列表
    @GET("loan/loanType.html")
    Observable<HttpResult.CreditListResponse> getCreditList(@QueryMap Map<String, Object> options);

    //获取贷款的金额列表
    @GET("loanAmount/list.html")
    Observable<HttpResult.GetCreditAmountResponse> getCreditAmountList(@QueryMap Map<String, Object> options);

    //获取贷款的天数列表
    @GET("loanDay/list.html")
    Observable<HttpResult.GetCreditDayResponse> getCreditDayList(@QueryMap Map<String, Object> options);

    //获取贷款的详细信息
    @GET("loanRule/rule.html")
    Observable<HttpResult.GetCreditDetailResponse> getCreditDetail(@QueryMap Map<String, Object> options);

    //申请贷款
    @GET("loanRecord/add.html")
    Observable<HttpResult.BaseResponse> addCredit(@QueryMap Map<String, Object> options);

    /**
     * 还款
     */

    //还款详细信息
    @GET("repaymentRecord/userRepayment.html")
    Observable<HttpResult.GetPayDetailResponse> getPayDetail(@QueryMap Map<String, Object> options);

    //获取续贷的详细信息
    @GET("loanRenewal/renewal.html")
    Observable<HttpResult.GetCreditDetailResponse> getLaterPayDetail(@QueryMap Map<String, Object> options);



}
