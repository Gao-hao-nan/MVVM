package com.example.basemodel.base.baseact

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.basemodel.R
import com.example.basemodel.base.basevm.BaseViewModel
import com.example.basemodel.base.baseint.IBaseView
import com.hjq.window.EasyWindow

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
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> :
    BaseToastActivity<V, VM>(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseToastActivity>.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
        if (isDebuggable(this)) {
            enableDebugWindowIfNeeded(mViewModel)
        }
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

    protected fun enableDebugWindowIfNeeded(viewModel: BaseViewModel) {
        EasyWindow.with(this).apply {
            setContentView(R.layout.flw)
            setDraggable()
            setXOffset(500)
            setOutsideTouchable(true)
            setOnClickListener(R.id.TvFlw) { _, _ ->
                val launchIntent =
                    "cn.coderpig.cp_network_capture.ui.activity.NetworkCaptureActivity"
                viewModel.startModelActivity(packageName, launchIntent)
            }
        }.show()
    }
}


