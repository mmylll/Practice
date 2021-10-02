package com.example.lib_base.utils

import android.content.Context
import android.content.SharedPreferences
//SP封装
object SpUtils {

    private const val SP_NAME = "config"

    //对象
    private lateinit var sp: SharedPreferences
    private lateinit var spEdit: SharedPreferences.Editor

    //初始化
    fun initUtils(mContext: Context) {
        sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        spEdit = sp.edit()
        spEdit.apply()
    }

    fun putString(key: String, value: String) {
        spEdit.putString(key, value)
        spEdit.commit()
    }

    fun getString(key: String): String? {
        return sp.getString(key, "")
    }

    fun putInt(key: String, value: Int) {
        spEdit.putInt(key, value)
        spEdit.commit()
    }

    fun getInt(key: String, defValue: Int): Int? {
        return sp.getInt(key, defValue)
    }
}