package com.ghn.cocknovel.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.basemodel.base.BaseViewModel
import com.ghn.cocknovel.net.DataService
import com.ghn.cocknovel.ui.activity.WebviewActivity
import com.kt.NetworkModel.bean.ProjectBean
import com.kt.NetworkModel.bean.TabFrameBean
import com.kt.network.bean.FontDataNew
import com.stx.xhb.androidx.entity.BaseBannerInfo

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

    val homeStatus = MutableLiveData<FontDataNew>()
    val mBanner = MutableLiveData<List<BaseBannerInfo>>()
    val mProject = MutableLiveData<MutableList<ProjectBean.Data>>()
    val mProjectcontent = MutableLiveData<TabFrameBean.Data>()
    open fun getBanner() {
        launchOnlyresult({
            DataService.wbanner(5)
        }, {
            mBanner.value = it as List<BaseBannerInfo>
        })

    }

    open fun getHomeStatus() {
        launchOnlyresult({
            DataService.callback(5)
        }, {
            homeStatus.value = it
        })
    }

    open fun setWebview(url: String) {
        val bundle = Bundle()
        bundle.putString("url", url)
        startActivity(WebviewActivity::class.java, bundle, 1000)
    }


    open fun getProject() {
        launchOnlyresult({
            DataService.project(5)
        }, {
            mProject.value = it
        })
    }

    open fun project_content(page: Int, cid: Int) {
        launchOnlyresult({
            DataService.project_content(5, page, cid)
        }, {
            mProjectcontent.value = it
        })
    }


//    open fun getwan() {
//        launchOnlyresult({
//            DataService.callback(5)
//        }, {
//            loginStatus.value = it
//        })
//    }

    open fun dow() {

    }


}