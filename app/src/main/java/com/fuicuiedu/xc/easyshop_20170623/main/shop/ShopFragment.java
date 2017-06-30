package com.fuicuiedu.xc.easyshop_20170623.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.model.GoodsInfo;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class ShopFragment extends MvpFragment<ShopView, ShopPresenter> implements ShopView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//展示商品的列表
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv_load_error)
    TextView tvLoadError;//错误提示

    private ActivityUtils activityUtils;
    private ShopAdapter adapter;

    //获取商品时，商品类型，获取全部商品时为空
    private String pageType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        adapter = new ShopAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//初始化视图
    }

    private void initView() {
        //初始化RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //item点事件（适配器添加跳转事件）
//        适配器的item点击事件
        adapter.setListener(new ShopAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(GoodsInfo goodsInfo) {
                // TODO: 2017/6/30 0030 跳转到详情页
                activityUtils.showToast("跳转到详情页,待实现");
            }
        });
//        给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);

        //初始化RefreshLayout
        //使用本对象作为key，用来记录上一次刷新时间，如果两次刷新时间间隔太近，不会触发刷新方法
        refreshLayout.setLastUpdateTimeRelateObject(this);
        //设置刷新时显示的背景色
        refreshLayout.setBackgroundResource(R.color.recycler_bg);
        //关闭header所耗时长
        refreshLayout.setDurationToCloseHeader(1500);
        //实现刷新，加载回调
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //加载更多时触发
                presenter.loadData(pageType);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                presenter.refreshData(pageType);
            }
        });
    }

    //再次打开市场页面时，刷新下数据
    @Override
    public void onStart() {
        super.onStart();
        if (adapter.getItemCount() == 0) {
            refreshLayout.autoRefresh();
        }
    }

    //点击错误视图时刷新数据
    @OnClick(R.id.tv_load_error)
    public void onClick() {
        refreshLayout.autoRefresh();
    }


    //  #############################    视图接口相关   #######################

    @Override
    public void showRefresh() {
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        //判断是否拿到数据
        if (adapter.getItemCount() > 0) {
            activityUtils.showToast(msg);
            return;
        }
        //显示刷新错误提示
        tvLoadError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRefreshEnd() {
        activityUtils.showToast(R.string.refresh_more_end);
        //停止刷新
        refreshLayout.refreshComplete();
    }

    @Override
    public void hideRefresh() {
        //停止刷新
        refreshLayout.refreshComplete();
    }

    @Override
    public void addRefreshData(List<GoodsInfo> data) {
        //数据清空
        adapter.clear();
        if (data != null) {
            adapter.addData(data);
        }
    }

    @Override
    public void showLoadMoreLoading() {
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        refreshLayout.refreshComplete();
        if (adapter.getItemCount() > 0) {
            activityUtils.showToast(msg);
            return;
        }
        tvLoadError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadMoreEnd() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void hideLoadMore() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void addMoreData(List<GoodsInfo> data) {
        adapter.addData(data);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

}
