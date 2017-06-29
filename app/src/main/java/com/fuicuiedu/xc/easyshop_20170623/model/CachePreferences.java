package com.fuicuiedu.xc.easyshop_20170623.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */


//对用户信息本地存储
public class CachePreferences {

    private static final String NAME = CachePreferences.class.getSimpleName();
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PWD = "userPwd";
    private static final String KEY_USER_HX_ID = "userHxID";
    private static final String KEY_USER_TABLE_ID = "userUuid";
    private static final String KEY_USER_HEAD_IMAGE = "userHeadImage";
    private static final String KEY_USER_NICKNAME = "userNickName";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private CachePreferences(){}

    //初始化方法，在程序入口进行初始化
    public static void init(Context context){
        preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void clearAllData(){
        editor.clear();
        editor.apply();
    }

    public static void setUser(User user){
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_PWD, user.getPassword());
        editor.putString(KEY_USER_HX_ID, user.getHx_Id());
        editor.putString(KEY_USER_TABLE_ID, user.getTable_Id());
        editor.putString(KEY_USER_HEAD_IMAGE, user.getHead_Image());
        editor.putString(KEY_USER_NICKNAME, user.getNick_name());
        editor.apply();
    }

    public static User getUser(){
        User user = new User();
        user.setName(preferences.getString(KEY_USER_NAME,null));
        user.setPassword(preferences.getString(KEY_USER_PWD, null));
        user.setHx_Id(preferences.getString(KEY_USER_HX_ID, null));
        user.setTable_Id(preferences.getString(KEY_USER_TABLE_ID, null));
        user.setHead_Image(preferences.getString(KEY_USER_HEAD_IMAGE, null));
        user.setNick_name(preferences.getString(KEY_USER_NICKNAME, null));
        return user;
    }
}
