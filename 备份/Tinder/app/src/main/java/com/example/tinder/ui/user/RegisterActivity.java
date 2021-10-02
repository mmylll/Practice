package com.example.tinder.ui.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinder.R;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.base.BasePersenter;
import com.example.tinder.ui.user.persenter.RegisterPersenter;
import com.example.tinder.ui.user.view.RegisterView;

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPersenter> implements TextWatcher,View.OnClickListener,RegisterView {

    private ImageView ivPhoto;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btRegister;
    private TextView tvGoLogin;

    private String name;
    private String email;
    private String password;

    @Override
    public void initView() {
        ivPhoto = findViewById(R.id.iv_photo);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btRegister = findViewById(R.id.bt_register);
        tvGoLogin = findViewById(R.id.tv_goLogin);

        etName.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        btRegister.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    public void initData() {
    }

    @Override
    public RegisterPersenter createPersenter() {
        return new RegisterPersenter();
    }

    private void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                ivPhoto.setImageURI(uri);
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_goLogin:
                showToast("Go To Login");
                startIntent(LoginActivity.class);
                //TODO 调用注册接口
                break;
            case R.id.bt_register:
                showToast("Register");
                //TODO 调用登录接口
                break;
            case R.id.iv_photo:
                selectPhoto();
                break;
            default:
                break;


        }
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
        email = getEditText(etEmail);
        password = getEditText(etPassword);
        if(name.length()>0&&email.length()>0&&password.length()>=6){
            btRegister.setEnabled(true);
            btRegister.setBackgroundResource(R.drawable.bg_btn_register);
        }else {
            btRegister.setEnabled(false);
            btRegister.setBackgroundResource(R.drawable.bg_btn_register_not);
        }
    }

    @Override
    public void setData(Object data) {

    }
}