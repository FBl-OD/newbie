package com.newbie.community.Vo;

import java.util.Map;

/**
 * 用于封装异步请求的结果
 */
public class ResultVo {

    //状态码
    private int code;

    //状态消息
    private String msg;

    //返回数据
    private Map<String,Object> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
