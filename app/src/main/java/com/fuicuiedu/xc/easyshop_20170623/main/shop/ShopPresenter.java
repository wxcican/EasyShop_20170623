package com.fuicuiedu.xc.easyshop_20170623.main.shop;

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

public class ShopPresenter extends MvpNullObjectBasePresenter<ShopView>{

    private Call call;

    private int pageInt = 1;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //刷新数据
    public void refreshData(String type){
        getView().showRefresh();
        //刷新数据，永远是最新的数据，也就是说永远请求第一页
        call = EasyShopClient.getInstance().getGoods(1,type);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showRefreshError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String json) {
                GoodsResult goodsResult = new Gson().fromJson(json,GoodsResult.class);
                switch (goodsResult.getCode()){
                    //成功
                    case 1:
                        //还没有商品（服务器还没有数据）
                        if (goodsResult.getDatas().size() == 0){
                            getView().showRefreshEnd();
                        }else{
                            getView().addRefreshData(goodsResult.getDatas());
                            getView().showRefreshEnd();
                        }
                        //分页改为2，之后要加载更多数据
                        pageInt = 2;
                        break;
                    //失败或其他
                    default:
                        getView().showRefreshError(goodsResult.getMessage());
                }

            }
        });
    }

    //加载更多
    public void loadData(String type){
        getView().showLoadMoreLoading();
        call = EasyShopClient.getInstance().getGoods(pageInt,type);
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
                            getView().showLoadMoreEnd();
                        }
                        //分页加载，之后加载新一页的数据
                        pageInt ++;
                        break;
                    default:
                        getView().showLoadMoreError(goodsResult.getMessage());
                }
            }
        });
    }
}
