package com.ghn.cocknovel.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author 浩楠
 *
 * @date 2023/1/10   13:27.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */
class TabLayoutAdapter(fm: FragmentManager,  val fragmentlist: List<Fragment>,  val listTitle: List<String>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragmentlist[position]
    }

    override fun getCount(): Int = listTitle.size

    override fun getPageTitle(position: Int): CharSequence {
        return listTitle.get(position)
    }
}