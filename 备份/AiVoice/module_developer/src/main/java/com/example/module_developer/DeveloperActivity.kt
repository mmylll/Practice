package com.example.module_developer

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.helper.ARouterHelper

@Route(path = ARouterHelper.PATH_DEVELOPER)
class DeveloperActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_developer
    }

    override fun getTitleText(): String {
        return getString(R.string.app_name)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

    }
}