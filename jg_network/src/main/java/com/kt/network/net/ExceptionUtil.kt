package com.kt.network.net

import android.accounts.NetworkErrorException
import android.util.MalformedJsonException
import androidx.annotation.StringRes
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

object ExceptionUtil {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    const val NETWORK_ERROR = 1002

    /**
     * 协议错误
     */
    const val HTTP_ERROR = 1003

    /**
     * 处理异常，toast提示错误信息
     */
    fun catchException(e: Throwable) {
        e.printStackTrace()



        when (e) {
            is HttpException -> {
                catchHttpException(e.code())
            }
            is SocketTimeoutException -> {
//                showToast(R.string.common_error_net_time_out)
            }
            is UnknownHostException, is NetworkErrorException -> {
//                showToast(R.string.common_error_net)
            }
            is MalformedJsonException, is JsonSyntaxException -> {
//                showToast(R.string.common_error_server_json)
            }
            is InterruptedIOException -> {
                showToast("服务器连接失败，请稍后重试")
            }
            // 自定义接口异常
            is ApiException -> {
                showToast(e.message?:"", e.getCode())
            }
            is ConnectException -> {
                showToast( "连接服务器失败" )
            }
            else -> {
//                showToast("${MyApplication.instance.getString(
//                    R.string.common_error_do_something_fail
//                )}：${e::class.java.name}")
            }
        }
    }


    /**
     * 服务器异常 或 网络通道异常
     *
     * @param e
     * @return
     */
    private fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        return if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            //解析错误
            ex = ApiException(PARSE_ERROR, e.message)
            ex
        } else if (e is ConnectException) {
            //网络错误
            ex = ApiException(
                NETWORK_ERROR,
                e.message
            )
            ex
        } else if (e is UnknownHostException || e is SocketTimeoutException) {
            //连接错误
            ex = ApiException(
                NETWORK_ERROR,
                e.message
            )
            ex
        } else {
            //未知错误
            ex = ApiException(
                UNKNOWN,
                e.message
            )
            ex
        }
    }

    /**
     * 处理网络异常
     */
    private fun catchHttpException(errorCode: Int) {
        if (errorCode in 200 until 300) return// 成功code则不处理
//        showToast(
//            catchHttpExceptionCode(
//                errorCode
//            ), errorCode
//        )
    }

    /**
     * toast提示
     */
    private fun showToast(@StringRes errorMsg: Int, errorCode: Int = -1) {
//        showToast(MyApplication.instance.getString(
//                errorMsg
//            ), errorCode
//        )
    }

    /**
     * toast提示
     */
    private fun showToast(errorMsg: String, errorCode: Int = -1) {
//        if (errorCode == -1) {
//            ToastUtils.showShort(errorMsg)
//        } else {
//            ToastUtils.showShort("$errorCode：$errorMsg")
//        }
    }

    /**
     * 处理网络异常
     */
//    private fun catchHttpExceptionCode(errorCode: Int): Int = when (errorCode) {
//        in 500..600 -> R.string.common_error_server
//        in 400 until 500 -> R.string.common_error_request
//        else -> R.string.common_error_request
//    }
}