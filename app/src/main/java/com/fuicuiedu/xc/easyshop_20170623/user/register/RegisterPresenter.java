package com.fuicuiedu.xc.easyshop_20170623.user.register;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.fuicuiedu.xc.easyshop_20170623.commons.CurrentUser;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.fuicuiedu.xc.easyshop_20170623.model.UserResult;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{

    //业务：执行网络请求，完成注册
    //在特定的地方，触发相应的UI操作

    //环信相关
    private String hxPassword;

    private Call call;

    @Override
    public void attachView(RegisterView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    //视图销毁，取消网络请求
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
        EventBus.getDefault().unregister(this);
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
                    //用户信息保存到本地配置当中，自动登录
                    User user = result.getUser();
                    CachePreferences.setUser(user);

                    //登录环信相关操作（会用过EventBus返回值）
                    EaseUser easeUser = CurrentUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser,hxPassword);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event) {
        //判断是否是登录成功事件
        if (event.type != HxEventType.LOGIN) return;

        hxPassword = null;
        //调用注册成功的方法
        getView().registerSuccess();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event) {
        //判断是否是登录失败
        if (event.type != HxEventType.LOGIN) return;

        hxPassword = null;
        getView().hidePrb();
        getView().showMsg(event.toString());
    }


}
