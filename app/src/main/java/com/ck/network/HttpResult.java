package com.ck.network;

import com.ck.bean.Banks;
import com.ck.bean.CheckInfo;
import com.ck.bean.CheckPhone;
import com.ck.bean.CheckStatus;
import com.ck.bean.credit.Credit;
import com.ck.bean.credit.GetCreditAmount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public class HttpResult {

    /**
     * 登录、发送验证码、注册、找回密码、修改密码
     * <p>
     * 添加手机认证、添加身份证认证／银行卡认证/通讯录认证/个人信息认证
     * <p>
     * 反馈
     */
    public static class BaseResponse {
        public int code;
        public String msg;
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

    public static class CreditListResponse extends BaseResponse {
        public List<Credit> list;
    }

    public static class GetCreditAmountResponse extends BaseResponse {
        public List<GetCreditAmount> list;
    }
}
