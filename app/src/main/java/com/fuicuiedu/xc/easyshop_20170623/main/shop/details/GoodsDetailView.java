package com.fuicuiedu.xc.easyshop_20170623.main.shop.details;

import com.fuicuiedu.xc.easyshop_20170623.model.GoodsDetail;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface GoodsDetailView extends MvpView{

    //加载动画的显示和隐藏
    void showProgress();

    void hideProgress();

    //设置图片路径
    void setImageData(ArrayList<String> arrayList);

    //设置商品信息
    void setData(GoodsDetail data, User goods_user);

    //*商品不存在了*/
    void showError();

    //提示信息
    void showMessage(String msg);

    //*删除商品*/
    void deleteEnd();
}
