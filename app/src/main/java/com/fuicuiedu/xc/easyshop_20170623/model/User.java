package com.fuicuiedu.xc.easyshop_20170623.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class User {

    @SerializedName("username")
    private String name;
    @SerializedName("name")
    private String hx_Id;
    @SerializedName("uuid")
    private String table_Id;
    private String password;

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
