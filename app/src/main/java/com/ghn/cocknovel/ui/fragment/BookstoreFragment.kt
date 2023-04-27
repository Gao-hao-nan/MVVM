package com.ghn.cocknovel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basemodel.base.BaseFragment
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentBookstoreBinding
import com.ghn.cocknovel.ui.adapter.TabLayoutAdapter
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.fragment_bookstore.*


class BookstoreFragment : BaseFragment<FragmentBookstoreBinding, BookStoreViewModel>() {
    private val fragmentList = ArrayList<Fragment>()
    var titles = ArrayList<String>()
    override fun initVariableId(): Int {
        return BR.model
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_bookstore
    }

    override fun initParam() {

    }

    override fun initViewObservable() {
        fragmentList.clear()
        titles.clear()
        fragmentList.add(RecommendFragment())
        fragmentList.add(BoyFragment())
        fragmentList.add(GirlFragment())
        titles.add("推荐")
        titles.add("男生")
        titles.add("女生")
        viewpage.adapter=
            activity?.let { TabLayoutAdapter(it.supportFragmentManager,fragmentList,titles) }
        main_tab.setupWithViewPager(viewpage)
        main_tab.getTabAt(0)?.view?.scaleX=1.5f
        main_tab.getTabAt(0)?.view?.scaleY=1.5f
        main_tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                (tab.view as View).setScaleX(1.5f)
                (tab.view as View).setScaleY(1.5f)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.view as View).scaleX = 0.8f
                (tab.view as View).scaleY = 0.8f
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                (tab.view as View).scaleX = 1.5f
                (tab.view as View).scaleY = 1.5f
            }
        })
    }
}