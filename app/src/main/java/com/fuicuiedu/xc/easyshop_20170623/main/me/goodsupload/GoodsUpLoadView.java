package com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface GoodsUpLoadView extends MvpView{

    void showPrb();

    void hidePrb();

    void upLoadSuccess();

    void showMsg(String msg);
}
