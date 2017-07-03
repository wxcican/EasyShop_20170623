package com.fuicuiedu.xc.easyshop_20170623.main.me.persongoods;

import com.fuicuiedu.xc.easyshop_20170623.main.shop.ShopView;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsResult;
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

//我的商品页面的业务类（复用市场页面视图接口）
public class PersonGoodsPresenter extends MvpNullObjectBasePresenter<ShopView>{

    private Call call;
    private int pageInt = 1;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call !=null ) call.cancel();
    }

    //刷新数据
    public void refreshData(String type){
        getView().showRefresh();
        call = EasyShopClient.getInstance().getPersonData(1,type, CachePreferences.getUser().getName());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showRefreshError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                GoodsResult goodsResult = new Gson().fromJson(json,GoodsResult.class);
                switch (goodsResult.getCode()){
                    case 1:
                        if (goodsResult.getDatas().size() == 0){
                            getView().showRefreshEnd();
                        }else{
                            getView().addRefreshData(goodsResult.getDatas());
                            getView().hideRefresh();
                        }
                        pageInt = 2;
                        break;
                    default:
                        getView().showRefreshError(goodsResult.getMessage());
                }
            }
        });
    }


    //加载更多
    public void loadData(String type){
        getView().showLoadMoreLoading();
        call = EasyShopClient.getInstance().getPersonData(pageInt,type,CachePreferences.getUser().getName());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showLoadMoreError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                GoodsResult goodsResult = new Gson().fromJson(json,GoodsResult.class);
                switch (goodsResult.getCode()){
                    case 1:
                        if (goodsResult.getDatas().size() == 0){
                            getView().showLoadMoreEnd();
                        }else{
                            getView().addMoreData(goodsResult.getDatas());
                            getView().hideLoadMore();
                        }
                        pageInt++;
                        break;
                    default:
                        getView().showLoadMoreError(goodsResult.getMessage());
                }
            }
        });
    }
}
