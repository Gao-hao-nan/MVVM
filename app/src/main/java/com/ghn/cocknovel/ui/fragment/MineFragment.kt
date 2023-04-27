package com.ghn.cocknovel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basemodel.base.BaseFragment
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentMineBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import java.util.*


class MineFragment : BaseFragment<FragmentMineBinding, BookStoreViewModel>() {
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_mine
    }

    override fun initParam() {

    }

    override fun initViewObservable() {

    }

}