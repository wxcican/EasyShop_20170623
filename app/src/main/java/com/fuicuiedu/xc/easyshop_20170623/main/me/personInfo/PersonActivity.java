package com.fuicuiedu.xc.easyshop_20170623.main.me.personInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.feicuiedu.apphx.model.HxUserManager;
import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.components.AvatarLoadOptions;
import com.fuicuiedu.xc.easyshop_20170623.components.PicWindow;
import com.fuicuiedu.xc.easyshop_20170623.components.ProgressDialogFragment;
import com.fuicuiedu.xc.easyshop_20170623.main.MainActivity;
import com.fuicuiedu.xc.easyshop_20170623.model.CachePreferences;
import com.fuicuiedu.xc.easyshop_20170623.model.ItemShow;
import com.fuicuiedu.xc.easyshop_20170623.model.User;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends MvpActivity<PersonView, PersonPresenter> implements PersonView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;//用户头像
    @BindView(R.id.listView)
    ListView listView;//显示用户名，昵称，环信ID的listView

    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;
    private List<ItemShow> list = new ArrayList<>();
    //适配器
    private PersonAdapter adapter;
    private PicWindow picWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new PersonAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        //获取用户头像
        updataAvatar(CachePreferences.getUser().getHead_Image());
    }

    @NonNull
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //方便修改完昵称，回来更改数据
    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        init();//数据初始化
        adapter.notifyDataSetChanged();
    }

    private void init() {
        User user = CachePreferences.getUser();
        list.add(new ItemShow("用户名",user.getName()));
        list.add(new ItemShow("昵称",user.getNick_name()));
        list.add(new ItemShow("环信Id",user.getHx_Id()));
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                //用户名
                case 0:
                    activityUtils.showToast(getResources().getString(R.string.username_update));
                    break;
                //昵称
                case 1:
                    //昵称页面
                    activityUtils.startActivity(NickNameActivity.class);
                    break;
                //环信ID
                case 2:
                    activityUtils.showToast(getResources().getString(R.string.id_update));
                    break;
            }
        }
    };

    @OnClick({R.id.btn_login_out,R.id.iv_user_head})
    public void onClick(View view){
        switch (view.getId()){
            //点击头像
            case R.id.iv_user_head:
                //头像来源选择（相册，相机）
                if (picWindow == null){
                    picWindow = new PicWindow(this,listener);
                }
                if (picWindow.isShowing()){
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();
                break;
            //点击退出登录
            case R.id.btn_login_out:
                //清空本地配置
                CachePreferences.clearAllData();
                //清楚所有旧的Activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //退出环信相关
                HxUserManager.getInstance().asyncLogout();
                //登出关掉通知栏中的通知
                EaseUI.getInstance().getNotifier().reset();
                break;
        }
    }

    //图片来源选择弹窗的监听
    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //从相册中选择
            //清空裁剪的缓存
            CropHelper.clearCachedCropFile(handler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(handler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            //从相机中选择
            activityUtils.showToast("从相机中选择");
            CropHelper.clearCachedCropFile(handler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(handler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);
        }
    };

    //图片裁剪的handler
    private CropHandler handler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            //图片裁剪结束后
            //通过uri拿到图片文件
            File file = new File(uri.getPath());
            //业务：上传头像
            presenter.updataAvatar(file);
        }

        @Override
        public void onCropCancel() {
            //停止裁剪触发
        }

        @Override
        public void onCropFailed(String message) {
            //裁剪失败
        }

        @Override
        public CropParams getCropParams() {
            //设置裁剪参数
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonActivity.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CropHelper帮助我们去处理结果（裁剪完成的图像）
        CropHelper.handleResult(handler,requestCode, resultCode, data);
    }

    //  #######################   视图接口相关  #####################
    @Override
    public void showPrb() {
        if (dialogFragment == null) dialogFragment = new ProgressDialogFragment();
        if (dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(), "progress_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updataAvatar(String url) {
        //头像加载操作
        ImageLoader.getInstance()
                //头像路径（服务器）,头像显示控件,加载选项
                .displayImage(EasyShopApi.IMAGE_URL + url,ivUserHead,
                        AvatarLoadOptions.build());
    }
}
