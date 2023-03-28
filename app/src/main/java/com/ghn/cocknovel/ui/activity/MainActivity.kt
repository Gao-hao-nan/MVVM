package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityMainBinding
import com.ghn.cocknovel.ui.fragment.BookshelfFragment
import com.ghn.cocknovel.ui.fragment.BookstoreFragment
import com.ghn.cocknovel.ui.fragment.ClassificationFragment
import com.ghn.cocknovel.ui.fragment.MineFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView
import com.kt.network.base.BaseActivity
import com.kt.network.base.BaseViewModel


class MainActivity : BaseActivity<ActivityMainBinding,BaseViewModel>() {

    override fun initVariableId(): Int {
        return BR._all
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initParam() {
        val controller: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        binding?.navView?.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED)
        binding?.navView?.let { NavigationUI.setupWithNavController(it,controller) }
    }

    //重写onKeyDown()方法,继承自退出的方法
    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                binding?.container?.let { showMsg(it,"再按一次退出鲸鱼") }
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}