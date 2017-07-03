package com.fuicuiedu.xc.easyshop_20170623.main.shop.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.components.ProgressDialogFragment;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsDetail;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class GoodsDetailActivity extends MvpActivity<GoodsDetailView,GoodsDetailPresenter> implements GoodsDetailView{

    // ###############################  跳转，来源判断相关 start ###########################

    private static final String UUID = "uuid";
    //从不用的页面进入商品详情页的状态值，0 = 从市场页面,1 = 从我的页面进来
    private static final String STATE = "state";

    public static Intent getStateIntent(Context context,String uuid,int state){
        Intent intent = new Intent(context,GoodsDetailActivity.class);
        intent.putExtra(UUID,uuid);
        intent.putExtra(STATE,state);
        return intent;
    }
    // ###############################  跳转，来源判断相关 end ###########################


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    /*使用开源库CircleIndicator来实现ViewPager的圆点指示器。*/
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.tv_detail_name)
    TextView tv_detail_name;
    @BindView(R.id.tv_detail_price)
    TextView tv_detail_price;
    @BindView(R.id.tv_detail_master)
    TextView tv_detail_master;
    @BindView(R.id.tv_detail_describe)
    TextView tv_detail_describe;
    @BindView(R.id.tv_goods_delete)
    TextView tv_goods_delete;
    @BindView(R.id.tv_goods_error)
    TextView tv_goods_error;
    @BindView(R.id.btn_detail_message)
    Button btn_detail_message;

    private String str_uuid;//商品的uuid
    private ArrayList<ImageView> list;//商品图片
    private ArrayList<String> list_uri;//图片路径的集合
    private ActivityUtils activityUtils;
    private User goods_user;
    private ProgressDialogFragment progressDialogFragment;
    //ViewPager适配器
    private GoodsDetailAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        //左上角添加返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        list_uri = new ArrayList<>();
        adapter = new GoodsDetailAdapter(list);
        adapter.setListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                // TODO: 2017/7/3 0003 点击图片，跳转到图片详情页
            }
        });
        viewPager.setAdapter(adapter);

        initView();

    }

    private void initView() {
        // TODO: 2017/7/3 0003  完成数据的初始化相关代码，逻辑
    }

    @NonNull
    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    //左上角返回，需实现的方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }


    // ###################################   视图接口相关    ################################
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setImageData(ArrayList<String> arrayList) {

    }

    @Override
    public void setData(GoodsDetail data, User goods_user) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void deleteEnd() {

    }
}
