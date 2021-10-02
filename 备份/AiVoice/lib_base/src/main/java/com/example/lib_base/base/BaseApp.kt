package com.example.lib_base.base

import android.app.Application
import com.example.lib_base.helper.ARouterHelper
import com.example.lib_base.utils.SpUtils

//基类app
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouterHelper.initHelper(this)
        SpUtils.initUtils(this)
    }
}