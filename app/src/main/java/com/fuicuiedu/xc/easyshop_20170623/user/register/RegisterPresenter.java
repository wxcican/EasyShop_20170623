package com.fuicuiedu.xc.easyshop_20170623.user.register;

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

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{

    //业务：执行网络请求，完成注册
    //在特定的地方，触发相应的UI操作

    // TODO: 2017/6/28 0028 环信相关

    private Call call;

    //视图销毁，取消网络请求
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    public void register(String username, String password){
        //显示进度条
        getView().showPrb();

        call = EasyShopClient.getInstance().register(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                //隐藏进度条
                getView().hidePrb();
                //显示异常信息
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                //隐藏进度条
                getView().hidePrb();
                //拿到返回结果
                UserResult result = new Gson().fromJson(json,UserResult.class);
                //根据不用的结果码来处理
                if (result.getCode() == 1){
                    //成功提示
                    getView().showMsg("注册成功");
                    // TODO: 2017/6/28 0028 用户信息保存到本地配置当中，自动登录
                    //执行注册成功的方法
                    getView().registerSuccess();
                } else if (result.getCode() == 2) {
                    //提示失败信息
                    getView().showMsg(result.getMessage());
                    //执行注册失败的方法
                    getView().registerFailed();
                }else{
                    getView().showMsg("未知错误！");
                }
            }
        });
    }


}
