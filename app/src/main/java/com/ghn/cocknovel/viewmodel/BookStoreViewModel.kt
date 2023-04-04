package com.ghn.cocknovel.viewmodel

import FontAdapter
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ghn.cocknovel.App
import com.ghn.cocknovel.net.DataService
import com.ghn.cocknovel.ui.activity.MainActivity
import com.ghn.cocknovel.ui.activity.SetActivity
import com.ghn.cocknovel.ui.activity.SwitchActivity
import com.kt.network.base.BaseViewModel
import com.kt.network.base.SingleLiveEvent
import com.kt.network.net.ApiService
import com.kt.network.net.IBaseResponse

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
    }
    val loginStatus = MutableLiveData<List<String>>().apply {}
    open fun getwan(){
        launchOnlyresult({
            DataService.callback(5)
        },{
            loginStatus.value= listOf(it.toString())
        }, isShowDialog = false)
    }
    /**
     * 跳转到首页
     */
    open fun getMain(){
        startActivity(MainActivity::class.java)
    }
    /**
     * 从我的页面跳转到设置页面
     */
    open fun getsetImage(){
        startActivity(SetActivity::class.java)
    }

    /**
     * 全局更换字体
     */
    open fun getSwitchFont(){
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