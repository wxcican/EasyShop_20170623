package com.fuicuiedu.xc.easyshop_20170623;

import android.app.Application;

import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class EasyShopApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化本地配置
        CachePreferences.init(this);
    }
}
