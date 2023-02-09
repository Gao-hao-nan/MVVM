package com.kt.network.net

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    /**
     * 图书馆数据
     */
    @GET(ApiAddress.ONE_PIECE)
    suspend fun onePiece(
        @Query("dtype") dtype: String,
        @Query("key") key: String
    ): Any?


    /**
     * 历史上的今天
     */
    @GET(ApiAddress.HISTORY_DATE)
    suspend fun history(
        @Query("date") dtype: String,
        @Query("key") key: String
    ): Any?


    /**
     * get
     */
    @GET(ApiAddress.LOGIN)
    suspend fun login(
        @Query("account") account: String,
        @Query("password") password: String
    ): BaseResponse<Any>


    /**
     * post body
     */
    @POST(ApiAddress.LOGIN)
    suspend fun loginBody(@Body requestBody: RequestBody): BaseResponse<Any>
} 