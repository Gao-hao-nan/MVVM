package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import android.text.TextUtils
import com.example.basemodel.base.BaseActivity
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityStartBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.kt.network.utils.RandomverificationCode
import kotlinx.android.synthetic.main.activity_start.iv_code

class StartActivity : BaseActivity<ActivityStartBinding, BookStoreViewModel>() {
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
       return R.layout.activity_start
    }

    override fun initParam() {
        binding?.ivCode?.setImageBitmap(RandomverificationCode.instance?.createBitmap())
        binding?.ivCode?.setOnClickListener {
            iv_code.setImageBitmap(RandomverificationCode.instance?.createBitmap())
        }
        binding?.btSignIn?.setOnClickListener {
            if (!TextUtils.isEmpty(binding?.startEdtextName?.text.toString()) && !TextUtils.isEmpty(binding?.startEdtextPassword?.text.toString())){
                viewModel?.getMain(binding?.startEdtextName?.text.toString(),binding?.startEdtextPassword?.text.toString())
            }else{
                showMsg("账号密码不能为空")
            }
        }
    }
}