package com.ghn.cocknovel.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.basemodel.base.BaseActivity
import com.example.basemodel.base.BaseViewModel
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityMainBinding
import com.ghn.cocknovel.ui.fragment.BookshelfFragment
import com.ghn.cocknovel.ui.fragment.BookstoreFragment
import com.ghn.cocknovel.ui.fragment.ClassificationFragment
import com.ghn.cocknovel.ui.fragment.MineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var preFragment = 0 // 记录上一个被点击的 fragment页面 ，默认值是0
    private var fragmentList:ArrayList<Fragment>?=null
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    @SuppressLint("CommitTransaction")
    override fun initParam() {
        fragmentList = ArrayList()
        fragmentList?.add(BookstoreFragment())
        fragmentList?.add(ClassificationFragment())
        fragmentList?.add(BookshelfFragment())
        fragmentList?.add(MineFragment())
        //默认显示第一个fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragmentList!!.get(0)).commit();
        binding?.navView?.setOnNavigationItemSelectedListener(this)
        binding?.mainFlWarn?.setOnClickListener {
            binding?.flContent?.visibility=View.GONE
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookstore -> {
                if (preFragment != 0) {
                    switchFragemnt(preFragment, 0)
                    preFragment = 0
                }
                return true
            }
            R.id.classification -> {
                if (preFragment != 1) {
                    switchFragemnt(preFragment, 1)
                    preFragment = 1
                }
                return true
            }
            R.id.bookshelf -> {
                if (preFragment != 2) {
                    switchFragemnt(preFragment, 2)
                    preFragment = 2
                }
                return true
            }
            R.id.mine -> {
                if (preFragment != 3) {
                    switchFragemnt(preFragment, 3)
                    preFragment = 3
                }
                return true
            }
        }
        return false
    }
    private fun switchFragemnt(preFragment: Int, i: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.hide(fragmentList!![preFragment])
        //如果当前被点击的fragment页面还没有被加入到   FragmentManager 里面，则需要添加进来
        if (fragmentList!![i].isAdded == false) {
            ft.add(R.id.fl_content, fragmentList!![i])
        }
        ft.show(fragmentList!![i]).commitAllowingStateLoss()
    }
    //重写onKeyDown()方法,继承自退出的方法
    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                binding?.container?.let { showMsg(it, "再按一次退出鲸鱼阅读") }
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}


