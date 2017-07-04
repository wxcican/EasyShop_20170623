package com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fuicuiedu.xc.easyshop_20170623.model.ImageItem;

import java.util.ArrayList;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class GoodsUpLoadAdapter extends RecyclerView.Adapter{

    //适配器的数据
    private ArrayList<ImageItem> list = new ArrayList<>();
    private LayoutInflater inflater;

    public GoodsUpLoadAdapter(Context context,ArrayList<ImageItem> list){
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
    public enum ITEM_TYPE{
        ITEM_NORMAL,ITEM_ADD
    }

    //模式设置
    public void changeMode(int mode){
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
    public void add(ImageItem imageItem){
        list.add(imageItem);
    }

    //获取数据数量
    public int getSize(){
        return list.size();
    }

    //获取数据
    public ArrayList<ImageItem> getList(){
        return list;
    }

    //刷新数据
    public void notifyData(){
        notifyDataSetChanged();
    }
    // ########################### 外部调用的相关方法 end #######################

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // #############################  item点击事件(接口回调)   #######################
    public interface OnItemClickedListener{

        //无图，点击添加图片
        void onAddClicked();
        //有图，点击跳转到图片展示页
        void onPhotoClicked(ImageItem photo, ImageView imageView);
        //有图，长按执行删除相关操作
        void onLongClicked();
    }

    private OnItemClickedListener listener;

    public void setListener(OnItemClickedListener listener){
        this.listener = listener;
    }
}
