package com.fuicuiedu.xc.easyshop_20170623.user.register;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class UserResult {

    private int code;
    private String message;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //alt + insert

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
}
