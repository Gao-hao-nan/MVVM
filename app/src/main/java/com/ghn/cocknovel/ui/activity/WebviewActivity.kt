package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import android.util.Log
import com.example.basemodel.base.BaseActivity
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityWebviewBinding
import com.ghn.cocknovel.ui.view.MyWebView
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
        //webview加载成功和失败的回调
        mBinding?.homeWebview?.setOnLoadStatueListener(object :MyWebView.OnWebLoadStatusListener{
            override fun error() {
                Log.i("TAG", "error: 失败")
            }

            override fun success() {
                Log.i("TAG", "success: 成功")
            }

            override fun onTitle(title: String) {
                Log.i("TAG", "onTitle: 标题")
            }

        })
        bundle?.getString("url")?.let { mBinding?.homeWebview?.loadUrl(it) }
        mBinding?.homeWebview?.canGoBack()
    }
}