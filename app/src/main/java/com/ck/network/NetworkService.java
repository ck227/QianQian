package com.ck.network;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public interface NetworkService {

    //登录
    @GET("user/login.html")
    Observable<HttpResult.BaseResponse> login(@QueryMap Map<String, Object> options);

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
}
