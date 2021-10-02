package com.example.tinder.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.base.BasePersenter;
import com.example.tinder.ui.user.persenter.LoginPersenter;
import com.example.tinder.ui.user.view.LoginView;

public class LoginActivity extends BaseActivity<LoginView, LoginPersenter> implements TextWatcher, View.OnClickListener,LoginView {

    private EditText etName;
    private EditText etPassword;
    private Button btLogin;
    private TextView tvToRegister;

    private String name;
    private String password;

    @Override
    public void initView() {
        etName = findViewById(R.id.et_login_name);
        etPassword = findViewById(R.id.et_login_password);
        btLogin = findViewById(R.id.bt_login);
        tvToRegister = findViewById(R.id.tv_to_register);
        etName.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        btLogin.setOnClickListener(this);
        tvToRegister.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public LoginPersenter createPersenter() {
        return new LoginPersenter();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        name = getEditText(etName);
        password = getEditText(etPassword);
        if(name.length()>0&&password.length()>=6){
            btLogin.setEnabled(true);
            btLogin.setBackgroundResource(R.drawable.bg_btn_register);
        }else {
            btLogin.setEnabled(false);
            btLogin.setBackgroundResource(R.drawable.bg_btn_register_not);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                //TODO 登录接口 保存用户 Toke
                showToast("Login");
                getmPersenter().sendLogin("lzy","1111111");
                break;
            case R.id.tv_to_register:
                showToast("Go To Register");
                startIntent(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(Object data) {
        //渲染UI
        showToast((String)data);
    }
}