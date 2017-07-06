package com.fuicuiedu.xc.easyshop_20170623;

import android.app.Application;

import com.feicuiedu.apphx.HxBaseApplication;
import com.feicuiedu.apphx.HxModuleInitializer;
import com.feicuiedu.apphx.model.repository.DefaultLocalInviteRepo;
import com.feicuiedu.apphx.model.repository.DefaultLocalUsersRepo;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class EasyShopApplication extends HxBaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化本地配置
        CachePreferences.init(this);

        // ####################   ImageLoader相关初始化    ################
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//开启硬盘缓存
                .cacheInMemory(true)//开启内存缓存
                .resetViewBeforeLoading(true)//加载前重置ImageView
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(1024 * 1024 * 4)//设置内存缓存的大小，4M
                .defaultDisplayImageOptions(displayImageOptions)//设置默认加载选项
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    //初始化环信模块
    @Override
    protected void initHxModule(HxModuleInitializer initializer) {
        initializer.setLocalInviteRepo(DefaultLocalInviteRepo.getInstance(this))
                .setLocalUsersRepo(DefaultLocalUsersRepo.getInstance(this))
                .setRemoteUsersRepo(new RemoteUserRepo())
                .init();
    }
}
