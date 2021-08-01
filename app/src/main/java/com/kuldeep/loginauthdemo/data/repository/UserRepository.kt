package com.kuldeep.loginauthdemo.data.repository

import com.kuldeep.loginauthdemo.data.UserPreferences
import com.kuldeep.loginauthdemo.data.api.AuthApi
import com.kuldeep.loginauthdemo.data.api.UserApi

class UserRepository(
    val api: UserApi
) : BaseRepository() {
    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}