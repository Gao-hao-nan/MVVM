package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import android.util.Log
import com.example.basemodel.base.BaseActivity
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivitySetBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import kotlinx.android.synthetic.main.activity_set.*


class SetActivity : BaseActivity<ActivitySetBinding, BookStoreViewModel>() {

    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_set
    }

    override fun initParam() {
        titlebar.setOnTitleBarListener(object : OnTitleBarListener {
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