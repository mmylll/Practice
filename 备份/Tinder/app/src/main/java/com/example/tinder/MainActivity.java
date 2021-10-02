package com.example.tinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.tinder.main.persenter.MainPersenter;
import com.example.tinder.main.view.MainView;
import com.example.tinder.ui.base.BaseActivity;
import com.example.tinder.ui.base.BasePersenter;
import com.example.tinder.ui.base.BaseView;

public class MainActivity extends BaseActivity<MainView, MainPersenter> implements MainView{


    @Override
    public void initView() {
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmPersenter().getTest();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public MainPersenter createPersenter() {
        return new MainPersenter();
    }

    @Override
    public void setData(Object data) {
        showToast((String) data);
    }
}