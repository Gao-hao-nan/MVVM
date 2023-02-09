package com.kt.network.net

import java.io.Serializable

class BaseResponse<T>:Serializable {

    private var message: String? = null
    private var code: Int? = null
    private var data: T? = null
    private var result = false


    fun isResult(): Boolean {
        return result
    }

    fun setResult(result: Boolean) {
        this.result = result
    }


    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

    fun getErrCode(): Int? {
        return code
    }

    fun setErroCode(erroCode: Int?) {
        this.code = erroCode
    }

    override fun toString(): String {
        return "BaseResponse{" +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", result=" + result +
                '}'
    }

}