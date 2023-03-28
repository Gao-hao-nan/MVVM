package com.ghn.cocknovel.net

import com.ghn.cocknovel.App
import com.kt.network.bean.BaseResult
import com.kt.network.net.ApiService
import com.kt.network.net.RetrofitClient

/**
 * @author 浩楠
 *
 * @date 2023/1/9   17:08.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:请求管理器
 */
class DataService {
    companion object{
        /**
         * 测试网络请求框架
         */
        suspend fun callback(host: Int): BaseResult<Any> {
            return RetrofitClient.getInstance(App.get()).getDefault(ApiService::class.java, host)
                .callback()
        }

    }
}