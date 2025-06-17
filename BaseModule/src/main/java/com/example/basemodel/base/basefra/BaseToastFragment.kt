package com.example.basemodel.base.basefra

import android.view.Gravity
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.example.basemodel.base.basevm.BaseViewModel
import com.kt.NetworkModel.utils.ToastUtils


/**
 * @author 浩楠
 * @date 2025/5/22 15:44
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO
 */
abstract class BaseToastFragment<V : ViewBinding, VM : BaseViewModel> :
    BaseDialogFragment<V, VM>() {

    private var toast: ToastUtils? = null

    override fun initViewObservable() {
        super.initViewObservable()

        mViewModel.uc.toastEvent().observe(this) { message ->
            showMsg(message.toString())
        }
    }

    protected fun showMsg(msg: String) {
        toast = ToastUtils(context).apply {
            InitToast()
            setText(msg)
            setGravity(Gravity.CENTER)
            show()
        }
    }

    protected fun showMsgWithImage(msg: String, iconRes: Int) {
        toast = ToastUtils(context).apply {
            InitToast()
            setText(msg)
            setImage(iconRes)
            setGravity(Gravity.CENTER)
            show()
        }
    }
}
