package com.fuicuiedu.xc.easyshop_20170623.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//"username": "xc62",
//        "other": "/images/0F8EC12223174657B2E842076D54C361/9B61E85244.jpg",
//        "nickname": "555",
//        "name": "yt59856b15cf394e7b84a7d48447d16098",
//        "uuid": "0F8EC12223174657B2E842076D54C361",
//        "password": "123456"

public class User {

    @SerializedName("username")
    private String name;
    @SerializedName("name")
    private String hx_Id;
    @SerializedName("uuid")
    private String table_Id;
    private String password;

    @SerializedName("other")
    private String head_Image;
    @SerializedName("nickname")
    private String nick_name;

    public String getHead_Image() {
        return head_Image;
    }

    public void setHead_Image(String head_Image) {
        this.head_Image = head_Image;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHx_Id() {
        return hx_Id;
    }

    public void setHx_Id(String hx_Id) {
        this.hx_Id = hx_Id;
    }

    public String getTable_Id() {
        return table_Id;
    }

    public void setTable_Id(String table_Id) {
        this.table_Id = table_Id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
