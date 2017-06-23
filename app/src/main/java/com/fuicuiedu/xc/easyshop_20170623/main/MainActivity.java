package com.fuicuiedu.xc.easyshop_20170623.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.main.me.MeFragment;
import com.fuicuiedu.xc.easyshop_20170623.main.shop.ShopFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    TextView[] textViews;
    @BindView(R.id.main_toobar)
    Toolbar toolbar;
    @BindView(R.id.main_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private ActivityUtils activityUtils;
    //点击2次返回，退出应用程序
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        //初始化数据
        init();
    }

    private void init() {
        //设置适配器
        viewPager.setAdapter(unLoginAdapter);

        //刚进来默认选择市场页面
        textViews[0].setSelected(true);

        //控制TextView的图标，通过ViewPager滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //textView全部未选择
                for (TextView textView : textViews){
                    textView.setSelected(false);
                }

                //更改title，设置选择效果
                tv_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //todo 用户已经登录时的适配器

    //用户未登录时的适配器(ctrl + p 查看所需参数)
    private FragmentStatePagerAdapter unLoginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                //市场
                case 0:
                    return new ShopFragment();
                    //消息
                case 1:
                    return new UnLoginFragment();
                    //通讯录
                case 2:
                    return new UnLoginFragment();
                    //我的
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            //确定4个页面，写死为4
            return 4;
        }
    };


    //textView点击事件
    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    public void onClick(TextView textView){
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }

        //设置选择效果
        textView.setSelected(true);
        //不要平滑效果，第二个参数设置为false
        viewPager.setCurrentItem((int)textView.getTag(),false);
        tv_title.setText(textViews[(int)textView.getTag()].getText());
    }

    //如何知道用户点击了返回按钮？返回按钮点击监听
    @Override
    public void onBackPressed() {
        //点击2次返回，退出应用程序
        if (!isExit) {
            isExit = true;
            activityUtils.showToast("再摁一次退出程序！");
            //如果2s内再次点击返回则退出（如果2s内不点击返回，则设置false）
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }
}


