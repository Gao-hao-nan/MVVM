package com.kt.network.net

class BaseUrlConstants {


    companion object {
        private const val baseUrl1: String = "http://test1/"
        private const val baseUrl2: String = "http://test2/"
        private const val video: String = "http://apis.juhe.cn/"
        private const val degree: String = "http://v.juhe.cn/"
        private const val wanandroid: String = "https://www.wanandroid.com"
        private const val baseurl3: String = "http://192.168.66.121:7002"
        fun getHost(host: Int): String {
            when (host) {
                1 -> return baseUrl1
                2 -> return baseUrl2
                3 -> return video
                4 -> return degree
                5 -> return wanandroid
                6 -> return baseurl3
            }
            return baseUrl1;
        }
    }
}