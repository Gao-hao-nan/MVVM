package com.kt.network.base

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.kt.network.base.BaseViewModel.Companion.ParameterField.BUNDLE
import com.kt.network.base.BaseViewModel.Companion.ParameterField.CLASS
import com.kt.network.base.BaseViewModel.Companion.ParameterField.REQUEST
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : RxAppCompatActivity(), IBaseView {

    open var binding: V? = null
    open var viewModel: VM? = null
    open var viewModelId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewDataBinding(savedInstanceState)
        //页面接受的参数方法
        initParam()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
    }


    private fun registerUIChangeLiveDataCallBack() {

        //跳入新页面
        viewModel?.getUC()?.getStartActivityEvent()?.observe(this) { params ->

            params?.let {
                val clz = params[CLASS] as Class<*>?
                val intent = Intent(this@BaseActivity, clz)
//            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                val bundle = params[BUNDLE]
                if (bundle is Bundle) {
                    intent.putExtras((bundle as Bundle?)!!)
                }
                startActivityForResult(intent, params[REQUEST] as Int)
            }

        }
        viewModel?.getUC()?.getFinishResult()?.observe(this) { integer ->
            integer?.let {
                setResult(integer)
                finish()
            }
        }

        //关闭界面

        //关闭界面
        viewModel?.getUC()?.getFinishEvent()?.observe(this) { finish() }
        //关闭上一层

        viewModel?.getUC()?.getOnBackPressedEvent()?.observe(this) { onBackPressed() }

        viewModel?.getUC()?.getSetResultEvent()?.observe(this) { params ->
            params?.let {
                val intent = Intent()
                if (params.isNotEmpty()) {
                    val strings: Set<String> = params.keys
                    for (string in strings) {
                        intent.putExtra(string, params[string])
                    }
                }
                setResult(RESULT_OK, intent)
            }

        }
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding =
            DataBindingUtil.setContentView(this@BaseActivity, initContentView(savedInstanceState))


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

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }


    /**
     * 创建ViewModel 如果 需要自己定义ViewModel 直接复写此方法
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    open fun <T : ViewModel> createViewModel(activity: FragmentActivity?, cls: Class<T>?): T {
        return ViewModelProvider(activity!!)[cls!!]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel?.onActivityResult(requestCode, resultCode, data)
    }


    /**
     * 提供livedata 或者flow 数据流观察回调
     */
    override fun initViewObservable() {
    }


    /**
     * 返回vaeriableId
     */
    abstract fun initVariableId(): Int

    /**
     * 返回布局id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

}


