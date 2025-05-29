package com.ghn.cocknovel.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basemodel.base.basefra.BaseFragment
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentBoyBinding
import com.ghn.cocknovel.viewmodel.RecommendViewModel


class BoyFragment : BaseFragment<FragmentBoyBinding, RecommendViewModel>(){
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?): Int =R.layout.fragment_boy


    override fun initParam() {

    }

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initViewObservable() {
    }
}