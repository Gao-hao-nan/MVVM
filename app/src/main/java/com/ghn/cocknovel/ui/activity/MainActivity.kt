package com.ghn.cocknovel.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityMainBinding
import com.ghn.cocknovel.ui.adapter.FragmentAdapter
import com.ghn.cocknovel.ui.fragment.BookshelfFragment
import com.ghn.cocknovel.ui.fragment.BookstoreFragment
import com.ghn.cocknovel.ui.fragment.ClassificationFragment
import com.ghn.cocknovel.ui.fragment.MineFragment
import com.kt.network.base.BaseActivity
import com.kt.network.base.BaseViewModel
import com.kt.network.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.thread

class MainActivity : BaseActivity<ActivityMainBinding,BaseViewModel>() {

    override fun initVariableId(): Int {
        return BR._all
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initParam() {
        LoadingDialog.getInstance(this)?.show()
        val fragments = mutableListOf<Fragment>()
        fragments.add(BookstoreFragment())
        fragments.add(ClassificationFragment())
        fragments.add(BookshelfFragment())
        fragments.add(MineFragment())
        val fragmentAdapter = FragmentAdapter(fragments, supportFragmentManager)
        view_pager?.adapter=fragmentAdapter
        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.bookstore -> view_pager?.currentItem=0
                R.id.classification ->view_pager?.currentItem=1
                R.id.bookshelf ->view_pager?.currentItem=2
                R.id.mine ->view_pager?.currentItem=3
            }
            false
        }
        nav_view.itemIconTintList = null
        view_pager.setCanSwipe(false);
        //page滑动监听（下面）
        view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                nav_view.menu.getItem(position).isChecked=true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}