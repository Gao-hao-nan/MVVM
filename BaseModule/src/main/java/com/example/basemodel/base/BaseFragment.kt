package com.example.basemodel.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.basemodel.R
import com.kt.NetworkModel.utils.ToastUtils
import com.kt.network.dialog.LoadingDialog
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.ParameterizedType

/**
 * @author 浩楠
 *
 * @date 2023/6/4-17:23
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 封装一个BaseFragment
 */
@AndroidEntryPoint
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> :RxFragment(), IBaseView{
    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM
    open var viewModelId = 0
    var dialog: MaterialDialog? = null
    private var toast: ToastUtils? = null

    //是否第一次加载
    private var isFirst: Boolean = true

    @Deprecated("Deprecated in Java")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParam()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = (DataBindingUtil.inflate<ViewDataBinding>
            (inflater, initContentView(inflater, container, savedInstanceState), container, false) as V)
        return mBinding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()
        lifecycle.addObserver(mViewModel)
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    private fun registerUIChangeLiveDataCallBack() {
        //跳入新页面
        mViewModel.getUC()?.getStartActivityEvent()?.observe(viewLifecycleOwner) { params ->
            params?.let {
                val clz = params[BaseViewModel.Companion.ParameterField.CLASS] as Class<*>?
                val intent = Intent(activity, clz)
//            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                val bundle = params[BaseViewModel.Companion.ParameterField.BUNDLE]
                if (bundle is Bundle) {
                    intent.putExtras((bundle as Bundle?)!!)
                }

                this@BaseFragment.startActivityForResult(
                    intent,
                    params[BaseViewModel.Companion.ParameterField.REQUEST] as Int
                )
            }

        }
        //包名和类名跳转
        mViewModel.getUC()?.getStartModelActivityEvent()?.observe(viewLifecycleOwner){ params->
            params?.let {
                val clz=params[BaseViewModel.Companion.ParameterField.CLASS]
                val Packagename=params[BaseViewModel.Companion.ParameterField.CANONICAL_NAME]
                val intent=Intent()
                intent.setClassName(Packagename.toString(), clz.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this@BaseFragment.startActivity(intent)
            }
        }

        mViewModel.getUC()?.getFinishResult()?.observe(viewLifecycleOwner) { integer ->
            integer?.let {
                activity?.setResult(integer)
                activity?.finish()
            }
        }

        mViewModel.getUC()?.getShowDialog()?.observe(viewLifecycleOwner) {
            ShowDialog()
        }
        mViewModel.getUC()?.getDismissDialog()?.observe(viewLifecycleOwner) {
            dismissLoading()
        }

        //关闭界面
//        viewModel?.getUC()?.getFinishEvent()?.observe(this) { activity?.finish() }
        //关闭上一层

        mViewModel.getUC()?.getOnBackPressedEvent()?.observe(viewLifecycleOwner) { activity?.onBackPressed() }

        mViewModel.getUC()?.getSetResultEvent()?.observe(viewLifecycleOwner) { params ->
            params?.let {
                val intent = Intent()
                if (params.isNotEmpty()) {
                    val strings: Set<String> = params.keys
                    for (string in strings) {
                        intent.putExtra(string, params[string])
                    }
                }
                activity?.setResult(RxAppCompatActivity.RESULT_OK, intent)
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViewDataBinding() {
        viewModelId = initVariableId()


        viewModelId = initVariableId()
        val modelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }

        mViewModel = createViewModel(this, modelClass as Class<VM>)
        //关联ViewModel
        mBinding.setVariable(viewModelId, mViewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.lifecycleOwner = this
        mBinding.lifecycleOwner?.lifecycle?.addObserver(mViewModel!!)
        //让ViewModel拥有View的生命周期感应
//        lifecycle.addObserver(viewModel!!)
        //注入RxLifecycle生命周期
        mViewModel.injectLifecycleProvider(this)
    }

    open fun <T : ViewModel> createViewModel(fragment: Fragment?, cls: Class<T>?): T {
        return ViewModelProvider(fragment!!)[cls!!]
    }

    /**
     * 打开等待框
     */
    private fun ShowDialog() {
        if (dialog == null) {
            dialog = context?.let {
                MaterialDialog(it)
                    .cancelable(false)
                    .cornerRadius(8f)
                    .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
                    .lifecycleOwner(this)
                    .maxWidth(R.dimen.dialog_width)
            }
        }
        dialog?.show()
//        if (dialog == null) {
//            dialog = LoadingDialog.getInstance(activity)
//        }
//        dialog?.show()
    }

    /**
     * 打开等待框
     */
    private fun dismissLoading() {
        dialog?.run {
            if (isShowing) dismiss()
        }
    }


    /**
     * 返回variableid
     */
    abstract fun initVariableId(): Int



    /**
     * 自定义Toast文字
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun showMsg(msg: String) {
        toast=ToastUtils(context)
        toast?.InitToast()
        toast?.setText(msg)
        toast?.setGravity(Gravity.CENTER)
        toast?.show()
    }
    /**
     * 自定义Toast图片+文字
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun showMsgimage(msg: String, url:Int) {
        toast=ToastUtils(context)
        toast?.InitToast()
        toast?.setText(msg)
        toast?.setImage(url)
        toast?.setGravity(Gravity.CENTER)
        toast?.show()
    }

    /**
     * 返回布局id
     */
    abstract fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int



}