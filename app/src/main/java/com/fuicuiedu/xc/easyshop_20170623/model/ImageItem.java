package com.fuicuiedu.xc.easyshop_20170623.model;

import android.graphics.Bitmap;

import com.fuicuiedu.xc.easyshop_20170623.commons.Bimp;

import java.io.IOException;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//图片上传时，item布局对应的实体类
public class ImageItem {
    //图片路径
    public String imagePath;
    //图片选中状态，默认为false
    private boolean isCheck = false;
    //根据图片路径获取BitMap
    private Bitmap bitmap;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Bitmap getBitmap() {
        if (bitmap == null){
            try {
                bitmap = Bimp.revisionImageSize(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
