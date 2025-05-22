package com.ghn.cocknovel.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.basemodel.base.baseact.BaseActivity
import com.example.basemodel.base.BaseViewModel
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.ActivityMainBinding
import com.ghn.cocknovel.ui.fragment.BookshelfFragment
import com.ghn.cocknovel.ui.fragment.BookstoreFragment
import com.ghn.cocknovel.ui.fragment.ClassificationFragment
import com.ghn.cocknovel.ui.fragment.MineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.kt.NetworkModel.utils.MVUtils


class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var preFragment = 0 // 记录上一个被点击的 fragment页面 ，默认值是0
    private var fragmentList: ArrayList<Fragment>? = null
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    @SuppressLint("CommitTransaction")
    override fun initParam() {
        Log.i("TAG", "initParam: ${MVUtils.getInt("token")}")
        fragmentList = ArrayList()
        fragmentList?.add(BookstoreFragment())
        fragmentList?.add(ClassificationFragment())
        fragmentList?.add(BookshelfFragment())
        fragmentList?.add(MineFragment())
        //默认显示第一个fragment
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fl_content, fragmentList!!.get(0)).commit();
        mBinding.navView.setOnNavigationItemSelectedListener(this)
        mBinding.mainFlWarn.setOnClickListener {
            mBinding.mainFlWarn.visibility = View.GONE
        }
        XXPermissions.with(this).permission(Permission.CAMERA)
            .permission(Permission.READ_MEDIA_IMAGES)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        showMsg("获取部分权限成功，但部分权限未正常授予")
                        return
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        showMsg("被永久拒绝授权，请手动授权")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(this@MainActivity, permissions)
                    } else {
                        showMsg("获取权限失败")
                    }
                }
            })
        showMsgWithImage("提示", com.example.basemodel.R.mipmap.ic_my_handes)

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
                mBinding.container.let { showMsg("再按一次退出鲸鱼阅读") }
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}


