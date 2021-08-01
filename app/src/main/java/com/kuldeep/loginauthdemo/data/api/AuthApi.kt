package com.kuldeep.loginauthdemo.data.api

import com.kuldeep.loginauthdemo.data.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun Login(
        @Field("email") email :String,
        @Field("password") password: String
    ): LoginResponse
}