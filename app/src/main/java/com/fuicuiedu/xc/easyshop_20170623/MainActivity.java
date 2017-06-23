package com.fuicuiedu.xc.easyshop_20170623;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)ViewPager viewPager;

    private ActivityUtils activityUtils;
    //点击2次返回，退出应用程序
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);
    }

    //如何知道用户点击了返回按钮？返回按钮点击监听
    @Override
    public void onBackPressed() {
        //点击2次返回，退出应用程序
        if (!isExit){
            isExit = true;
            activityUtils.showToast("再摁一次退出程序！");
            //如果2s内再次点击返回则退出（如果2s内不点击返回，则设置false）
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }else{
            finish();
        }
    }
}


