package com.fuicuiedu.xc.easyshop_20170623.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.components.AvatarLoadOptions;
import com.fuicuiedu.xc.easyshop_20170623.main.me.goodsupload.GoodsUpLoadActivity;
import com.fuicuiedu.xc.easyshop_20170623.main.me.personInfo.PersonActivity;
import com.fuicuiedu.xc.easyshop_20170623.main.me.persongoods.PersonGoodsActivity;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopApi;
import com.fuicuiedu.xc.easyshop_20170623.user.login.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class MeFragment extends Fragment{

    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;//用户头像
    @BindView(R.id.tv_login)
    TextView tv_login;//用户名

    private ActivityUtils activityUtils;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_me,container,false);
            activityUtils = new ActivityUtils(this);
            ButterKnife.bind(this,view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //判断是否登录，显示昵称和头像
        if (CachePreferences.getUser().getName() == null)return;//未登录
        //刚注册还没有昵称和头像
        if (CachePreferences.getUser().getNick_name() == null){
            tv_login.setText("请输入昵称");
        }else{
            tv_login.setText(CachePreferences.getUser().getNick_name());
        }
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + CachePreferences.getUser().getHead_Image(),
                        iv_user_head, AvatarLoadOptions.build());

    }

    @OnClick({R.id.iv_user_head,R.id.tv_person_info, R.id.tv_login, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View v){
        //需要判断用户是否登录，从而觉得跳转的位置
        if(CachePreferences.getUser().getName() == null){
            activityUtils.startActivity(LoginActivity.class);
            return;
        }

        switch (v.getId()){
            //个人信息
            case R.id.tv_person_info:
            //头像
            case R.id.iv_user_head:
            //登录
            case R.id.tv_login:
                //跳转到个人信息页面
                activityUtils.startActivity(PersonActivity.class);
                break;
            //我的商品
            case R.id.tv_person_goods:
                //跳转到我的商品页面
                activityUtils.startActivity(PersonGoodsActivity.class);
                break;
            //商品上传
            case R.id.tv_goods_upload:
                //跳转到商品上传页面
                activityUtils.startActivity(GoodsUpLoadActivity.class);
                break;
        }
    }

}
