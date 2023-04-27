package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import com.example.basemodel.base.BaseActivity
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityStartBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.kt.network.utils.RandomverificationCode
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity<ActivityStartBinding, BookStoreViewModel>() {
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
       return R.layout.activity_start
    }

    override fun initParam() {
        iv_code.setImageBitmap(RandomverificationCode.instance?.createBitmap())
        iv_code.setOnClickListener {
            iv_code.setImageBitmap(RandomverificationCode.instance?.createBitmap())
        }
    }
}