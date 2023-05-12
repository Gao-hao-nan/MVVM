package com.kt.network.net

class BaseUrlConstants {


    companion object {
        private const val baseUrl1: String = "http://test1/"
        private const val baseUrl2: String = "http://test2/"
        private const val video: String = "http://apis.juhe.cn/"
        private const val degree: String = "http://v.juhe.cn/"
        private const val wanandroid: String = "https://www.wanandroid.com"

        fun getHost(host: Int): String {
            when (host) {
                1 -> return baseUrl1
                2 -> return baseUrl2
                3 -> return video
                4 -> return degree
                5 -> return wanandroid
            }
            return baseUrl1;
        }
    }
}