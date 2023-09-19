package com.kt.network.net.interceptor

import android.content.Context
import com.alibaba.sdk.android.httpdns.HttpDns
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 拦截请求头
 */
class HTTPDNSInterceptor(private var context: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val httpUrl = originRequest.url
        val url = httpUrl.toString()
        val host = httpUrl.host
        val service = HttpDns.getService(context)
        val hostIP = service.getIpByHostAsync(host)
        val builder = originRequest.newBuilder()
        if (hostIP != null) {
            builder.url(url.replaceFirst(url, hostIP))
            builder.header("host", hostIP)
        }
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}