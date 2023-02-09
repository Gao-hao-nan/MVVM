package com.ghn.cocknovel.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *
 * @author: 浩楠 2023/1/2/13:55
 */
open class FragmentAdapter(list:List<Fragment>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val mFragments: List<Fragment> = list
    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }

}