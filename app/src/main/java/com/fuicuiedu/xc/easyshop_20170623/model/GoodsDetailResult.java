package com.fuicuiedu.xc.easyshop_20170623.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//"code": 1,
//        "msg": " success",
//        "datas": {

public class GoodsDetailResult {

    private int code;
    @SerializedName("msg")
    private String message;
    private GoodsDetail datas;

    //发布者信息
    private User user;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GoodsDetail getDatas() {
        return datas;
    }

    public User getUser() {
        return user;
    }
}
