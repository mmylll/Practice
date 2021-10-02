package com.example.aivoice
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.helper.ARouterHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getTitleText(): String {
        return getString(R.string.app_name)
    }

    override fun initView() {

        button.setOnClickListener {
            ARouterHelper.startActivity(ARouterHelper.PATH_APP_MANAGER)
        }
    }
    override fun isShowBack(): Boolean {
        return false
    }

}