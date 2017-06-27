package com.fuicuiedu.xc.easyshop_20170623.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class UserResult {

    private int code;
    @SerializedName("msg")
    private String message;
    private User user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
