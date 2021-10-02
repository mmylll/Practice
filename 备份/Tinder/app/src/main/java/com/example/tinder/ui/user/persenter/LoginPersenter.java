package com.example.tinder.ui.user.persenter;

import com.example.tinder.ui.base.BasePersenter;
import com.example.tinder.ui.user.view.LoginView;

public class LoginPersenter extends BasePersenter<LoginView> {
    //处理业务 请求接口给model
    public void sendLogin(String name,String password){
        getmBaseView().setData(name+"<---------->"+password);
    }

}
