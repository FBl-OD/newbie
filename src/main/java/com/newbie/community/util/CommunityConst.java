package com.newbie.community.util;

public interface CommunityConst {
    /**
     * 响应状态码
     * 1xx - 正常
     * 2xx - 客户端参数异常
     * 3xx - 服务器内部异常
     */
    //正常状态码
    int OK=100;

    //客户端异常
    int CLIENT_ERROR=200;

    //服务端异常
    int SERVER_ERROR=300;
}
