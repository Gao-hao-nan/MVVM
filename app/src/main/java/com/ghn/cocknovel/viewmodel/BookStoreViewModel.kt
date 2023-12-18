package com.ghn.cocknovel.viewmodel

import FontAdapter
import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basemodel.base.BaseViewModel
import com.example.basemodel.base.SingleLiveEvent
import com.ghn.cocknovel.net.DataService
import com.ghn.cocknovel.ui.activity.SetActivity
import com.ghn.cocknovel.ui.activity.SwitchActivity
import com.kt.NetworkModel.bean.LoginBean

/**
 * @author 浩楠
 *
 * @date 2023/1/9   16:11.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */
open class BookStoreViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        val TAG: String? = BookStoreViewModel::class.simpleName
        val mLogin = MutableLiveData<LoginBean>()
    }


    /**
     * 跳转到首页
     */
    open fun getMain(phoneNumber: String) {
        launchOnlyresult({
            DataService.login(1, phoneNumber)
        }, {
            Log.i(TAG, "getMain: $it")
//            if (it?.id != null) {
//                MVUtils.put("token",it.id)
//                startActivity(MainActivity::class.java)
//            } else {
//            }
        })

    }

    /**
     * 从我的页面跳转到设置页面
     */
    open fun getsetImage() {
        startActivity(SetActivity::class.java)
    }

    /**
     * 全局更换字体
     */
    open fun getSwitchFont() {
        //App.get().changeTTF()
        startActivity(SwitchActivity::class.java)
    }

    /**
     * 字体recyclerview的适配器
     */
    var canVertically: SingleLiveEvent<Int> = SingleLiveEvent()
    var adapter: ObservableField<FontAdapter>? = ObservableField(FontAdapter(this))
    var layoutManager: ObservableField<LinearLayoutManager>? = ObservableField(
        LinearLayoutManager(application)
    )
    var mListener: ObservableField<RecyclerView.OnScrollListener>? = ObservableField(OnListener())
    fun notifyScroller(newState: Int?, recyclerView: RecyclerView?) {

        if (newState != 0) {
            //通知scrolling
            //SCROLLING
//            canVertically.postValue(0)
        }
        recyclerView?.parent?.requestDisallowInterceptTouchEvent(true)
        val canScrollVertically = recyclerView?.canScrollVertically(-1)
        if (!canScrollVertically!! && newState == RecyclerView.SCROLL_STATE_IDLE) {
            canVertically.postValue(1)
            //CAN_SCROLLVERTICALLY
        }
    }

    inner class OnListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            notifyScroller(newState, recyclerView)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }
}