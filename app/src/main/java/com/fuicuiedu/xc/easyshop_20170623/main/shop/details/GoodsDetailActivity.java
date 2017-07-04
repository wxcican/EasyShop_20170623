package com.fuicuiedu.xc.easyshop_20170623.main.shop.details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.components.AvatarLoadOptions;
import com.fuicuiedu.xc.easyshop_20170623.components.ProgressDialogFragment;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsDetail;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopApi;
import com.fuicuiedu.xc.easyshop_20170623.user.login.LoginActivity;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
                //点击图片，跳转到图片详情页
                Intent intent = GoodsDetailInfoActivity.getStartIntent(getApplicationContext(),list_uri);
                startActivity(intent);
            }
        });
        viewPager.setAdapter(adapter);

        initView();

    }

    private void initView() {
        //拿到uuid
        str_uuid = getIntent().getStringExtra(UUID);
        //来自哪个页面
        int btn_show = getIntent().getIntExtra(STATE,0);
        //如果=1，来自我的页面
        if (btn_show == 1){
            tv_goods_delete.setVisibility(View.VISIBLE);//显示“删除”
            btn_detail_message.setVisibility(View.GONE);//隐藏“发消息”
        }
        //获取商品详情，业务
        presenter.getData(str_uuid);

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

    //点击，发消息，删除
    @OnClick({R.id.btn_detail_message,R.id.tv_goods_delete})
    public void onClick(View v){
        //判断登录状态
        if (CachePreferences.getUser().getName() == null){
            activityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (v.getId()){
            //发消息
            case R.id.btn_detail_message:
                // TODO: 2017/7/3 0003 跳转到环信发消息的页面
                activityUtils.showToast("跳转到环信发消息的页面,待实现");
                break;
            //删除
            case R.id.tv_goods_delete:
                //执行删除操作
                //弹出警告，询问用户是否要删除
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("商品删除");
                builder.setMessage("是否删除该商品？");
                //设置确认按钮，点击删除
                builder.setPositiveButton(R.string.goods_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //执行删除方法
                        presenter.delete(str_uuid);
                    }
                });
                //设置取消按钮
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
        }
    }

    // ###################################   视图接口相关    ################################
    @Override
    public void showProgress() {
        if (progressDialogFragment == null)progressDialogFragment = new ProgressDialogFragment();
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getSupportFragmentManager(),"progress");
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void setImageData(ArrayList<String> arrayList) {
        list_uri = arrayList;
        //加载图片
        for (int i = 0; i < list_uri.size(); i++) {
            ImageView view = new ImageView(this);
            ImageLoader.getInstance().displayImage(
                    EasyShopApi.IMAGE_URL + list_uri.get(i),
                    view, AvatarLoadOptions.build_item());
            //添加到图片控件集合当中
            list.add(view);
        }
        adapter.notifyDataSetChanged();
        //确认Viewpager显示页面数量之后，创建圆点指示器
        indicator.setViewPager(viewPager);
    }

    @Override
    public void setData(GoodsDetail data, User goods_user) {
        //数据绑定
        this.goods_user = goods_user;
        tv_detail_name.setText(data.getName());
        tv_detail_price.setText(getString(R.string.goods_money, data.getPrice()));
        tv_detail_master.setText(getString(R.string.goods_detail_master, goods_user.getNick_name()));
        tv_detail_describe.setText(data.getDescription());
    }

    @Override
    public void showError() {
        tv_goods_error.setVisibility(View.VISIBLE);
        toolbar.setTitle("商品过期不存在");
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void deleteEnd() {
        finish();
    }
}
