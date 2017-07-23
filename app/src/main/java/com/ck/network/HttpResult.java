package com.ck.network;

import com.ck.bean.Banks;
import com.ck.bean.CheckInfo;
import com.ck.bean.CheckPhone;
import com.ck.bean.CheckStatus;
import com.ck.bean.Login;
import com.ck.bean.credit.Credit;
import com.ck.bean.credit.CreditDetail;
import com.ck.bean.credit.GetCreditAmount;
import com.ck.bean.credit.GetCreditDay;
import com.ck.bean.credit.GetCreditDetail;
import com.ck.bean.credit.PayNow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public class HttpResult {

    /**
     * 登录、发送验证码、注册、找回密码、修改密码
     * 添加手机认证、添加身份证认证／银行卡认证/通讯录认证/个人信息认证
     * 反馈
     * 申请贷款/首页判断
     */
    public static class BaseResponse {
        public int code;
        public String msg;
    }

    //判断首页状态
    public static class IndexResponse extends BaseResponse{
        public CreditDetail obj;
    }

    //登录
    public static class LoginResponse extends BaseResponse{
        public Login obj;
    }

    //认证状态
    public static class CheckResponse extends BaseResponse {
        public CheckStatus obj;
    }

    //手机认证状态
    public static class CheckPhoneResponse extends BaseResponse {
        public CheckPhone obj;
    }

    //上传照片
    public static class UploadPicResponse extends BaseResponse {
        public String obj;
    }

    //获取银行列表
    public static class BanksResponse extends BaseResponse {
        public Banks obj;
    }

    //获取个人信息的各种列表
    public static class InfoResponse extends BaseResponse {
        public CheckInfo obj;
    }


    /**
     * 下面的是首页的
     */


    //首页列表
    public static class CreditListResponse extends BaseResponse {
        public List<Credit> list;
    }

    //金额列表
    public static class GetCreditAmountResponse extends BaseResponse {
        public ArrayList<GetCreditAmount> list;
    }

    //天数列表
    public static class GetCreditDayResponse extends BaseResponse {
        public ArrayList<GetCreditDay> list;
    }

    //获取详细信息
    public static class GetCreditDetailResponse extends BaseResponse {
        public GetCreditDetail obj;
    }

    /**
     * 下面的是还款的
     */
    //获取还款信息
    public static class GetPayDetailResponse extends BaseResponse {
        public PayNow obj;
    }

}
