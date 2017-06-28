package com.fuicuiedu.xc.easyshop_20170623.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface RegisterView extends MvpView{

    //显示进度条
    void showPrb();
    //隐藏进度条
    void hidePrb();
    //注册成功
    void registerSuccess();
    //注册失败
    void registerFailed();
    //提示信息
    void showMsg(String msg);
    //用户名或密码不对时提示用户
    void showUserPasswordError(String msg);

}
