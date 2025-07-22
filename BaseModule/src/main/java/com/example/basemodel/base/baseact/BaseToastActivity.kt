package com.example.basemodel.base.baseact

import androidx.viewbinding.ViewBinding
import com.example.basemodel.base.basevm.BaseViewModel
import com.kt.NetworkModel.ext.showToast


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
abstract class BaseToastActivity<V : ViewBinding, VM : BaseViewModel> :
    BaseLoadingActivity<V, VM>() {

    override fun registerUIObservers() {
        super.registerUIObservers()
        mViewModel.uc.toastEvent().observe(this) { showMsg(it.toString()) }
    }

    protected fun showMsg(msg: String) {
        showToast(msg)
    }

    protected fun showMsgWithImage(msg: String, iconRes: Int) {
        showToast(msg, iconRes = iconRes)
    }
}
