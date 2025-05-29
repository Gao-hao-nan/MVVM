package com.example.basemodel.base.basefra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.basemodel.base.basevm.BaseViewModel
import com.example.basemodel.base.baseint.IBaseView
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.components.support.RxFragment
import java.lang.reflect.ParameterizedType
/**
 * @author 浩楠
 * @date 2025/5/22 15:36
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO TODO 最底层基类 绑定 ViewModel + DataBinding
 */
abstract class BaseCoreFragment<V : ViewDataBinding, VM : BaseViewModel> :
    RxFragment(), IBaseView {

    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM

    open var viewModelId: Int = 0
    private var isFirst: Boolean = true

    abstract fun initVariableId(): Int
    abstract fun initContentView(inflater: LayoutInflater, container: ViewGroup?): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding(inflater, container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        lifecycle.addObserver(this)
    }

    override fun onResume() {
        super<RxFragment>.onResume()
        if (isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container), container, false)
        mBinding.lifecycleOwner = this
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        viewModelId = initVariableId()

        val modelClass = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.get(1) as? Class<VM> ?: BaseViewModel::class.java as Class<VM>

        mViewModel = ViewModelProvider(this)[modelClass]
        mBinding.setVariable(viewModelId, mViewModel)
        mViewModel.injectLifecycleProvider(this as LifecycleProvider<*>)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.unbind()
    }
//    /** 懒加载：首次可见时触发 */
    open fun lazyLoadData() {}
}
