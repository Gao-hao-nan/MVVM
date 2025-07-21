package com.example.basemodel.base.baseact

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.example.basemodel.R
import com.example.basemodel.base.basevm.BaseViewModel
import com.example.basemodel.base.baseint.IBaseView
import com.hjq.window.EasyWindow
import com.therouter.TheRouter

/**
 * @author 浩楠
 *
 * @date 2023/6/4-15:58
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 最终对外暴露的基类
 */
abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> :
    BaseToastActivity<V, VM>(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseToastActivity>.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }


    @SuppressLint("NewApi")
    private fun isDebuggable(context: Context): Boolean {
        return try {
            val info = context.applicationInfo
            info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            false
        }
    }
}


