package com.example.tinder.ui.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public abstract class BaseActivity<V extends BaseView,P extends BasePersenter> extends AppCompatActivity implements BaseView {

    private P mPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //改状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            View view = window.getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if(mPersenter == null){
            mPersenter = createPersenter();
            mPersenter.bindView(this);
        }
        initView();
        initData();
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract P createPersenter();

    public String getEditText(EditText et){
        return et.getText().toString().trim();//去空格
    }

    public void showToast(String str) {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    public void startIntent(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
    }

    public P getmPersenter() {
        return mPersenter;
    }
}