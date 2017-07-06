package com.fuicuiedu.xc.easyshop_20170623.main.me.personInfo;

import com.feicuiedu.apphx.model.HxMessageManager;
import com.feicuiedu.apphx.model.HxUserManager;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.fuicuiedu.xc.easyshop_20170623.model.UserResult;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopApi;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class PersonPresenter extends MvpNullObjectBasePresenter<PersonView>{


    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call !=null )call.cancel();
    }

    //上传头像
    public void updataAvatar(File file){
        getView().showPrb();

        call = EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                getView().hidePrb();
                UserResult result = new Gson().fromJson(json,UserResult.class);

                if (result == null){
                    getView().showMsg("未知错误！");
                }else if (result.getCode() != 1){
                    getView().showMsg(result.getMessage());
                    return;
                }

                User user = result.getUser();
                CachePreferences.setUser(user);

                //上传成功，触发UI操作（更新头像）
                getView().updataAvatar(result.getUser().getHead_Image());

                //环信更新头像
                HxUserManager.getInstance().updateAvatar(EasyShopApi.IMAGE_URL + result.getUser().getHead_Image());
                HxMessageManager.getInstance().sendAvatarUpdateMessage(EasyShopApi.IMAGE_URL + result.getUser().getHead_Image());

            }
        });
    }
}
