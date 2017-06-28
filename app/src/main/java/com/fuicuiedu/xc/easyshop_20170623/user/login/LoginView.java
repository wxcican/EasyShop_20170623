package com.fuicuiedu.xc.easyshop_20170623.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface LoginView extends MvpView{

    void showPrb();

    void hidePrb();

    void loginFailed();

    void loginSuccess();

    void showMsg(String msg);
}
