package com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload;

import com.fuicuiedu.xc.easyshop_20170623.commons.MyFileUtils;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsUpLoad;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsUpLoadResult;
import com.fuicuiedu.xc.easyshop_20170623.model.ImageItem;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class GoodsUpLoadPresenter extends MvpNullObjectBasePresenter<GoodsUpLoadView>{

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //商品上传
    public void upLoad(GoodsUpLoad goodsUpLoad, List<ImageItem> list){
        getView().showPrb();
        call = EasyShopClient.getInstance().upLoad(goodsUpLoad,getFiles(list));
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                getView().hidePrb();
                GoodsUpLoadResult result = new Gson().fromJson(json,GoodsUpLoadResult.class);
                getView().showMsg(result.getMessage());
                //上传成功
                if (result.getCode() == 1){
                    getView().upLoadSuccess();
                }
            }
        });
    }

    //根据imageItem（图片路径）获取图片文件
    private ArrayList<File> getFiles(List<ImageItem> list){
        ArrayList<File> files = new ArrayList<>();
        for (ImageItem imageItem : list){
            //根据图片路径，拿到图片文件
            File file = new File(MyFileUtils.SD_PATH + imageItem.getImagePath());
            files.add(file);
        }
        return files;
    }
}
