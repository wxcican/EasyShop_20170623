package com.fuicuiedu.xc.easyshop_20170623.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.commons.CurrentUser;
import com.fuicuiedu.xc.easyshop_20170623.main.MainActivity;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.hyphenate.easeui.domain.EaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityUtils = new ActivityUtils(this);

        EventBus.getDefault().register(this);


        //环信登录相关（账号冲突踢出）
        if (getIntent().getBooleanExtra("AUTO_LOGIN",false)){
            //清除本地配置
            CachePreferences.clearAllData();
            //踢出时，退出环信
            HxUserManager.getInstance().asyncLogout();
        }

        //判断用户是否登录，自动登录
        if (CachePreferences.getUser().getName() != null  && !HxUserManager.getInstance().isLogin()){
            User user = CachePreferences.getUser();
            EaseUser easeUser = CurrentUser.convert(user);
            HxUserManager.getInstance().asyncLogin(easeUser,user.getPassword());
            return;
        }


        //1.5s跳转到主页
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //跳转到主页
                activityUtils.startActivity(MainActivity.class);
                finish();
            }
        }, 1500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //判断登录是否成功
        if (event.type != HxEventType.LOGIN) return;
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        //判断登录是否失败
        if (event.type != HxEventType.LOGIN) return;
        throw new RuntimeException("loing error");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
