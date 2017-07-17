package com.ck.network;

import com.ck.bean.CheckStatus;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public class HttpResult {

    /**
     * 登录、发送验证码、注册、找回密码、修改密码
     */
    public static class BaseResponse {
        public int code;
        public String msg;
    }

    public static class CheckResponse extends BaseResponse {
        public CheckStatus obj;
    }

}
