package com.ghn.cocknovel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentRecommendBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.kt.network.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : BaseFragment<FragmentRecommendBinding,BookStoreViewModel>() {

    override fun initVariableId(): Int {
        return BR.model
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_recommend
    }

    override fun initParam() {

    }

    override fun initViewObservable() {
//        webview.loadUrl("https://www.biquzge.com/")
//        val websetting: WebSettings = webview.settings
//        websetting.setCacheMode(WebSettings.LOAD_NO_CACHE)//不使用缓存，只从网络获取数据.
//        //支持屏幕缩放
//        websetting.setSupportZoom(true)
//        websetting.setBuiltInZoomControls(true)

    }

}