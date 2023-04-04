package com.ghn.cocknovel.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.lifecycle.Observer
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
        viewModel?.getwan()
        viewModel?.loginStatus?.observe(this, Observer {
            binding?.tv?.text=it.toString()
            Log.i("TAG", "initViewObservable: $it")
        })
    }

}