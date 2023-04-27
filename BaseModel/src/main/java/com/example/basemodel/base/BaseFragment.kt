package com.example.basemodel.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjq.xtoast.XToast
import com.kt.ktmvvm.lib.R
import com.kt.network.dialog.LoadingDialog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : RxFragment(), IBaseView {

    open var binding: V? = null
    open var viewModel: VM? = null
    open var viewModelId = 0
    var dialog: LoadingDialog? = null

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
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            initContentView(inflater, container, savedInstanceState),
            container,
            false
        ) as V?
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        viewModel?.let { lifecycle.addObserver(it) }
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding()
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
        viewModel?.getUC()?.getStartActivityEvent()?.observe(this) { params ->
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
        viewModel?.getUC()?.getFinishResult()?.observe(this) { integer ->
            integer?.let {
                activity?.setResult(integer)
                activity?.finish()
            }
        }

        viewModel?.getUC()?.getShowDialog()?.observe(this) {
            ShowDialog()
        }
        viewModel?.getUC()?.getDismissDialog()?.observe(this) {
            dismissLoading()
        }

        //关闭界面
//        viewModel?.getUC()?.getFinishEvent()?.observe(this) { activity?.finish() }
        //关闭上一层

        viewModel?.getUC()?.getOnBackPressedEvent()?.observe(this) { activity?.onBackPressed() }

        viewModel?.getUC()?.getSetResultEvent()?.observe(this) { params ->
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

        viewModel = createViewModel(this, modelClass as Class<VM>)
        //关联ViewModel
        binding?.setVariable(viewModelId, viewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel!!)
        //注入RxLifecycle生命周期
        viewModel?.injectLifecycleProvider(this)
    }

    open fun <T : ViewModel> createViewModel(fragment: Fragment?, cls: Class<T>?): T {
        return ViewModelProvider(fragment!!)[cls!!]
    }

    /**
     * 打开等待框
     */
    private fun ShowDialog() {
        if (dialog == null) {
            dialog = LoadingDialog.getInstance(activity)
        }
        dialog?.show()
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
     * Toast
     */
    fun showMsg(view: View, msg: String) {
        XToast<XToast<*>>(activity).apply {
            setContentView(R.layout.layout_toast)
            setDuration(3000)
            showAsDropDown(view)
            findViewById<TextView>(R.id.txtToastMessage).text = msg
        }.show()
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