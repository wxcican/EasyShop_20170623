package com.fuicuiedu.xc.easyshop_20170623.user.login;

import com.fuicuiedu.xc.easyshop_20170623.model.UserResult;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{

    // TODO: 2017/6/28 0028 环信相关

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null) call.cancel();
    }

    public void login(final String username, String password){
        getView().showPrb();
        call = EasyShopClient.getInstance().login(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                getView().hidePrb();
                UserResult userResult = new Gson().fromJson(json,UserResult.class);
                if (userResult.getCode() == 1){
                    // TODO: 2017/6/28 0028 保存用户信息到本地配置
                    getView().showMsg("登录成功");
                    getView().loginSuccess();
                }else if(userResult.getCode() ==2){
                    getView().showMsg(userResult.getMessage());
                    getView().loginFailed();
                }else{
                    getView().showMsg("未知错误！");
                }
            }
        });
    }
}
