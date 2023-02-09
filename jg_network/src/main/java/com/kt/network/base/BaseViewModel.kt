package com.kt.network.base

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.kt.network.base.BaseViewModel.Companion.ParameterField.BUNDLE
import com.kt.network.base.BaseViewModel.Companion.ParameterField.CLASS
import com.kt.network.base.BaseViewModel.Companion.ParameterField.REQEUST_DEFAULT
import com.kt.network.base.BaseViewModel.Companion.ParameterField.REQUEST
import com.kt.network.net.ExceptionUtil

import com.trello.rxlifecycle2.LifecycleProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {
    private var mLifecycle: WeakReference<LifecycleProvider<*>>? = null
    private var uc: UIChangeLiveData? = null

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {


    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }


    fun ViewModel.launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = { _: Throwable -> },
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                run {
                    // 这里统一处理错误
                    ExceptionUtil.catchException(throwable)
                    onError(throwable)
                }
            }
        ) {
            try {
                block.invoke(this)
            } finally {
                onComplete()
            }
        }
    }


    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    fun injectLifecycleProvider(lifecycle: LifecycleProvider<*>?) {
        mLifecycle = WeakReference(lifecycle)

    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<out Activity?>?) {
        startActivity(clz, null)
    }

    /**
     * @param clz  clz 所跳转的目的Activity类
     * @param code 启动requestCode
     */
    fun startActivity(clz: Class<out Activity?>?, code: Int) {
        startActivity(clz, null, code)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    private fun startActivity(clz: Class<out Activity?>?, bundle: Bundle?) {
        startActivity(clz, bundle, REQEUST_DEFAULT)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<out Activity?>?, bundle: Bundle?, requestCode: Int) {
        val params: MutableMap<String, Any?> = HashMap()
        params[CLASS] = clz
        params[REQUEST] = requestCode
        params[BUNDLE] = bundle
        uc?.getStartActivityEvent()?.postValue(params as Map<String, Any>)
    }


    fun startActivityForFragment(clz: Class<out Activity?>, bundle: Bundle, requestCode: Int) {
        val params: MutableMap<String, Any> = HashMap()
        params[CLASS] = clz
        params[REQUEST] = requestCode
        params[BUNDLE] = bundle
        uc?.getStartActivityForFragment()?.postValue(params)
    }

    /**
     * 关闭界面
     */
    fun finish() {
        uc?.getFinishEvent()?.postValue(null)
    }

    /**
     * 携带code的 finish
     */
    fun finishFragmentResult() {
        uc?.getResultFragment()?.postValue(null)
    }

    /**
     * 返回上一层
     */
    fun onBackPressed() {
        uc?.getOnBackPressedEvent()!!.postValue(null)
    }


    fun getUC(): UIChangeLiveData? {
        if (uc == null) {
            uc = UIChangeLiveData()
        }
        return uc
    }


    companion object {

        class UIChangeLiveData : SingleLiveEvent<Any?>() {

            private var startActivityEvent: SingleLiveEvent<Map<String, Any>>? = null
            private var finishEvent: SingleLiveEvent<Void>? = null
            private var onBackPressedEvent: SingleLiveEvent<Void>? = null
            private var setResultEvent: SingleLiveEvent<Map<String, String>>? = null
            private var finishResult: SingleLiveEvent<Int>? = null
            private var startActivityForFragment: SingleLiveEvent<Map<String, Any>>? = null
            private var setResultFragment: SingleLiveEvent<Map<String, Any>>? = null


            fun getResultFragment(): SingleLiveEvent<Map<String, Any>> {
                return createLiveData(setResultFragment).also {
                    setResultFragment = it
                }
            }

            fun getStartActivityForFragment(): SingleLiveEvent<Map<String, Any>> {
                return createLiveData(startActivityForFragment).also {
                    startActivityForFragment = it
                }
            }

            fun getFinishResult(): SingleLiveEvent<Int> {
                return createLiveData(finishResult ).also {
                    finishResult = it
                }
            }

            fun getStartActivityEvent(): SingleLiveEvent<Map<String, Any>> {
                return createLiveData(startActivityEvent).also {
                    startActivityEvent = it
                }


            }

            fun getSetResultEvent(): SingleLiveEvent<Map<String, String>> {
                return createLiveData(setResultEvent).also {
                    setResultEvent = it
                }
            }

            fun getFinishEvent(): SingleLiveEvent<Void> {
                return createLiveData(finishEvent).also {
                    finishEvent = it
                }
            }

            fun getOnBackPressedEvent(): SingleLiveEvent<Void> {
                return createLiveData(onBackPressedEvent).also {
                    onBackPressedEvent = it
                }
            }


            private fun <T> createLiveData(liveData: SingleLiveEvent<T>?): SingleLiveEvent<T> {

                var mLive: SingleLiveEvent<T>?=liveData
                liveData?.let {
                    return mLive!!
                }?:let {
                    mLive= SingleLiveEvent()
                }


                return mLive!!
            }


            override fun observe(owner: LifecycleOwner, observer: Observer<in Any?>) {
                super.observe(owner, observer)
            }


        }


        object ParameterField {
            const val CLASS = "CLASS"
            const val CANONICAL_NAME = "CANONICAL_NAME"
            const val BUNDLE = "BUNDLE"
            const val REQUEST = "REQUEST"
            const val REQEUST_DEFAULT = 1
        }
    }

}