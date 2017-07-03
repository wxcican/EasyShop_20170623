package com.fuicuiedu.xc.easyshop_20170623.main.shop.details;

import com.fuicuiedu.xc.easyshop_20170623.model.GoodsDetail;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsDetailResult;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class GoodsDetailPresenter extends MvpNullObjectBasePresenter<GoodsDetailView>{

    private Call getDetailCall;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (getDetailCall != null) getDetailCall.cancel();
    }

    //获取商品详细数据
    public void getData(String uuid){
        getView().showProgress();
        getDetailCall  = EasyShopClient.getInstance().getGoodsData(uuid);
        getDetailCall.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                getView().hideProgress();
                GoodsDetailResult result = new Gson().fromJson(json,GoodsDetailResult.class);
                if (result.getCode() == 1){
                    //商品详情
                    GoodsDetail goodsDetail = result.getDatas();
                    //用来存放图品路径的集合
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < goodsDetail.getPages().size(); i++) {
                        String page = goodsDetail.getPages().get(i).getUri();
                        list.add(page);
                    }
                    //设置图片路径，完成加载图片
                    getView().setImageData(list);
                    //设置商品信息
                    getView().setData(goodsDetail,result.getUser());
                }else{
                    getView().showError();
                }
            }
        });
    }

    // TODO: 2017/7/3 0003 删除商品业务 
}
