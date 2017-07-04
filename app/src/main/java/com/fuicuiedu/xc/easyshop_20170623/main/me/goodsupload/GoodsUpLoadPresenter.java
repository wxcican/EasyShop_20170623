package com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload;

import com.fuicuiedu.xc.easyshop_20170623.model.GoodsUpLoad;
import com.fuicuiedu.xc.easyshop_20170623.model.ImageItem;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

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
//        call = EasyShopClient.getInstance().upLoad(goodsUpLoad,图片文件集合);

    }

    //根据imageItem（图片路径）获取图片文件
}
