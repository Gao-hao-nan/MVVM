package com.example.basemodel.base.baseact

import android.view.Gravity
import androidx.databinding.ViewDataBinding
import com.example.basemodel.base.basevm.BaseViewModel
import com.kt.NetworkModel.utils.ToastUtils


/**
 * @author 浩楠
 * @date 2025/5/22 14:44
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO 添加 Toast 支持（自定义样式）
 */
abstract class BaseToastActivity<V : ViewDataBinding, VM : BaseViewModel> :
    BaseLoadingActivity<V, VM>() {

    private var toast: ToastUtils? = null

    override fun registerUIObservers() {
        super.registerUIObservers()
        mViewModel.uc.toastEvent().observe(this) { showMsg(it.toString()) }
    }

    protected fun showMsg(msg: String) {
        toast = ToastUtils(this)
        toast?.apply {
            InitToast()
            setText(msg)
            setGravity(Gravity.CENTER)
            show()
        }
    }

    protected fun showMsgWithImage(msg: String, iconRes: Int) {
        toast = ToastUtils(this)
        toast?.apply {
            InitToast()
            setText(msg)
            setImage(iconRes)
            setGravity(Gravity.CENTER)
            show()
        }
    }
}
