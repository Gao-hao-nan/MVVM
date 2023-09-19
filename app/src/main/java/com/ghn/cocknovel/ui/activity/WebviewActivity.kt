package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import com.example.basemodel.base.BaseActivity
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityWebviewBinding
import com.ghn.cocknovel.viewmodel.RecommendViewModel

class WebviewActivity : BaseActivity<ActivityWebviewBinding, RecommendViewModel>() {
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_webview
    }

    override fun initParam() {
        val bundle = this.intent.extras
        bundle?.getString("url")?.let { binding?.homeWebview?.loadUrl(it) }
        binding?.homeWebview?.canGoBack()
    }
}