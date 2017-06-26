package com.fuicuiedu.xc.easyshop_20170623.user.register;

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

import com.fuicuiedu.xc.easyshop_20170623.R;
import com.fuicuiedu.xc.easyshop_20170623.commons.ActivityUtils;
import com.fuicuiedu.xc.easyshop_20170623.commons.RegexUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        init();
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

//        1.创建一个客户端
//        2.构建请求
//                2.1 添加url（服务器地址，接口）
//                2.2 添加请求方式（get，post）
//                2.3 添加请求头（根据服务器要求来添加，通常是不需要的）
//                2.4 添加请求体（可以为空，根据服务器要求）
//        3.客户端发送请求给服务器 ->  拿到响应
//        4.解析响应
//                4.1 判断响应码（是否请求成功，200-299）
//                4.2 响应码 = 200-299   ->      取出响应体（解析，展示）


        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("aaa", "网络连接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("aaa", "网络连接成功");
                if (response.isSuccessful()) {
                    Log.e("aaa", "服务器成功响应");
                } else {
                    Log.e("aaa", "请求失败");
                }
            }
        });


    }
}
