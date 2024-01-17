package com.example.basemodel.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.basemodel.R
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.BUNDLE
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.CANONICAL_NAME
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.CLASS
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.REQUEST
import com.kt.NetworkModel.utils.ToastUtils
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import java.lang.reflect.ParameterizedType


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
 * @Description: TODO 封装一个BaseActivity
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : RxAppCompatActivity(),
    IBaseView {
    open var mBinding: V? = null
    open var mViewModel: VM? = null
    open var viewModelId = 0
    private var dialog: MaterialDialog? = null
    private var toast: ToastUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewDataBinding(savedInstanceState)
        //页面接受的参数方法
        initParam()
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack()
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable()
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) //隐藏状态栏
        }
    }


    private fun registerUIChangeLiveDataCallBack() {

        //跳入新页面
        mViewModel?.getUC()?.getStartActivityEvent()?.observe(this) { params ->

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
        //包名和类名跳转
        mViewModel?.getUC()?.getStartModelActivityEvent()?.observe(this){ params->
            params?.let {
                val clz=params[CLASS]
                val Packagename=params[CANONICAL_NAME]
                val intent=Intent()
                intent.setClassName(Packagename.toString(), clz.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        mViewModel?.getUC()?.getFinishResult()?.observe(this) { integer ->
            integer?.let {
                setResult(integer)
                finish()
            }
        }

        mViewModel?.getUC()?.getShowDialog()?.observe(this) {
            showLoading()
        }

        mViewModel?.getUC()?.getDismissDialog()?.observe(this) {
            dismissLoading()
        }
        mViewModel?.getUC()?.toastEvent()?.observe(this) {
//            ToastUtils.showShort(it)
            showMsg(it.toString())
        }
        //关闭界面
        mViewModel?.getUC()?.getFinishEvent()?.observe(this) { finish() }
        //关闭上一层

        mViewModel?.getUC()?.getOnBackPressedEvent()?.observe(this) { onBackPressed() }

        mViewModel?.getUC()?.getSetResultEvent()?.observe(this) { params ->
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

    override fun onResume() {
        super.onResume()
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding =
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
        mViewModel = createViewModel(this, modelClass as Class<VM>)
        //关联ViewModel
        mBinding?.setVariable(viewModelId, mViewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding?.lifecycleOwner = this
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(mViewModel!!)
        //注入RxLifecycle生命周期
        mViewModel?.injectLifecycleProvider(this)

    }

    /**
     * 打开等待框
     */
    private fun showLoading() {
//        if (dialog == null) {
//            dialog = LoadingDialog.getInstance(this)
//        }
//        dialog?.show()

        if (dialog == null) {
            dialog = MaterialDialog(this)
                .cancelOnTouchOutside(false)
                .cornerRadius(8f)
                .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
                .lifecycleOwner(this)
                .maxWidth(R.dimen.dialog_width)
        }

        dialog?.show()
    }

    /**
     * 关闭等待框
     */
    private fun dismissLoading() {
        dialog?.run {
            if (isShowing) dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
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
        mViewModel?.onActivityResult(requestCode, resultCode, data)
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

    /**
     * 自定义Toast文字
     */
    fun showMsg(msg: String) {
        toast = ToastUtils(this)
        toast?.InitToast()
        toast?.setText(msg)
        toast?.setGravity(Gravity.CENTER)
        toast?.show()
    }

    /**
     * 自定义Toast图片+文字
     */
    fun showMsgimage(msg: String, url: Int) {
        toast = ToastUtils(this)
        toast?.InitToast()
        toast?.setText(msg)
        toast?.setImage(url)
        toast?.setGravity(Gravity.CENTER)
        toast?.show()
    }

}


