package com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.model.ImageItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class GoodsUpLoadAdapter extends RecyclerView.Adapter {

    //适配器的数据
    private ArrayList<ImageItem> list = new ArrayList<>();
    private LayoutInflater inflater;

    public GoodsUpLoadAdapter(Context context, ArrayList<ImageItem> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    // ############################   逻辑：模式的选择 start   ##########################
    //编辑时的模式，1 = 有图，2 = 无图(显示加号图片的布局)
    public static final int MODE_NORMAL = 1;
    public static final int MODE_MULTI_SELECT = 2;

    //代表图片的编辑模式
    public int mode;

    //用枚举，表示item类型，有图或无图
    public enum ITEM_TYPE {
        ITEM_NORMAL, ITEM_ADD
    }

    //模式设置
    public void changeMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    //获取当前模式
    public int getMode() {
        return mode;
    }
    // ############################   逻辑：模式的选择 end   ##########################

    // ########################### 外部调用的相关方法 start #######################
    //添加图片（imageItem）
    public void add(ImageItem imageItem) {
        list.add(imageItem);
    }

    //获取数据数量
    public int getSize() {
        return list.size();
    }

    //获取数据
    public ArrayList<ImageItem> getList() {
        return list;
    }

    //刷新数据
    public void notifyData() {
        notifyDataSetChanged();
    }
    // ########################### 外部调用的相关方法 end #######################


    //确定ViewType的值
    @Override
    public int getItemViewType(int position) {
        //当position与图片数量相同时，则为加号布局
        if (position == list.size()) return ITEM_TYPE.ITEM_ADD.ordinal();
        return ITEM_TYPE.ITEM_NORMAL.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断当前显示item的类型，有图或无图，从而选择不同的ViewHolder（不同布局）
        if (viewType == ITEM_TYPE.ITEM_NORMAL.ordinal()) {
            //有图的ViewHolder
            return new ItemSelectViewHolder(
                    inflater.inflate(R.layout.layout_item_recyclerview, parent, false));
        } else {
            //无图，显示加号的ViewHolder
            return new ItemAddViewHolder(
                    inflater.inflate(R.layout.layout_item_recyclerviewlast, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        //最多八张图
        return Math.min(list.size() + 1,8);
    }


    //显示添加按钮的ViewHolder
    public static class ItemAddViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ib_recycle_add)
        ImageButton ib_add;

        public ItemAddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //已经有图片的ViewHolder
    public static class ItemSelectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.cb_check_photo)
        CheckBox checkBox;
        ImageItem photo;//用来控制checkbox的选择属性

        public ItemSelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    // #############################  item点击事件(接口回调)   #######################
    public interface OnItemClickedListener {

        //无图，点击添加图片
        void onAddClicked();

        //有图，点击跳转到图片展示页
        void onPhotoClicked(ImageItem photo, ImageView imageView);

        //有图，长按执行删除相关操作
        void onLongClicked();
    }

    private OnItemClickedListener listener;

    public void setListener(OnItemClickedListener listener) {
        this.listener = listener;
    }
}
