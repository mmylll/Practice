package com.example.tinder.main.persenter;

import com.example.tinder.main.view.MainView;
import com.example.tinder.ui.base.BasePersenter;

public class MainPersenter extends BasePersenter<MainView> {
    public void getTest(){
        getmBaseView().setData("Test");
    }
}
