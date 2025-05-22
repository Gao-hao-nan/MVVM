package com.example.basemodel.base.baseact

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.example.basemodel.base.BaseViewModel
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType

/**
 * @author 浩楠
 * @date 2025/5/22 14:40
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO 最底层基类 绑定 ViewModel + DataBinding
 */
abstract class BaseCoreActivity<V : ViewDataBinding, VM : BaseViewModel> :
    RxAppCompatActivity(), LifecycleObserver {

    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM

    abstract fun initVariableId(): Int
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(savedInstanceState)
        initViewModel()
        lifecycle.addObserver(mViewModel)
    }

    private fun initBinding(savedInstanceState: Bundle?) {
        mBinding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))
        mBinding.lifecycleOwner = this
    }

    private fun initViewModel() {
        val modelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        mViewModel = ViewModelProvider(this)[modelClass]
        mBinding.setVariable(initVariableId(), mViewModel)
        mViewModel.injectLifecycleProvider(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}
