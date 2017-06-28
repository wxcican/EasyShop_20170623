package com.fuicuiedu.xc.easyshop_20170623.user.register;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.commons.RegexUtils;
import com.fuicuiedu.xc.easyshop_20170623.components.AlertDialogFragment;
import com.fuicuiedu.xc.easyshop_20170623.components.ProgressDialogFragment;
import com.fuicuiedu.xc.easyshop_20170623.main.MainActivity;
import com.fuicuiedu.xc.easyshop_20170623.model.UserResult;
import com.fuicuiedu.xc.easyshop_20170623.network.EasyShopClient;
import com.fuicuiedu.xc.easyshop_20170623.network.UICallBack;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter>
    implements RegisterView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_userName;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwdAgain)
    EditText et_pwdAgain;
    @BindView(R.id.btn_register)
    Button btn_register;

    private String username;
    private String password;
    private String pwd_again;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        init();
    }

    //创建业务类
    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    private void init() {
        //给左上角加上返回图标，需要重写菜单点击事件，否则点击无效
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_userName.addTextChangedListener(textWathcer);
        et_pwd.addTextChangedListener(textWathcer);
        et_pwdAgain.addTextChangedListener(textWathcer);
    }

    //给左上角加上返回图标，需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWathcer = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = et_userName.getText().toString();
            password = et_pwd.getText().toString();
            pwd_again = et_pwdAgain.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(pwd_again));
            btn_register.setEnabled(canLogin);
        }
    };

    @OnClick(R.id.btn_register)
    public void onClcik() {
        //使用正则表达式限制用户名及密码
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.username_rules);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            activityUtils.showToast(R.string.password_rules);
            return;
            //判断两次密码相同
        } else if (!TextUtils.equals(password, pwd_again)) {
            activityUtils.showToast(R.string.username_equal_pwd);
            return;
        }


        //业务：网络请求完成注册
        presenter.register(username,password);
    }

    // #############################    视图接口中内容    #####################
    @Override
    public void showPrb() {
        //关闭软键盘
        activityUtils.hideSoftKeyboard();
        //进度条
        //初始化进度条
        if (dialogFragment == null)dialogFragment = new ProgressDialogFragment();
        //如果进度条已经显示，则跳出
        if(dialogFragment.isVisible()) return;
        //否则进度条显示
        dialogFragment.show(getSupportFragmentManager(),"dialogFragment");
    }

    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override
    public void registerSuccess() {
        //成功跳转到主页
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void registerFailed() {
        et_userName.setText("");
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showUserPasswordError(String msg) {
        //展示弹窗，提示错误信息
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(),getString(R.string.username_pwd_rule));
    }
}
