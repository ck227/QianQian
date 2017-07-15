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
}
