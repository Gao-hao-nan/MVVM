package com.ghn.cocknovel.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivitySwitchBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.kt.network.base.BaseActivity

class SwitchActivity : BaseActivity<ActivitySwitchBinding, BookStoreViewModel>() {


    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_switch
    }

    override fun initParam() {
        binding?.titleBarSwtich?.setOnTitleBarListener(object : OnTitleBarListener{
            override fun onLeftClick(titleBar: TitleBar) {
                finish()

            }

            override fun onTitleClick(titleBar: TitleBar) {

            }

            override fun onRightClick(titleBar: TitleBar) {

            }
        })

    }
}