package com.fuicuiedu.xc.easyshop_20170623.network;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class EasyShopClient {

    private static EasyShopClient easyShopClient;

    private OkHttpClient okHttpClient;

    private EasyShopClient(){
        okHttpClient = new OkHttpClient();
    }

    public static EasyShopClient getInstance(){
        if (easyShopClient == null){
            easyShopClient = new EasyShopClient();
        }
        return easyShopClient;
    }

    //注册
    public Call register(String username, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }
}
