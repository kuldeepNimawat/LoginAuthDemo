package com.kuldeep.loginauthdemo.data.repository

import com.kuldeep.loginauthdemo.data.UserPreferences
import com.kuldeep.loginauthdemo.data.api.AuthApi

class LoginRepository(
    val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {
    suspend fun login(
        email: String,
        password:String
    ) = safeApiCall {
        api.Login(email,password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}