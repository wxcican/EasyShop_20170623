package com.fuicuiedu.xc.easyshop_20170623.main.me.personInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface PersonVIew extends MvpView{

    void showPrb();

    void hidePrb();

    void showMsg(String msg);
    //用来更新头像
    void updataAvatar(String url);
}
