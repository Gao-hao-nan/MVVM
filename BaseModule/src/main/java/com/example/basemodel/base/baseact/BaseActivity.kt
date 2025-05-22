package com.example.basemodel.base.baseact

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.basemodel.R
import com.example.basemodel.base.BaseViewModel
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
    BaseToastActivity<V, VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initParam()             // 参数、Fragment等初始化
        initView()              // UI视图、adapter等初始化
        initViewObservable()    // LiveData、Flow 绑定
        initData()              // 加载数据（网络、本地）

        if (isDebuggable(this)) {
            enableDebugWindowIfNeeded(mViewModel)
        }
    }

    /**
     * 初始化传参、Fragment列表等
     */
    open fun initParam() {}

    /**
     * 初始化视图，如 RecyclerView、按钮监听等
     */
    open fun initView() {}

    /**
     * 初始化数据，如请求网络、加载缓存等
     */
    open fun initData() {}

    /**
     * 初始化 ViewModel → View 的观察者（LiveData/Flow）
     */
    open fun initViewObservable() {}

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


