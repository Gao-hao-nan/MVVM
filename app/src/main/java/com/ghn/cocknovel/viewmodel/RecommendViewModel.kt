package com.ghn.cocknovel.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.basemodel.base.BaseViewModel
import com.ghn.cocknovel.net.DataService
import com.kt.network.bean.FontDataNew

/**
 * @author 浩楠
 *
 * @date 2023/4/6-17:16.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
open class RecommendViewModel(application: Application) : BaseViewModel(application) {
    companion object {
        val TAG: String? = BookStoreViewModel::class.simpleName
    }
    val loginStatus = MutableLiveData<FontDataNew>()
    open fun getwan(){
        launchOnlyresult({
            DataService.callback(5)
        },{
            Log.i(TAG, "getwan: $it")
            loginStatus.value= it
        })
    }
}