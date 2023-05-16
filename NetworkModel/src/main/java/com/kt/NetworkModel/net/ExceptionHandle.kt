package com.kt.network.net

import android.app.Application
import android.content.Context
import android.net.ParseException
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.hjq.xtoast.XToast
import com.kt.NetworkModel.App
import com.kt.ktmvvm.lib.R
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * @author 浩楠
 *
 * @date 2023/3/10-10:56.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO 根据不同的错误给出相应的提示
 */
object ExceptionHandle {
    fun handleException(e: Throwable): ResponseThrowable {
        var ex: ResponseThrowable
        if (e is ResponseThrowable) {
            ex = e
        } else if (e is HttpException) {
            ex = ResponseThrowable(ERROR.HTTP_ERROR, e)
            when (e.code()) {
                404 -> {
//                    ToastUtils.showShort(R.string.net_erro)
                    XToast<XToast<*>>(App.get()).apply {
                        setContentView(R.layout.layout_toast)
                        setDuration(3000)
                        findViewById<TextView>(R.id.txtToastMessage).text="网络地址错误。请稍后再试"
                    }.show()
                    ex = ResponseThrowable(ERROR.NOT_FOUND, e)
                }
                400 -> {
                    ex = ResponseThrowable(ERROR.TOKEN_EMPTY, e)
                }
            }
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {
            ex = ResponseThrowable(ERROR.PARSE_ERROR, e)
        } else if (e is ConnectException) {
//            ToastUtils.showShort(R.string.net_erro)
            XToast<XToast<*>>(App.get()).apply {
                setContentView(R.layout.layout_toast)
                setDuration(3000)
                findViewById<TextView>(R.id.txtToastMessage).text="网络连接失败。请稍后再试"
            }.show()

            ex = ResponseThrowable(ERROR.NETWORD_ERROR, e)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(ERROR.SSL_ERROR, e)
        } else if (e is java.net.SocketTimeoutException) {
//            ToastUtils.showShort(R.string.net_erro)
            XToast<XToast<*>>(App.get()).apply {
                setContentView(R.layout.layout_toast)
                setDuration(3000)
                findViewById<TextView>(R.id.txtToastMessage).text="网络连接失败。请稍后再试"
            }.show()
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, e)

        } else if (e is java.net.UnknownHostException) {
//            ToastUtils.showShort(R.string.net_erro)
            XToast<XToast<*>>(App.get()).apply {
                setContentView(R.layout.layout_toast)
                setDuration(3000)
                findViewById<TextView>(R.id.txtToastMessage).text="网络连接失败。请稍后再试"
            }.show()
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, e)
        } else {
            ex = if (!e.message.isNullOrEmpty()) ResponseThrowable(1000, e.message!!, e)
            else ResponseThrowable(ERROR.UNKNOWN, e)
        }
        return ex
    }
}